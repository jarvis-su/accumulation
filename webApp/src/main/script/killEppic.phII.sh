#!/bin/sh

################################################
# killEppic.sh by Jack Gott 2015
#
# Features:
#   minimal configuration.
#   Take thread and heap dumps when eppic shuts down.
#   Checks for Curent External Files Processing.
#   Checks for processing running in JVM via jmx remote interface (java console).
#   If any are found , warns user and asks to proceed.
#
#################################################

if [ "" = "${1}" ]; then
  echo 'USAGE: ./killEppic [username]'
  exit
fi

USER=${1}
STATE="$( whoami )"
RUNEPPIC='./eccPrimary.sh'

 REQUIRE_OVERRIDE=''

 ## Check for running processes.
 processes_found() {
     echo "Checking via jconsole for running Tasks..."
     taskfound="`$HOME/scripts/check_running_Tasks.sh | tail -1 `" 
     # echo "$taskfound"

     if [ "${taskfound}" == "RunningTasks = (  );" ]; then
             return 1
         else
             echo "   Found task: ${taskfound#*=}"
             return 0
     fi
 }

 ## Check for external files.
 extfile_found() {
 # Check if there are extfiles in the extfile directory.
 # Returns 0 if file found, return 1 if no.
     echo "Testing External Files Directory..."

     if [ ! -z /home/${STATE}/${STATE}/extfile/queues ]; then
          filefound="$( find /home/${STATE}/${STATE}/extfile/ -maxdepth 1 -type f )"
     else
          filefound="$( find /home/${STATE}/${STATE}/extfile/queues/extfile* -maxdepth 1 -type f )"
     fi

     if [ ! -z "${filefound}" ]; then
             echo "   Found: ${filefound}"
             return 0
         else
             return 1
     fi
 }

 currentfile_found()
 {
    echo
 }


 ## take the dumps before stopping eppic.

 dist_dir="$HOME/$STATE/"
 log_dir="${dist_dir}log/"
 dt="`date +%Y%m%d%H%M`"
 eppic_pid=`ps -ef | grep SysMgmtManagerImpl | grep -v grep | awk {'print$2'}`
 jstack="$JAVA_HOME/bin/jstack"
 jmap="$JAVA_HOME/bin/jmap"
 tdump_name="${STATE}_threaddump_${dt}"
 hdump_name="${STATE}_heapdump_${dt}"

 if [ -z ${jstack} ] || [ -z ${eppic_pid} ]; then
    echo "ERROR!  No heap/thread dump taken."
 else
    # Uncomment the following to enable stack trace to be taken.
    echo "Dumping Threads list to ${log_dir}${tdump_name}.txt ..."
    ${jstack} -l ${eppic_pid} >${log_dir}${tdump_name}.txt 
    echo "Thread dump file created."
    # Uncomment the following to enable heap dump to be taken.
    # ${jmap} -dump:format=b,file=${log_dir}${hdump_name}prof ${eppic_pid}
 fi

 ## Check for running External Files
 if extfile_found; then
     currentfile_found
     REQUIRE_OVERRIDE='true'
 fi

 ## Check for running External Files
 if processes_found; then
     currentfile_found
     REQUIRE_OVERRIDE='true'
 fi

 hold_extfile() {
 # Moves files from the extfile directory to a staging location. Performs
 # notification and logging after moving files.

     filefound="$( find ~/${STATE}/extfile/ -maxdepth 1 -type f )"

     datestamp=$( date +'%Y%m%d' )

     filefoundfile=filefound${datestamp}.txt

     echo ${filefound} >> ${filefoundfile}

     mkdir /home/${STATE}/${STATE}/extfile/killhold

     mv $filefound /home/${STATE}/${STATE}/extfile/killhold


 EMAIL_TO="Round.Rock.EPSCE.Group@xerox.com eppic24@xerox.com eppic24ext@xerox.com USA.EBTStatesServOpsMgmt@xerox.com"
 EMAIL_FROM="$( whoami )@$( hostname )"
 EMAIL_SUBJECT="External Files were moved because Eppic was killed "
 # Please edit the following to indicate a UAT if it is one.
 EMAIL_BODY="$USER UAT eppic was killed in a override scenario.  A Likely extfile, report, or system process was running and Eppic was killed with an override. \n  An ECC CE should be contacted if they are not already aware of this. \n
  So extfiles were moved as a precaution to /home/${STATE}/${STATE}/extfile/killhold folder. You should see the following files there. \n ${filefound} \n ${filefound2} \n\n"

 echo -e "${EMAIL_BODY}" | mail -s "${STATE}:${EMAIL_SUBJECT}" ${EMAIL_TO}
 echo "Sending emails on moved extfiles"
 }

# function to kill the eppic running processes.
kill_eppic() {
   # Kill EPPIC
   echo "Getting Core Dump of EPPIC..."
   # pkill -3 -o -u $USER -f SysMgmtManagerImpl &> /dev/null
   echo "Killing EPPIC..."
   # pkill -9 -u $USER -f SysMgmtManagerImpl &> /dev/null

   # Make sure we killed it.
   pgrep -u $USER -f SysMgmtManagerImpl &> /dev/null
   if [ $? == 0 ]; then
      echo "EPPIC is not fully dead. Bailing out."
      exit
   fi
}


 ## Override Resolution and Kill EPPIC
 if [ -z "${REQUIRE_OVERRIDE}" ]; then
     kill_eppic
 else
     echo "============================================================"
     echo "== EPPIC HAS DETECTED AN ISSUE                            =="
     echo "============================================================"
     echo "If this is part of a production incident, please present the"
     echo "output above to your OM before making your decision."
     echo ""
     echo "If you wish to kill EPPIC at this time, please type 'yes'"
     echo "Any other input will be ignored."

     read decision

     if [ "${decision}" == 'yes' ]; then
         hold_extfile
# SysMgmtManagerImpl
         EPPICPID=` ps -fww --user ${USER} | grep 'SysMgmtManagerImpl' | grep -v grep | sort | awk '{print $2}' | perl -ne 'chomp;print "$_ "'`
         if [ "${EPPICPID}" ]; then
             kill_eppic
             mv *core* ../cores/
         fi

     else
         echo "EPPIC survives"
     fi
 fi

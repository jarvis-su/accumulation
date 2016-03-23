#!/bin/bash

# Test script to find the running processes on ECC.

st=`whoami`
jmxtermLoc="/home/$st/scripts/bin/"
jmxterm="java -jar ${jmxtermLoc}jmxterm-1.0-alpha-4-uber.jar"
jmxcmd="/home/$st/scripts/myjmx.script"
jmxout="/home/$st/scripts/myjmx.out"
pw=`cat $HOME/$st/jmxremote.password | tail -2 | head -1 | awk {'print $2'}`
ident=${pw:0:11}
username=`cat $HOME/$st/jmxremote.access | tail -1 | awk {'print $1'}`
jmxport="`grep jmxremote.port $HOME/$st/eccPrimary.sh | awk {'print $2'}`"
port="${jmxport#*port=}"
ip="localhost"

echo "get -b name=Scheduler -d sms *" >$jmxcmd
$jmxterm -o $jmxout -l \"${ip}:${port}\" -u $username -p \"$ident\" -i $jmxcmd

tail -2 $jmxout | head -1

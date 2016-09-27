package com.acs.fileChecker.bs;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import com.acs.eppic.message.AlertData;
import com.acs.eppic.message.Message;
import com.acs.eppic.messageservice.MessageSender;
import com.acs.fileChecker.common.Constant;
import com.acs.fileChecker.common.PropertyFileReader;
import com.smj.dbvariable.VarCache;

public class FileChecker {

    public static final String CONFIG_STR = Constant.AGENT_CONFIG_FILE;

    public static void main(String argv[]) {
        FileChecker x = null;
        if (argv.length <= 2) {
            if (argv[0].equals("-unignoreAll")) {
                x = new FileChecker(CONFIG_STR);
                x.unignoreAll();
            } else if (argv[0].equals("-ignore")) {
                x = new FileChecker(CONFIG_STR);
                String state = argv[1];
                x.ignoreAFile(state);
            } else if (argv[0].equals("-listAllFor")) {
                x = new FileChecker(CONFIG_STR);
                String state = argv[1];
                System.out.println(x.getAllFor(state));
            } else {
                String states = argv[0];
                String dateFor = argv[1];
                String files = null;
                x = new FileChecker(dateFor, CONFIG_STR);
                for (Iterator iterator = x.commasToArray(states).iterator(); iterator.hasNext();) {
                    String instance = (String) iterator.next();
                    ReportList replist = x.getFilesForInstanceLocal(instance, false);
                    if (replist != null) {
                        files = x.checkFilesLocal(replist, false, true);
                        if (files != null && !files.equals(""))
                            System.out.println(files);
                    }
                }

            }
            x.cleanup();
        } else if (argv.length >= 3) {
            String states = argv[0];
            String dateFor = argv[1];
            x = new FileChecker(dateFor, CONFIG_STR);
            String files = null;
            String alertType = argv[2];
            for (Iterator iterator1 = x.commasToArray(states).iterator(); iterator1.hasNext();) {
                String instance = (String) iterator1.next();
                ReportList replist = x.getFilesForInstanceLocal(instance, true);
                files = x.checkFilesLocal(replist, true, false);
                if (alertType.startsWith("-mailTo=")) {
                    String emailList = alertType.substring(8);
                    // x.mailIt(emailList, files);
                    x.mailIt(instance.toUpperCase(), emailList, files);
                } else if (alertType.startsWith("-alert") && !files.equals(""))
                    x.sendAlert(instance.toUpperCase(), (new StringBuilder("FileChecker Detected problems\n\n"))
                            .append(files).toString());
                if (!files.equals(""))
                    System.out.println(files);
            }

            x.cleanup();
        } else {
            System.out
                    .println("Usage: FileChecker instance1,instance2 \n -mailTo=email1,email2...\n -alert (Sends alerts to EMMS)");
        }
    }

    public FileChecker(String properties) {
        _dbhost = null;
        _dbname = null;
        _dbuser = null;
        _dbpass = null;
        _username = null;
        _emailFrom = null;
        _emailHost = null;
        _emailReplyTo = null;
        _keyFile = null;
        _remoteCommand = "/usr/local/java14/bin/java -classpath /home/monitor/lib FileCheckerMain3 2>/tmp/blah";
        _states = new HashMap();
        _ms = null;
        _cal = Calendar.getInstance();
        _ms = new MessageSender();
        _today = Calendar.getInstance();
        _checkDate = getMonth() + "/" + getDay() + "/" + getYear();
        try {
//            _conn = SQLiteUtil.openConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadProperties(properties);
        _ps = prepare(_checkDate);
    }

    public FileChecker(String checkDate, String properties) {
        _dbhost = null;
        _dbname = null;
        _dbuser = null;
        _dbpass = null;
        _username = null;
        _emailFrom = null;
        _emailHost = null;
        _emailReplyTo = null;
        _keyFile = null;
        _remoteCommand = "/usr/local/java14/bin/java -classpath /home/monitor/lib FileCheckerMain3 2>/tmp/blah";
        _states = new HashMap();
        _ms = null;
        _cal = strToCal(checkDate);
        _ms = new MessageSender();
        _today = Calendar.getInstance();
        _checkDate = checkDate;
        try {
            Class.forName("org.sqlite.JDBC");
            _conn = DriverManager.getConnection("jdbc:sqlite:fileChecker.db");
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadProperties(properties);
        _ps = prepare(checkDate);
    }

    public void sendAlert(String id, String desc) {
        Message m = new Message(id, "FILECHECK", 1, "");
        // m.setM_MessageData(new AlertData("_GENERIC_1", desc));
        m.setM_MessageData(new AlertData("_GENERIC_1",
                "FileChecker Detected problems\n\n\"http://acsint414:8080/FileChecker/FileChecker?state=" + id
                        + "&checknow=true&dateFor=" + _checkDate + "\""));
        // m.setM_MessageData(new AlertData("_GENERIC_1",
        // "FileChecker Detected problems for " + _checkDate +
        // "\n\nhttp://acsint414:8080/FileChecker/FileChecker?state=" + id));
        _ms.enqueue(m);
    }

    public ArrayList commasToArray(String s) {
        ArrayList ar = new ArrayList();
        for (StringTokenizer st = new StringTokenizer(s, ","); st.hasMoreTokens(); ar.add(st.nextToken()))
            ;
        return ar;
    }

    public void mailIt(String id, String emailList, String stuff) {
        ArrayList list = commasToArray(emailList);
        if (stuff == null || stuff.equals("") || _emailFrom == null || _emailHost == null)
            return;
        MultiPartEmail email = new MultiPartEmail();
        try {
            email.setFrom(_emailFrom);
            email.addReplyTo(_emailReplyTo);
            // email.setSubject((new
            // StringBuilder("[FileChecker] Problems detected at ")).append(String.format("%1$tD %1$tl:%1$tM %1$tp",
            // new Object[] {
            // Calendar.getInstance()
            // })).toString());
            email.setSubject(id + " - FILECHECK has had an error");
            email.setHostName(_emailHost);
            String s;
            for (Iterator iterator = list.iterator(); iterator.hasNext(); email.addTo(s))
                s = (String) iterator.next();

            // email.addPart(stuff, "text/plain");
            String emailtext = "LAECC - FILECHECK {DESCRIPTION=FileChecker Detected problems\n\n\"http://acsint414:8080/FileChecker/FileChecker?state="
                    + id + "&checknow=true&dateFor=" + _checkDate + "\",CODE=_GENERIC_1}";
            email.addPart(emailtext, "text/plain");
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

    public void loadProperties(String filename) {
        PropertyFileReader props = new PropertyFileReader(filename, null);
        // URL url = getClass().getResource(filename);
        // File f = new File(url.getFile());
        System.out.println(filename);
        System.out.println();
        _username = props.retrieveValueInstance("remote.username");
        _keyFile = props.retrieveValueInstance("keyfile");
        _remoteCommand = props.retrieveValueInstance("remote.command");
        _dbhost = props.retrieveValueInstance("database.host");
        _dbname = props.retrieveValueInstance("database.schema");
        _dbuser = props.retrieveValueInstance("database.user");
        _dbpass = props.retrieveValueInstance("database.pass");
        _emailFrom = props.retrieveValueInstance("email.from");
        _emailHost = props.retrieveValueInstance("email.host");
        _emailReplyTo = props.retrieveValueInstance("email.replyto");
        _numGroups = props.retrieveIntValueInstance("products.numgroups");
        for (int i = 0; i < _numGroups; i++) {
            int numinstances = props.retrieveIntValueInstance((new StringBuilder("products.")).append(i).append(".numInstances").toString());
            for (int j = 0; j < numinstances; j++) {
                String name = props.retrieveValueInstance((new StringBuilder("products.")).append(i).append(".").append(j)
                        .append(".name").toString());
                String instanceId = props.retrieveValueInstance((new StringBuilder("products.")).append(i).append(".").append(j)
                        .append(".instanceId").toString());
                _states.put(name.toUpperCase(), instanceId);
            }

        }

        VarCache.getInstance().load();
    }

    public Set getStates() {
        return _states.keySet();
    }

    public void cleanup() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        _ms.setServiceEnabled(false);
        _ms = null;
        try {
            _ps.close();
        } catch (SQLException sqlexception) {
        }
        _conn = null;
        _ps = null;
    }

    public String getQueryForAllDates() {
        StringBuffer query = new StringBuffer("");
        query.append("select reportID, fileName, desc, checkTime, ignoreFlag from ( ");
        query.append("select r.reportID, r.fileName, rf.desc, r.checkTime, r.ignoreFlag from tblVariableReport r, tblReportFrequency rf ");
        query.append("where r.frequencyID = rf.frequencyID ");
        query.append("and r.instanceID = ? ");
        query.append("union all select r2.reportID, r2.fileName, cal.month || '/' || cal.dayOfMonth || '/' || cal.year as desc, r2.checkTime, r2.ignoreFlag ");
        query.append("from tblVariableReport r2, tblCalendar cal ");
        query.append("where r2.reportID = cal.reportID ");
        query.append("and r2.instanceID = ?) order by fileName, desc, checkTime ");
        return query.toString();
    }

    public String getAllFor(String state) {
        String instanceID;
        StringBuffer sb;
        instanceID = (String) _states.get(state.toUpperCase());
        sb = new StringBuffer();
        PreparedStatement ps;
        if (instanceID == null)
            return null;
        try {
            ps = _conn.prepareStatement(getQueryForAllDates());
            ps.setString(1, instanceID);
            ps.setString(2, instanceID);
            for (ResultSet rs = ps.executeQuery(); rs.next();) {
                String fileName = rs.getString("fileName");
                String reportID = rs.getString("reportID");
                String desc = rs.getString("desc");
                String checkTime = rs.getString("checkTime");
                String ignoreFlag = rs.getString("ignoreFlag");
                try {
                    sb.append(String.format("%s,%s,%s,%s,%s\r\n", new Object[] { reportID, fileName, desc, checkTime,
                            ignoreFlag }));
                } catch (Exception exception1) {
                }
            }

        } catch (Exception exception) {
        }
        return sb.toString();
    }

    public String checkFilesLocal(ReportList reports, boolean errOnly, boolean showExpected) {
        StringBuffer output = new StringBuffer();
        FileCheckerMain3 fcm = new FileCheckerMain3(false);
        if (reports == null)
            return null;
        Collection coll = reports.getAll().values();
        for (Iterator iterator = reports.getAll().values().iterator(); iterator.hasNext();) {
            Report r = (Report) iterator.next();
            String line = r.toString();
            String base = fcm.baseName(line);
            String name = fcm.fileName(line);
            int expectedCount = r.getExpectedCount();
            String test[];
            String startCheck = r.getReadableStartCheck();

            if (!r.shouldBeChecked() && !showExpected)
                continue;

            /**
             * CPU + IO enhancement most regex contain * (Don't have to check
             * complete dir if this not a regex)
             */
            if ((new File(line)).exists())
                test = new String[] { name };
            else
                test = fcm.getFilesRegExp(new File(base), name);
            /**/
            boolean missing = (test == null);

            // Check each file
            if (test != null && test.length != 0) {
                for (int j = 0; j < test.length; j++) {
                    String ch = fcm.check(base + test[j]);
                    if (!errOnly || errOnly && ch.contains("Error"))
                        output.append(base + test[j] + "\t" + startCheck + "\t" + ch + "\n");
                }

            }
            // No files, but we don't expect them until later. Show when they
            // should be expected
            else if (!r.shouldBeChecked() && showExpected) {
                output.append(base + name + "\t" + startCheck + "\t\t\t\n");
                continue;
            }

            // Only validate these files, don't care if they don't exist (NO
            // ERROR)
            if ((missing || expectedCount > test.length) && r.canIgnoreMissing()) {
                if (!errOnly)
                    output.append(r + "\tN/A\tN/A\t\n");
            }
            // No files or less files than expected (ERROR!)
            else if (missing || expectedCount > test.length) {
                int count = test != null ? expectedCount - test.length : expectedCount;
                output.append(base
                        + name
                        + "\t"
                        + startCheck
                        + String.format("\t\t\tError missing %1$s of %2$s file%3$s expected by %4$tH:%4$tM\n",
                                new Object[] { count, expectedCount, expectedCount != 1 ? "s" : "", r.getStartCheck() })
                                .toString());
            }
        }
        return output.toString();
    }

    public int getMonth() {
        return _cal.get(2) + 1;
    }

    public int getDay() {
        return _cal.get(5);
    }

    public int getDayOfWeek() {
        return _cal.get(7);
    }

    public int getYear() {
        return _cal.get(1);
    }

    public String getFilesForInstanceTest(String state, boolean errOnly) {
        String output = null;
        ReportList replist = new ReportList();
        replist.add(new Report(0, "YourFace"));
        try {
            output = checkFilesLocal(replist, errOnly, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    /*
     * public String getQuery(String date) { StringBuffer query = new
     * StringBuffer("");
     * query.append("select reportID, fileName, checkTime, checkStop from (");
     * query.append(
     * "select r.reportID, r.fileName, r.checkTime, r.checkStop from tblVariableReport r, tblReportFrequency rf "
     * ); query.append(
     * "where ( rf.month = 0 or rf.month = ? ) and ( rf.dayOfMonth = 0 or rf.dayOfMonth = ? ) "
     * ); query.append(
     * "and ( rf.dayOfWeek = 0 or rf.dayOfWeek = ? ) and r.instanceID = ? ");
     * query.append(
     * "and r.frequencyID = rf.frequencyID and r.ignoreFlag = 0 and r.disabled = 0 "
     * ); query.append(
     * "union all select r2.reportID, r2.fileName, r2.checkTime, r2.checkStop "
     * ); query.append("from tblVariableReport r2, tblCalendar cal ");
     * query.append("where cal.year = ? and cal.month = ? ");
     * query.append("and cal.dayOfMonth = ? and r2.reportID = cal.reportID ");
     * query.append(
     * "and r2.instanceID = ? and r2.ignoreFlag = 0 and r2.disabled = 0) order by fileName, checkTime, checkStop "
     * ); return query.toString(); }
     */

    public String getQuery(String date) {
        StringBuffer query = new StringBuffer("");
        query.append("select reportID, fileName, checkTime, checkStop, ignoreMissing from (");
        query.append("select r.reportID, r.fileName, r.checkTime, r.checkStop, r.ignoreMissing from tblVariableReport r, tblReportFrequency rf ");
        query.append("where ( rf.month = 0 or rf.month = ? ) and ( rf.dayOfMonth = 0 or rf.dayOfMonth = ? ) ");
        query.append("and ( rf.dayOfWeek = 0 or rf.dayOfWeek = ? ) and r.instanceID = ? ");
        query.append("and r.frequencyID = rf.frequencyID and r.ignoreFlag = 0 and r.disabled = 0 ");
        query.append("union all select r2.reportID, r2.fileName, r2.checkTime, r2.checkStop, r2.ignoreMissing ");
        query.append("from tblVariableReport r2, tblCalendar cal ");
        query.append("where cal.year = ? and cal.month = ? ");
        query.append("and cal.dayOfMonth = ? and r2.reportID = cal.reportID ");
        query.append("and r2.instanceID = ? and r2.ignoreFlag = 0 and r2.disabled = 0) order by fileName, checkTime, checkStop ");
        return query.toString();
    }

    public PreparedStatement prepare(String date) {
        PreparedStatement pps = null;
        try {
            pps = _conn.prepareStatement(getQuery(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pps;
    }

    public void setValues(PreparedStatement ps, int year, int month, int day, int dow, String instanceID)
            throws SQLException {
        ps.setInt(1, month);
        ps.setInt(2, day);
        ps.setInt(3, dow);
        ps.setInt(4, Integer.parseInt(instanceID));
        ps.setInt(5, year);
        ps.setInt(6, month);
        ps.setInt(7, day);
        ps.setInt(8, Integer.parseInt(instanceID));
    }

    public ReportList getFilesForInstanceLocal(String state, boolean errOnly) {
        String output = null;
        ReportList replist = new ReportList();
        String instanceID = (String) _states.get(state.toUpperCase());
        if (instanceID == null)
            return null;
        Properties runtimeEnv = new Properties();
        runtimeEnv.put("DATEFOR", _cal);
        try
        {
            setValues(_ps, getYear(), getMonth(), getDay(), getDayOfWeek(), instanceID);
            // instanceID);
            for (ResultSet rs = _ps.executeQuery(); rs.next();)
                try
                {
                    String s;
                    Calendar start;
                    Calendar end;
                    int reportID;
                    int ignoreMissing;
                    for(Iterator iterator = VarCache.resolve(rs.getString("fileName"), runtimeEnv).iterator(); iterator.hasNext(); replist.add(new Report(reportID, s, start, end, ignoreMissing == 1)))
                    {
                        s = (String)iterator.next();
                        start = setCalDate(_cal, rs.getString("checkTime"));
                        end = setCalDate(_cal, rs.getString("checkStop"));
                        reportID = rs.getInt("reportID");
                        ignoreMissing = rs.getInt("ignoreMissing");

                            // expression
                            // State folder path
                            // Eppic Path
                            // TODO: handle exception
                        }
                    }

                catch(Exception e)
                {
                    e.printStackTrace();
                }

            System.err.println();
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        return replist;
    }


    public void ignoreAFile(String state) {
        String instanceID = (String) _states.get(state.toUpperCase());
        ReportList replist = new ReportList();
        try {
            setValues(_ps, getYear(), getMonth(), getDay(), getDayOfWeek(), instanceID);
            Calendar start;
            Calendar end;
            int reportID;
            for (ResultSet rs = _ps.executeQuery(); rs.next(); replist.add(new Report(reportID, rs
                    .getString("fileName"), start, end, false))) {
                start = setCalDate(_cal, rs.getString("checkTime"));
                end = setCalDate(_cal, rs.getString("checkStop"));
                reportID = rs.getInt("reportID");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));
        ReportList ignores = new ReportList();
        boolean ignoreAll = false;
        try {
            int numgrps = replist.getAll().size();
            System.out.println((new StringBuilder("Ignore all ")).append(numgrps)
                    .append(" filetypes for this instance? [y|n]:").toString());
            String ans = scan.readLine();
            if (ans.matches("[yY](es)?"))
                ignoreAll = true;
            for (Iterator iterator = replist.getAll().values().iterator(); iterator.hasNext();) {
                Report r = (Report) iterator.next();
                if (ignoreAll) {
                    ignores.add(r);
                } else {
                    System.out.println(r.getFileName());
                    System.out.print("Ignore this filetype? [y|n]:");
                    ans = scan.readLine();
                    if (ans.matches("[yY](es)?")) {
                        ignores.add(r);
                        System.out.println("OK!");
                    }
                }
            }

        } catch (IOException ioexception) {
        }
        System.out.println((new StringBuilder("Return ")).append(executeIgnores(ignores)).toString());
    }

    private int unignoreAll() {
        Statement st = null;
        int i;
        try {
            st = _conn.createStatement();
            i = st.executeUpdate("update tblVariableReport set ignoreFlag = 0 where ignoreFlag = 1");
            return i;
        } catch (SQLException sqlexception) {
        }
        return -1;
    }

    private int executeIgnores(ReportList ignores) {
        Statement st;
        String sql;
        st = null;
        sql = String.format("update tblVariableReport set ignoreFlag = 1 where reportID in (%s)",
                new Object[] { ignores });
        System.out.println(sql);
        int i;
        try {
            st = _conn.createStatement();
            i = st.executeUpdate(sql);
            return i;
        } catch (SQLException sqlexception) {
        }
        return -1;
    }

    private Calendar setCalDate(Calendar cal, String d) {
        Calendar dateCal = (Calendar) cal.clone();
        Calendar timeCal = (Calendar) dateCal.clone();
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        try {
            java.util.Date date = df.parse(d);
            timeCal.setTimeInMillis(date.getTime());
        } catch (ParseException parseexception) {
        }
        dateCal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
        dateCal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
        dateCal.set(Calendar.SECOND, timeCal.get(Calendar.SECOND));
        return dateCal;
    }

    public Calendar strToCal(String d) {
        Calendar c = Calendar.getInstance();
        int yr = 0;
        int mo = 0;
        int dt = 0;
        if (validDate(d)) {
            mo = Integer.parseInt(d.substring(0, 2));
            dt = Integer.parseInt(d.substring(3, 5));
            yr = Integer.parseInt(d.substring(6, 10));
            c.set(yr, mo - 1, dt);
            return c;
        } else {
            return null;
        }
    }

    public boolean validDate(String date) {
        return date.equals("Never Checked") || Pattern.matches("[0-9]+/[0-9]+/2[0-9]+", date);
    }

    private Calendar _cal;
    private Calendar _today;
    private String _dbhost;
    private String _dbname;
    private String _dbuser;
    private String _dbpass;
    private String _username;
    private String _emailFrom;
    private String _emailHost;
    private String _emailReplyTo;
    private String _keyFile;
    private String _remoteCommand;
    private int _numGroups;
    private Map _states;
    private MessageSender _ms;
    private Connection _conn;
    private PreparedStatement _ps;
    private String _checkDate;
}

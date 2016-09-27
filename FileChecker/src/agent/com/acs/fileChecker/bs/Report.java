
package com.acs.fileChecker.bs;

import java.util.Calendar;

public class Report
    implements Comparable
{

    public Report(int reportID, String file, Calendar start, Calendar end, boolean ignoreMissing)
    {
        _startCheck = Calendar.getInstance();
        _endCheck = Calendar.getInstance();
        _reportID = reportID;
        if(start != null)
        {
            _startCheck = start;
        } else
        {
            _startCheck.set(11, 0);
            _startCheck.set(12, 0);
            _startCheck.set(13, 0);
        }
        if(end != null)
        {
            _endCheck = end;
        } else
        {
            _endCheck.set(11, 23);
            _endCheck.set(12, 59);
            _endCheck.set(13, 59);
        }
        _fileName = file;
        _expectedCount = 1;
        _ignoreMissing = ignoreMissing;
    }
    
    public boolean canIgnoreMissing() {
    	return _ignoreMissing;
    }
    
    public int compareTo(Object o1)
    {
        return _fileName.compareTo(((Report)o1).getFileName());
    }

    public Calendar getStartCheck()
    {
        return _startCheck;
    }
    
    public String getReadableStartCheck()
    {
    	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    	return sdf.format(_startCheck.getTime());
    }

    public Calendar getEndCheck()
    {
        return _endCheck;
    }

    public void setStartCheck(Calendar c)
    {
        _startCheck = (Calendar)c.clone();
    }

    public void setEndCheck(Calendar c)
    {
        _endCheck = (Calendar)c.clone();
    }

    public Report(int reportID, String file)
    {
        this(reportID, file, null, null, false);
    }

    public String getFileName()
    {
        return _fileName;
    }

    public void setFileName(String name)
    {
        _fileName = name;
    }

    public int getExpectedCount()
    {
        return _expectedCount;
    }

    public void incExpectedCount()
    {
        _expectedCount++;
    }

    public int getID()
    {
        return _reportID;
    }

    public static Calendar normalizeDate(Calendar c)
    {
        c.set(0, 0, 1);
        return c;
    }

    public boolean shouldBeChecked()
    {
        Calendar now = Calendar.getInstance();
        Calendar start = _startCheck;
        Calendar end = _endCheck;
        if(now.before(_startCheck) && _endCheck.before(_startCheck) && now.before(_endCheck))
            return true;
        return now.after(_startCheck);
    }

    public String toString()
    {
        return _fileName;
    }

    public static void main(String args[])
    {
        Calendar start = Calendar.getInstance();
        Calendar end = (Calendar)start.clone();
        start.set(11, 11);
        end.set(11, 3);
        Report r = new Report(0, "blah", start, end, false);
        r.shouldBeChecked();
    }

    private String _fileName;
    private int _reportID;
    private int _expectedCount;
    private Calendar _startCheck;
    private Calendar _endCheck;
    private boolean _ignoreMissing;
}

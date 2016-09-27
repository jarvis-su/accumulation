package com.acs.fileChecker.bs;

import java.util.*;

// Referenced classes of package com.acs.fileChecker:
//            Report

public class ReportList
{

    public ReportList()
    {
        reports = new TreeMap();
    }

    public Report get(String fileName)
    {
        return (Report)reports.get(fileName);
    }

    /*public boolean add(Report r)
    {
        Report r2 = (Report)reports.get(r.getFileName());
        boolean isNew = r2 == null;
        if(isNew && r.shouldBeChecked())
            reports.put(r.getFileName(), r);
        else
        if(r.shouldBeChecked())
        {
            if(r.getEndCheck().after(r2.getEndCheck()))
                r2.setEndCheck(r.getEndCheck());
            if(r.getStartCheck().before(r2.getStartCheck()))
                r2.setStartCheck(r.getStartCheck());
            r2.incExpectedCount();
        }
        return isNew;
    }*/

    public boolean add(Report r)
    {
        Report r2 = (Report)reports.get(r.getFileName());
        boolean isNew = r2 == null;
        if(isNew)
            reports.put(r.getFileName(), r);
        else
        {
        	if(r.shouldBeChecked()) {
        		if(r.getEndCheck().after(r2.getEndCheck()))
        			r2.setEndCheck(r.getEndCheck());
        		if(r.getStartCheck().after(r2.getStartCheck()))
        			r2.setStartCheck(r.getStartCheck());
        		r2.incExpectedCount();
        	}
        }
        return isNew;
    }
    
    public boolean isEmpty()
    {
        return reports.isEmpty();
    }

    public int size()
    {
        return reports.size();
    }

    public Map getAll()
    {
        return reports;
    }

    public String toString()
    {
        String result = "";
        Iterator i = reports.values().iterator();
        if(i.hasNext())
            result = (new StringBuilder(String.valueOf(result))).append(((Report)i.next()).getID()).toString();
        while(i.hasNext()) 
        {
            result = (new StringBuilder(String.valueOf(result))).append(",").toString();
            result = (new StringBuilder(String.valueOf(result))).append(((Report)i.next()).getID()).toString();
        }
        return result;
    }

    private SortedMap reports;
}


package com.acs.fileChecker.bs;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;


public class FileCheckerMain3
{

    public static void main(String args[])
    {
        FileCheckerMain3 fcm;
        BufferedReader br;
        fcm = new FileCheckerMain3(false);
        br = new BufferedReader(new InputStreamReader(System.in));
        if(fcm.outputHTML())
        {
            System.out.println("<td width=60%><H4>File Name</H4></td><td width=10%><H4>Size</H4></td><td width=15%><H4>Modified</H4></td><td width=15%><H4>Error</H4></td>");
            System.err.println("<td width=60%><H4>File Name</H4></td><td width=10%><H4>Size</H4></td><td width=15%><H4>Modified</H4></td><td width=15%><H4>Error</H4></td>");
        }
        try
        {
            String line;
            while((line = br.readLine()) != null && !line.equals(".")) 
            {
                line = stripQuotes(line);
                String base = fcm.baseName(line);
                String name = fcm.fileName(line);
                String test[] = fcm.getFilesRegExp(new File(base), name);
                if(fcm.outputHTML())
                {
                    /*if(test.length == 0)
                    {
                        System.out.println((new StringBuilder("<td width=60%>")).append(base).append(name).append("</td><td width=10%><td width=15%></td><td width=15%> does not exist</td>").toString());
                        System.err.println((new StringBuilder("<td width=60%>")).append(base).append(name).append("</td><td width=10%><td width=15%></td><td width=15%> does not exist</td>").toString());
                    }*/
                    for(int j = 0; j < test.length; j++)
                    {
                        System.out.println((new StringBuilder("<td width=60%>")).append(base).append(test[j]).append("</td><td width=10%>").append(fcm.check((new StringBuilder(String.valueOf(base))).append(test[j]).toString())).append("</td>").toString());
                        System.err.println((new StringBuilder("<td width=60%>")).append(base).append(test[j]).append("</td><td width=10%>").append(fcm.check((new StringBuilder(String.valueOf(base))).append(test[j]).toString())).append("</td>").toString());
                    }

                } else
                {
                    /*if(test.length == 0)
                    {
                        System.out.println((new StringBuilder(String.valueOf(base))).append(name).append("\tError does not exist").toString());
                        System.err.println((new StringBuilder(String.valueOf(base))).append(name).append("\tError does not exist").toString());
                    }*/
                    for(int j = 0; j < test.length; j++)
                    {
                        System.out.println((new StringBuilder(String.valueOf(base))).append(test[j]).append("\t").append(fcm.check((new StringBuilder(String.valueOf(base))).append(test[j]).toString())).toString());
                        System.err.println((new StringBuilder(String.valueOf(base))).append(test[j]).append("\t").append(fcm.check((new StringBuilder(String.valueOf(base))).append(test[j]).toString())).toString());
                    }

                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.err.println(e.toString());
        }
        System.out.println("Session Closed.");
        System.err.println("Session Closed.");
    }

    public FileCheckerMain3()
    {
        date = new Date();
        HTML = false;
    }

    public FileCheckerMain3(boolean html)
    {
        HTML = html;
        date = new Date();
    }

    public boolean outputHTML()
    {
        return HTML;
    }

    public FileCheckerMain3(String s)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try
        {
            date = sdf.parse(s);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public String check(String s)
    {
        val = new Validate(s);
        StringBuffer stb = new StringBuffer(String.valueOf(val.getFileSize()));
        if(outputHTML())
            stb.append("</td><td width=15%>");
        else
            stb.append("\t");
        stb.append(val.getFileMod());
        if(outputHTML())
            stb.append("</td><td width=15%>");
        else
            stb.append("\t");
        String error = val.validateFile();
        if(!error.equals(""))
            stb.append((new StringBuilder("Error ")).append(error).toString());
        return stb.toString();
    }

    public String[] getFilesRegExp(File dir, final String regexp)
    {
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name)
            {
                return Pattern.matches(regexp, name);
            }
        };
        return dir.list(filter);
    }

    protected static String stripQuotes(String a)
    {
        if(a.startsWith("\""))
            a = a.substring(1, a.length());
        if(a.endsWith("\""))
            a = a.substring(0, a.length() - 1);
        return a;
    }

    public String baseName(String a)
    {
        int base_len = a.lastIndexOf('/');
        if(base_len == -1)
            return "./";
        else
            return a.substring(0, base_len + 1);
    }

    public String fileName(String a)
    {
        int base_len = a.lastIndexOf('/');
        if(base_len == -1)
            return a;
        else
            return a.substring(base_len + 1, a.length());
    }

    public String replaceDate(String source)
    {
        char first = '{';
        char last = '}';
        if(source != null)
        {
            int found = -1;
            int found2 = -1;
            int cur = 0;
            StringBuffer sb = new StringBuffer();
            while((found = source.indexOf(first, cur)) != -1) 
                if((found2 = source.indexOf(last, cur)) != -1)
                {
                    int subLen = found2 - found - 1;
                    sb.append(source.substring(cur, found));
                    SimpleDateFormat sdf = new SimpleDateFormat(source.substring(found + 1, found + subLen + 1));
                    sb.append(sdf.format(date));
                    cur = found2 + 1;
                } else
                {
                    return null;
                }
            sb.append(source.substring(cur));
            return sb.toString();
        } else
        {
            return null;
        }
    }

    private Validate val;
    private Date date;
    private boolean HTML;
}

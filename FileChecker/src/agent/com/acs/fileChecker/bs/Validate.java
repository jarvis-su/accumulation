package com.acs.fileChecker.bs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate
{

    public static void main(String args[])
    {
        File f = new File(args[0]);
        Validate v = new Validate(f);
        System.out.println((new StringBuilder(String.valueOf(f.toString()))).append(" ").append(v.validateFile()).toString());
    }

    public Validate(String fileName)
    {
        _rejectThreshold = 0.050000000000000003D;
        _recordThreshold = 0.050000000000000003D;
        _curFile = new File(fileName);
        _error = null;
        _state = "";
    }

    public Validate(File f)
    {
        _rejectThreshold = 0.050000000000000003D;
        _recordThreshold = 0.050000000000000003D;
        _curFile = f;
        _error = null;
        _state = "";
    }

    public Validate()
    {
        this(((File) (null)));
    }

    public String validateTXT()
    {
        return _state;
    }

    public String validateDAT()
    {
        return _state;
    }

    public String validateLOG()
    {
        if(!isTrailer("SQR: End of Run.") && !isTrailer("Jasper - End of Run."))
            _state = "has a bad trailer";
        return _state;
    }

    public String validateSUM()
    {
        Matcher matcher = null;
        if(getFirstLineLike(".*General Database Failure.*") != null) {
          _state = "General Database Failure";
        }
        else if((matcher = getLastLineLike("^\\s*Total records rejected\\s*:\\s*([1-9][0-9]*)")) != null)
            _state = String.format("rejected %s records", new Object[] {
                matcher.group(1)
            });
        return _state;
    }

    public String validateACH()
    {
        String processedC = "^\\s*Total Credits Processed\\s*:\\s*([0-9]*)";
        String rejectedC = "^\\s*Total Credits Rejected\\s*:\\s*([0-9]*)";
        String recordsPreprocessed = "^\\s*Total records (pre-processed|detail)\\s*:\\s*([0-9]*)";
        String recordsProcessed = "^\\s*Total records processed( \\(detail\\))?\\s*:\\s*([0-9]*)";
        String recordsRejected = "^\\s*Total records rejected\\s*:\\s*([0-9]*)";
        String processedD = "^\\s*Total Debits Processed\\s*:\\s*([0-9]*)";
        String rejectedD = "^\\s*Total Debits Rejected\\s*:\\s*([0-9]*)";
        String empty = "^\\s*NO FILE WAS PROCESSED.*";
        try
        {
            String s = getFirstLineLike(empty);
            //If the ACH file was empty, basically ignore it
            if(s != null)
            	return "";
            Matcher matcher = getLastLineLike(recordsRejected);
            double rej = Double.parseDouble(matcher.group(1));
            matcher = getLastLineLike(recordsProcessed);
            double pro = Double.parseDouble(matcher.group(2));
            matcher = getLastLineLike(recordsPreprocessed);
            double prePro = Double.parseDouble(matcher.group(2));
            double pctR = rej / prePro;
            if(pro + rej != prePro) {
            	_state = "Invalid Summary; Processed + Rejected != PreProcessed";
            	return _state;
            }
            else if(pctR >= _recordThreshold) {
            	_state = String.format("rejected %2.2f%% of total records!", new Object[] {
                    Double.valueOf(pctR * 100D)
                });
            	return _state;
            }
            
            matcher = getLastLineLike(processedC);
            pro = Double.parseDouble(matcher.group(1));
            matcher = getLastLineLike(rejectedC);
            rej = Double.parseDouble(matcher.group(1));
            double pctC = rej / (pro + rej);
            if(!Double.isNaN(pctC) && pctC > _rejectThreshold) {
                _state = String.format("rejected %2.2f%% of total credits!", new Object[] {
                    Double.valueOf(pctC * 100D)
                });
                return _state;
            }
            matcher = getLastLineLike(processedD);
            pro = Double.parseDouble(matcher.group(1));
            matcher = getLastLineLike(rejectedD);
            rej = Double.parseDouble(matcher.group(1));
            double pctD = rej / (pro + rej);
            if(!Double.isNaN(pctD) && pctD > pctC && pctD > _rejectThreshold) {
                _state = String.format("rejected %2.2f%% of total debits!", new Object[] {
                    Double.valueOf(pctD * 100D)
                });
                return _state;
            }
        }
        catch(Exception e)
        {
            _state = "monitor could not determine number of rejects";
        }
        return _state;
    }
    
    //Open a rule file
    /**
     * Order of operations:
     * exists		boolean(default = true)
     * sizeAtLeast	Integer(default = 0)
     */
    public String validateCustom(String customname)
    {
    	Properties props = new Properties();
    	URL url = ClassLoader.getSystemResource(customname + ".rules");
    	try{
    		props.load(new FileInputStream(new File(url.getFile())));
    	}catch(IOException e){}
    	String tmp = props.getProperty("check.exists", "true");
    	if(!_curFile.exists()) {
        	if(tmp.equals("true"))
    			return "does not exist";
        	else
        		return "";
    	}

    	tmp = props.getProperty("check.sizeAtLeast");
    	if(tmp != null)
    		if(_curFile.length() < Integer.parseInt(tmp))
    			return "is smaller than expected";
    	
    	tmp = props.getProperty("check.sizeAtMost");
    	if(tmp != null)
    		if(_curFile.length() > Integer.parseInt(tmp))
    			return "is larger than expected";
    		
    	return "";
    }

    public String validatePDF()
    {
        if(!isTrailer(".*%%EOF\\s*"))
            _state = "has a bad trailer";
        return _state;
    }
    
    public String validateVAREJ()
    {
    	if(getLastLineLike(".*HT99$") != null)
    		_state = "HT99 error detected";
    	return _state;
    }

    public String validateCARDMAILER()
    {
        if(!isTrailer("T[0-9]*.*"))
            _state = "has a bad trailer";
        return _state;
    }
    
    public String validateCARDMAILDATE()
    {
    	if(getFirstLineLike("Error") != null)
    		_state = "Please Contact OM";
    	return _state;
    }

    public String validateGeneral()
    {
        String retCode;
        if(!_curFile.exists())
            retCode = "doesn't exist";
        else
        if(_curFile.length() == 0L)
            retCode = "is zero length";
        else
            retCode = "";
        return retCode;
    }

    public int getFileType()
    {
        String name = _curFile.toString();
        String type = name.substring(name.lastIndexOf('.') + 1).toLowerCase();
        int t;
        if(name.endsWith("CARDMAILER"))
            t = FTYPE_CARDMAILER;
        else
        if(name.endsWith("CARDMAILDATE.summary"))
                t = FTYPE_CARDMAILDATE;
        else
        if(name.endsWith("ACHDEP.summary"))
            t = FTYPE_ACHDEP;
        else if(name.matches(".*VA.*rej"))
        	t = FTYPE_VAREJ;
        else
        if(type.equals("txt"))
            t = FTYPE_TXT;
        else
        if(type.equals("dat"))
            t = FTYPE_DAT;
        else
        if(type.equals("pdf"))
            t = FTYPE_PDF;
        else
        if(type.equals("log"))
            t = FTYPE_LOG;
        else
        if(type.equals("summary"))
            t = FTYPE_SUM;
        else
            t = 0;
        return t;
    }
    
    public String validateFile() 
    {
        int type = getFileType();
        String ret = validateGeneral();
        if(!ret.equals("")) {
        	//ACH Files may or may not exist
        	if(type == FTYPE_ACHDEP && !_curFile.exists())
        		return "";
        	return ret;
        }
        switch(type)
        {
        case FTYPE_CARDMAILDATE:
        	ret = validateCARDMAILDATE();
        	break;
        case FTYPE_CARDMAILER:
            ret = validateCARDMAILER();
            break;
        case FTYPE_ACHDEP:
            ret = validateACH();
            break;
        case FTYPE_TXT:
            ret = validateTXT();
            break;
        case FTYPE_DAT:
            ret = validateDAT();
            break;
        case FTYPE_PDF:
            ret = validatePDF();
            break;
        case FTYPE_SUM:
            ret = validateSUM();
            break;
        case FTYPE_LOG:
            ret = validateLOG();
            break;
        case FTYPE_VAREJ:
        	ret = validateVAREJ();
        	break;
        }
        return ret;
    }

    public long getFileSize()
    {
        long tmp = _curFile.length();
        return tmp;
    }

    public String getFileMod()
    {
        SimpleDateFormat time = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date d = new Date(_curFile.lastModified());
        return time.format(d);
    }

    private String getFirstLineLike(String str)
    {
        String ret = null;
        String line = null;
        try
        {
            raf = new RandomAccessFile(_curFile, "r");
            for(line = raf.readLine(); line != null; line = raf.readLine())
            {
                if(!line.matches(str))
                    continue;
                ret = line;
                break;
            }

            raf.close();
        }
        catch(IOException ioexception) { }
        return ret;
    }

    private Matcher getLastLineLike(String str)
    {
        String line = null;
        Pattern pattern = Pattern.compile(str);
        Matcher matcher = null;
        try
        {
            raf = new RandomAccessFile(_curFile, "r");
            raf.seek(_curFile.length() - 1L);
            for(line = readLineReverse(); line != null;)
            {
                matcher = pattern.matcher(line);
                if(matcher.matches())
                    break;
                try
                {
                    line = readLineReverse();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                matcher = null;
            }

            raf.close();
        }
        catch(Exception exception) { }
        return matcher;
    }

    private boolean isTrailer(String str)
    {
        boolean match = false;
        try
        {
            raf = new RandomAccessFile(_curFile, "r");
            raf.seek(_curFile.length() - 1L);
            String line = readLineReverse();
            if(line.matches(str))
                match = true;
            raf.close();
        }
        catch(Exception exception) { }
        return match;
    }

    public String getError()
    {
        return _error;
    }

    public String readLineReverse()
        throws Exception
    {
        long position = raf.getFilePointer();
        String finalLine = "";
        boolean justStarting = true;
        if(position == 0L)
            return null;
        do
        {
            if(position < 0L)
            {
                raf.seek(0L);
                break;
            }
            raf.seek(position);
            int thisCode = raf.readByte();
            char thisChar = (char)thisCode;
            if(thisCode == 13 || thisCode == 10)
            {
                raf.seek(position - 1L);
                int nextCode = raf.readByte();
                if(thisCode == 10 && nextCode == 13 || thisCode == 13 && nextCode == 10)
                    position--;
                else
                if(justStarting)
                {
                    position--;
                    finalLine = (new StringBuilder(String.valueOf((char)nextCode))).append(finalLine).toString();
                }
                if(!justStarting)
                {
                    position--;
                    break;
                }
            } else
            {
                finalLine = (new StringBuilder(String.valueOf(thisChar))).append(finalLine).toString();
            }
            position--;
            justStarting = false;
        } while(true);
        return finalLine;
    }

    private String _error;
    private String _state;
    private RandomAccessFile raf;
    private File _curFile;
    private double _rejectThreshold;
    private double _recordThreshold;
    public static final int FTYPE_OTHER = 0;
    public static final int FTYPE_TXT = 1;
    public static final int FTYPE_DAT = 2;
    public static final int FTYPE_PDF = 4;
    public static final int FTYPE_LOG = 8;
    public static final int FTYPE_SUM = 16;
    public static final int FTYPE_CARDMAILER = 32;
    public static final int FTYPE_CARDMAILDATE = 48;
    public static final int FTYPE_ACHDEP = 64;
    public static final int FTYPE_VAREJ = 128;
}

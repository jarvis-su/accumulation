package com.jarvis.security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Created by Jarvis on 4/11/16.
 */
public class CrossSiteScriptWrapper extends HttpServletRequestWrapper {


    /**
     * @param request
     */
    public CrossSiteScriptWrapper(HttpServletRequest request) {
        super(request);
    }


    private static Pattern[] patterns = new Pattern[]{
            Pattern.compile("<")
            , Pattern.compile("&#x0{0,5}3c;?", Pattern.CASE_INSENSITIVE)
            , Pattern.compile("&#0{0,5}60;?")
            , Pattern.compile("\\\\x3c", Pattern.CASE_INSENSITIVE)
            , Pattern.compile("\\\\u003c", Pattern.CASE_INSENSITIVE)
            , Pattern.compile(">")
            , Pattern.compile("&#x0{0,5}3E;?", Pattern.CASE_INSENSITIVE)
            , Pattern.compile("&#0{0,5}62;?")
            , Pattern.compile("\\\\x3e", Pattern.CASE_INSENSITIVE)
            , Pattern.compile("\\\\u003e", Pattern.CASE_INSENSITIVE)
            , Pattern.compile("eval\\s*\\((.*?)\\)", Pattern.CASE_INSENSITIVE)
            , Pattern.compile("javascript:\\s*(.*?)", Pattern.CASE_INSENSITIVE)
            , Pattern.compile("script", Pattern.CASE_INSENSITIVE)
            , Pattern.compile("!")
    };


    public static String sanitizeString(String value) {
        String orig = value;

        if (value == null) {
            return value;
        }

        for (int i = 0; i < patterns.length; i++) {
            Matcher matcher = patterns[i].matcher(value);
            value = matcher.replaceAll("");
        }

        //recall the method if any replacement was performed
        if (!value.equals(orig)) {
            value = sanitizeString(value);
        }
        return value;
    }


    @Override
	public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];


        for (int i = 0; i < count; i++) {
            encodedValues[i] = sanitizeString(values[i]);
        }


        return encodedValues;
    }


    @Override
	public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }
        return sanitizeString(value);
    }

    @Override
	public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null) {
            return null;
        }
        return sanitizeString(value);
    }


}

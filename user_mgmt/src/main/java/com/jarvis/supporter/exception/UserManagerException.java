package com.jarvis.supporter.exception;

/**
 * Created by Jarvis on 4/20/16.
 */
public class UserManagerException extends RuntimeException {
    public static final String CR_LF = System.getProperty("line.separator");
    protected int _code;
    protected Throwable _originalError;

    public UserManagerException(int code, String msg) {
        this(code, msg, null);
    }

    public UserManagerException(int code, String msg, Throwable originalError) {
        super(msg);
        _code = code;
        _originalError = originalError;
    }

    public int getCode() {
        return _code;
    }

    public Throwable getOriginalError() {
        return _originalError;
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append(this.getClass().getName());
        result.append(" Error code:");
        result.append(_code);
        result.append(CR_LF);
        result.append(super.toString());
        result.append(CR_LF);
        return result.toString();
    }

}

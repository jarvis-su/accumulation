package com.cts.exception;

public class BusinessException extends Exception {

    private static final long serialVersionUID = 1L;

    public BusinessException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public BusinessException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}

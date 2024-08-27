package com.ztftrue.app;


public class MyException extends RuntimeException {
    String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public MyException() {
        super();
    }

    public MyException(String message) {
        super(message);
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyException(String code, String message) {
        super(message);
        this.code = code;
    }

    public MyException(Throwable cause) {
        super(cause);
    }

}

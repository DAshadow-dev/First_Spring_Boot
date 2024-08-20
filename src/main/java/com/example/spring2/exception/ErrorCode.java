package com.example.spring2.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"Uncategorized exception"),
    INVALID_KEY(1001,"Invalid message key"),
    USER_EXISTED(1002,"User already exists"),
    USERNAME_INVALID(1003,"User name must be at least 3 characters"),
    PASSWORD_INVALID(1004,"Password must be at least 8 characters")
    ;

    private int code;
    private String message;
    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}

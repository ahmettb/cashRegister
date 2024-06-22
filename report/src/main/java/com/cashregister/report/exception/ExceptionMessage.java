package com.cashregister.report.exception;

public class ExceptionMessage {
   public String timestamp;
    public   int staus;
    public String error;
    public String message;
    public String path;

    public ExceptionMessage( String error, int staus,String timestamp, String message, String path) {
        this.staus = staus;
        this.error = error;
        this.timestamp = timestamp;
        this.message = message;
        this.path = path;
    }
}

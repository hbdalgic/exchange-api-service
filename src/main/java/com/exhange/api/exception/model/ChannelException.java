package com.exhange.api.exception.model;

public class ChannelException extends Exception {
    private String errorCode;
    private String errorMessage;
    private int httpStatusCode;

    public ChannelException(String message, int httpCode) {
        super(message);
        this.httpStatusCode = httpCode;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }


}

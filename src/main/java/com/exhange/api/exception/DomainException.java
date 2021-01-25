package com.exhange.api.exception;

public class DomainException extends RuntimeException{
    private String code;
    private String message;

    public DomainException(String code,String message){
        this.code = code;
        this.message = message;
    }

    public DomainException(){

    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

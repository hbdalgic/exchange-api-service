package com.exhange.api.exception.model;

public enum ErrorEnum {
    ERROR("EX00", "Internal Servcer Error!"),
    INVALID_REQUEST_MODEL("EX01", "invalid request model"),
    EXCEPTION_FROM_CHANNEL("EX02", "exception from channel"),
    INVALID_AMOUNT("EX03", "invalid amount"),
    NOT_FOUND_DATA("EX04", "Not Found data!");

    private String code;
    private String message;

    ErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

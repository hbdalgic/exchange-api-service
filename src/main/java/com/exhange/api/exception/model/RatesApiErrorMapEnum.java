package com.exhange.api.exception.model;

public enum RatesApiErrorMapEnum {
    ERROR(000, ErrorEnum.ERROR),
    INVALID_REQUEST_MODEL(400, ErrorEnum.INVALID_REQUEST_MODEL);

    private int code;
    private final ErrorEnum error;

    RatesApiErrorMapEnum(int code, ErrorEnum error) {
        this.code = code;
        this.error = error;
    }

    public static RatesApiErrorMapEnum getByCode(int code) {
        RatesApiErrorMapEnum[] values = RatesApiErrorMapEnum.values();
        for (RatesApiErrorMapEnum value : values) {
            if (value.code == code) {
                return value;
            }
        }
        return RatesApiErrorMapEnum.ERROR;
    }

    public int getCode() {
        return code;
    }

    public ErrorEnum getError() {
        return error;
    }

}

package com.exhange.api.utils;
import com.exhange.api.exception.model.ErrorEnum;
import org.apache.commons.lang3.StringUtils;
import com.exhange.api.model.ProtocolEnum;

public class ExchangeUtils {

    public static String getExceptionCode(ErrorEnum errorEnum) {
        String code = errorEnum.getCode();
        code = code != null && code.length() > 0 ? StringUtils.leftPad(code, 4, '0') : "????";
        return "error." + errorEnum.name() + "_" + code;
    }
}

package com.exhange.api.utils;

import com.exhange.api.exception.model.ErrorEnum;
import com.exhange.api.model.BaseResponseModel;
import com.exhange.api.model.reqres.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class RequestUtils {

    public static String getCurrentUserIP(HttpServletRequest request) {
        if (request == null)
            return null;
        return request.getRemoteAddr();
    }

    public static Boolean isNullOrEmpty(String text) {
        return text == null || text.trim().length() == 0;
    }

    public static void controlGetRateListRequest(CalculateListRequestModel request, CalculateListResponseModel response) {
        response.setSuccess(true);
        if(request == null){
            response.setSuccess(false);
            response.setErrorCode(ExchangeUtils.getExceptionCode(ErrorEnum.INVALID_REQUEST_MODEL));
            response.setErrorMessage(ErrorEnum.INVALID_REQUEST_MODEL.getMessage() + "-- Request can not null");
        }
        if(request.getSize() <= 0 ){
            response.setSuccess(false);
            response.setErrorCode(ExchangeUtils.getExceptionCode(ErrorEnum.INVALID_REQUEST_MODEL));
            response.setErrorMessage(ErrorEnum.INVALID_REQUEST_MODEL.getMessage() + "-- Size can not zero");
        }
        if(request.getTrxDate() == null && isNullOrEmpty(request.getTrxId())){
            response.setSuccess(false);
            response.setErrorCode(ExchangeUtils.getExceptionCode(ErrorEnum.INVALID_REQUEST_MODEL));
            response.setErrorMessage(ErrorEnum.INVALID_REQUEST_MODEL.getMessage() + "-- TrxId or TrxDate can not null");
        }
    }
}

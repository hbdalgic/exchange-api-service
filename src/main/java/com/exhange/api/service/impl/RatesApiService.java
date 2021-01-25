package com.exhange.api.service.impl;

import com.exhange.api.config.RatesProperties;
import com.exhange.api.exception.DomainException;
import com.exhange.api.exception.model.ChannelException;
import com.exhange.api.exception.model.ErrorEnum;
import com.exhange.api.exception.model.RatesApiErrorMapEnum;
import com.exhange.api.model.reqres.ExchangeRateRequestModel;
import com.exhange.api.model.reqres.ExchangeRateResponseModel;
import com.exhange.api.model.ProtocolEnum;
import com.exhange.api.model.reqres.RateChannelResponseModel;
import com.exhange.api.service.IExchangeRateService;
import com.exhange.api.service.IHttpClient;
import com.exhange.api.utils.ExchangeUtils;
import com.exhange.api.utils.RequestUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class RatesApiService implements IExchangeRateService {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private RatesProperties properties;
    private IHttpClient httpClient;

    public RatesApiService(IHttpClient httpClient, RatesProperties properties) {
        this.httpClient = httpClient;
        this.properties = properties;
    }

    @Override
    public ExchangeRateResponseModel getRate(ExchangeRateRequestModel request) {
        ExchangeRateResponseModel response = new ExchangeRateResponseModel();

        String paramData = "";
        if (request.getDate() == null) {
            paramData = "latest";
        } else {
            paramData = dateTimeFormatter.format(request.getDate()) + "/";
        }
        paramData += request.getFromCurrency() + "/" + request.getToCurrency();

        try {
            RateChannelResponseModel provResponse = httpClient.get(properties.getUrl(), paramData, ProtocolEnum.Rate_SymbolAndBase, RateChannelResponseModel.class);

            if (provResponse == null || provResponse.getRates() == null || provResponse.getRates().get(request.getToCurrency()) == null) {
                throw new DomainException(ExchangeUtils.getExceptionCode(ErrorEnum.NOT_FOUND_DATA), ErrorEnum.NOT_FOUND_DATA.getMessage());
            }
            response.setRate(provResponse.getRates().get(request.getToCurrency()));
            response.setDate(provResponse.getDate());
            response.setSuccess(true);
            response.setFromCurrency(request.getFromCurrency());
            response.setToCurrency(request.getToCurrency());

        } catch (ChannelException ex) {
            RatesApiErrorMapEnum errorEnum = RatesApiErrorMapEnum.getByCode(ex.getHttpStatusCode());
            response.setErrorCode(ExchangeUtils.getExceptionCode(ErrorEnum.NOT_FOUND_DATA));
            response.setErrorMessage(errorEnum.getError().getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    public IHttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(IHttpClient httpClient) {
        this.httpClient = httpClient;
    }

}

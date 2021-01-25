package com.exhange.api.service.impl;

import com.exhange.api.entity.ExchangeEntity;
import com.exhange.api.exception.DomainException;
import com.exhange.api.exception.model.ErrorEnum;
import com.exhange.api.model.reqres.CalculateRateRequestModel;
import com.exhange.api.model.reqres.CalculateRateResponseModel;
import com.exhange.api.model.reqres.ExchangeRateRequestModel;
import com.exhange.api.model.reqres.ExchangeRateResponseModel;
import com.exhange.api.repository.IExchangeDBService;
import com.exhange.api.service.ICalculateRateService;
import com.exhange.api.service.IExchangeRateService;
import com.exhange.api.utils.RequestUtils;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Service
public class CalculateRateService implements ICalculateRateService {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private IExchangeRateService exchangeRateService;
    private IExchangeDBService exchangeDBService;

    @Autowired
    private Gson gson = new Gson();

    public CalculateRateService(IExchangeRateService exchangeRateService, IExchangeDBService exchangeDBService) {
        this.exchangeRateService = exchangeRateService;
        this.exchangeDBService = exchangeDBService;
    }

    @Override
    public CalculateRateResponseModel calculate(CalculateRateRequestModel request) {

        CalculateRateResponseModel response = new CalculateRateResponseModel();

        response.setTrxId(UUID.randomUUID().toString());
        response.setFromCurrency(request.getFromCurrency());
        response.setToCurrency(request.getToCurrency());
        response.setAmount(request.getAmount());
        response.setDate(request.getDate());

        ExchangeEntity save = exchangeDBService.save(response);
        if (save == null) {
            response.setSuccess(false);
            response.setErrorCode(ErrorEnum.ERROR.getCode());
            response.setErrorMessage(ErrorEnum.ERROR.getMessage());
            LOG.debug("::calculate DB crud error req:{} res:{}", gson.toJson(request), gson.toJson(response));
            return response;
        }
        ExchangeRateRequestModel exRequest = new ExchangeRateRequestModel();
        exRequest.setDate(request.getDate());
        exRequest.setFromCurrency(request.getFromCurrency());
        exRequest.setToCurrency(request.getToCurrency());

        ExchangeRateResponseModel exRate = exchangeRateService.getRate(exRequest);
        if (!exRate.isSuccess()) {
            response.setSuccess(false);
            response.setErrorCode(exRate.getErrorCode());
            response.setErrorMessage(exRate.getErrorMessage());
            LOG.debug("::calculate Not Found exRate error req:{} exRate:{}", gson.toJson(request), gson.toJson(exRate));
            exchangeDBService.update(save,response);
            return response;
        }
        BigDecimal rate = exRate.getRate();
        BigDecimal calculateAmount = request.getAmount().multiply(rate).setScale(6, RoundingMode.HALF_UP);

        response.setRate(rate);
        response.setCalculateAmount(calculateAmount);
        response.setSuccess(true);

        exchangeDBService.update(save,response);
        return response;
    }
}

package com.exhange.api.service;

import com.exhange.api.model.reqres.ExchangeRateRequestModel;
import com.exhange.api.model.reqres.ExchangeRateResponseModel;

public interface IExchangeRateService {

    ExchangeRateResponseModel getRate(ExchangeRateRequestModel request);
}

package com.exhange.api.service;

import com.exhange.api.model.reqres.CalculateRateRequestModel;
import com.exhange.api.model.reqres.CalculateRateResponseModel;

public interface ICalculateRateService {
    CalculateRateResponseModel calculate(CalculateRateRequestModel request);
}

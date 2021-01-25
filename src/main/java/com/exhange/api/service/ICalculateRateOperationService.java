package com.exhange.api.service;

import com.exhange.api.model.reqres.CalculateListRequestModel;
import com.exhange.api.model.reqres.CalculateListResponseModel;

public interface ICalculateRateOperationService {
    CalculateListResponseModel getCalculatedRateList(CalculateListRequestModel request);
}

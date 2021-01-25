package com.exhange.api.service.impl;

import com.exhange.api.entity.ExchangeEntity;
import com.exhange.api.exception.DomainException;
import com.exhange.api.exception.model.ErrorEnum;
import com.exhange.api.model.reqres.CalculateListRequestModel;
import com.exhange.api.model.CalculateListModel;
import com.exhange.api.model.reqres.CalculateListResponseModel;
import com.exhange.api.repository.IExchangeDBService;
import com.exhange.api.service.ICalculateRateOperationService;
import com.exhange.api.utils.RequestUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalculateRateOperationService implements ICalculateRateOperationService {

    private IExchangeDBService exchangeDBService;
    @Autowired
    private Gson gson = new Gson();

    public CalculateRateOperationService( IExchangeDBService exchangeDBService) {
        this.exchangeDBService = exchangeDBService;
    }

    @Override
    public CalculateListResponseModel getCalculatedRateList(CalculateListRequestModel request) {
        CalculateListResponseModel response = new CalculateListResponseModel();
        RequestUtils.controlGetRateListRequest(request,response);
        if(!response.isSuccess()){
            throw new DomainException(response.getErrorCode(),response.getErrorMessage());
        }
        Pageable paging = PageRequest.of(request.getPage(), request.getSize());
        List<ExchangeEntity> responseDb= exchangeDBService.getCalculatedRateList(request.getTrxId(),request.getTrxDate(),paging);

        if(!CollectionUtils.isEmpty(responseDb)){
            List<CalculateListModel> trxList =responseDb.stream().map(x -> {
                CalculateListModel res = new CalculateListModel();
                res.setRate(x.getRate());
                res.setCalculatedAmount(x.getCalculatedAmount());
                res.setAmount(x.getAmount());
                res.setFromCurrency(x.getFromCurrency());
                res.setToCurrency(x.getToCurrency());
                res.setTrxDate(x.getTrxDateTime());
                res.setTrxId(x.getTrxId());
                return res;
            }).collect(Collectors.toList());
            response.setTrxList(trxList);
            return response;
        }else{
            response.setSuccess(false);
            response.setErrorCode(ErrorEnum.NOT_FOUND_DATA.getCode());
            response.setErrorMessage(ErrorEnum.NOT_FOUND_DATA.getMessage());
        }

        return response;
    }
}

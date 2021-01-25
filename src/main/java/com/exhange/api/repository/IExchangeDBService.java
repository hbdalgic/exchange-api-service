package com.exhange.api.repository;

import com.exhange.api.entity.ExchangeEntity;
import com.exhange.api.model.reqres.CalculateRateResponseModel;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface IExchangeDBService {
    ExchangeEntity save(CalculateRateResponseModel request);
    ExchangeEntity update(ExchangeEntity entityRequest, CalculateRateResponseModel request);
    List<ExchangeEntity> getCalculatedRateList(String trxId, LocalDate trxDate, Pageable pageable);
}

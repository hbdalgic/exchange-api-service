package com.exchange.test;


import com.exhange.api.config.RatesProperties;
import com.exhange.api.exception.DomainException;
import com.exhange.api.exception.model.ErrorEnum;
import com.exhange.api.model.ProtocolEnum;
import com.exhange.api.model.reqres.CalculateRateRequestModel;
import com.exhange.api.model.reqres.CalculateRateResponseModel;
import com.exhange.api.model.reqres.ExchangeRateRequestModel;
import com.exhange.api.model.reqres.ExchangeRateResponseModel;
import com.exhange.api.service.ICalculateRateService;
import com.exhange.api.service.IExchangeRateService;
import com.exhange.api.service.IHttpClient;
import com.exhange.api.service.impl.HttpClientService;
import com.exhange.api.service.impl.RatesApiService;
import com.exhange.api.utils.ExchangeUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class ExchangeApiApplicationTests {

    @Mock
    private IExchangeRateService exchangeRateService;

    @Mock
    private ICalculateRateService calculateRateService;

    @Mock
    private IHttpClient httpClient;

    @Mock
    private RatesProperties ratesProperties;


    @Test
    void testNotFoundRateCase(){

        IExchangeRateService exchangeRateService = new RatesApiService(httpClient,ratesProperties);

        ExchangeRateRequestModel request = new ExchangeRateRequestModel();
        request.setFromCurrency("EUR");
        request.setToCurrency(null);
        request.setDate(LocalDate.now());

        DomainException apiException = Assertions.assertThrows(DomainException.class, () -> exchangeRateService.getRate(request));
        Assertions.assertEquals(ExchangeUtils.getExceptionCode(ErrorEnum.NOT_FOUND_DATA), apiException.getCode());
    }


    @Test
    void testCalculateCase(){
        String fromCurrency = "TRY";
        String toCurrency = "EUR";
        BigDecimal amount = new BigDecimal("100");
        BigDecimal rate = new BigDecimal("2");
        BigDecimal calculatedAmount = amount.multiply(rate);

        CalculateRateRequestModel request = new CalculateRateRequestModel();
        request.setFromCurrency(fromCurrency);
        request.setToCurrency(toCurrency);
        request.setDate(LocalDate.now());
        request.setAmount(amount);


        CalculateRateResponseModel calculatedRate = new CalculateRateResponseModel();
        calculatedRate.setFromCurrency(fromCurrency);
        calculatedRate.setToCurrency(toCurrency);
        calculatedRate.setRate(rate);
        calculatedRate.setSuccess(false);
        calculatedRate.setAmount(amount);
        calculatedRate.setRate(rate);
        calculatedRate.setCalculateAmount(calculatedAmount);

        Mockito.when(calculateRateService.calculate(request)).thenReturn(calculatedRate);

        CalculateRateResponseModel response = calculateRateService.calculate(request);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(rate,response.getRate());
        Assertions.assertEquals(fromCurrency,response.getFromCurrency());
        Assertions.assertEquals(toCurrency,response.getToCurrency());
        Assertions.assertEquals(calculatedAmount,response.getCalculateAmount());
        Assertions.assertEquals(amount,response.getAmount());
    }
    @Test
    void testGetRateCase(){
        String fromCurrency = "TRY";
        String toCurrency = "EUR";
        BigDecimal rate = new BigDecimal("0.11");

        ExchangeRateRequestModel request = new ExchangeRateRequestModel();
        request.setFromCurrency(fromCurrency);
        request.setToCurrency(toCurrency);
        request.setDate(LocalDate.now());


        ExchangeRateResponseModel exchangeRate = new ExchangeRateResponseModel();
        exchangeRate.setFromCurrency(fromCurrency);
        exchangeRate.setToCurrency(toCurrency);
        exchangeRate.setRate(rate);
        exchangeRate.setSuccess(true);

        Mockito.when(exchangeRateService.getRate(request)).thenReturn(exchangeRate);

        ExchangeRateResponseModel response = exchangeRateService.getRate(request);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(rate,response.getRate());
        Assertions.assertEquals(fromCurrency,response.getFromCurrency());
        Assertions.assertEquals(toCurrency,response.getToCurrency());
    }
}

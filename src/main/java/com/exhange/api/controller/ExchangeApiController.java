package com.exhange.api.controller;

import com.exhange.api.model.reqres.*;
import com.exhange.api.service.ICalculateRateOperationService;
import com.exhange.api.service.IExchangeRateService;
import com.exhange.api.service.ICalculateRateService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
public class ExchangeApiController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final IExchangeRateService exchangeRateService;
    private final ICalculateRateService calculateRateService;
    private final ICalculateRateOperationService calculateRateOperationService;

    public ExchangeApiController(IExchangeRateService exchangeRateService, ICalculateRateService calculateRateService,
                                 ICalculateRateOperationService calculateRateOperationService) {
        this.exchangeRateService = exchangeRateService;
        this.calculateRateService = calculateRateService;
        this.calculateRateOperationService = calculateRateOperationService;
    }

    @GetMapping("/stat")
    public String stat() {
        return "OK";
    }

    @ApiIgnore
    @GetMapping("/")
    public RedirectView home() {
        return new RedirectView("swagger-ui/index.html");
    }

    @PostMapping("/rate")
    public ExchangeRateResponseModel exchangeRate(@Valid @RequestBody ExchangeRateRequestModel request) {
        ExchangeRateResponseModel response = exchangeRateService.getRate(request);
        return response;
    }

    @PostMapping("/calculate-rate")
    public CalculateRateResponseModel calculateRate(@Valid @RequestBody CalculateRateRequestModel request) {
        return calculateRateService.calculate(request);
    }

    @PostMapping("/calculate-rate/list")
    public CalculateListResponseModel calculateRateList(@Valid @RequestBody CalculateListRequestModel request) {
        return calculateRateOperationService.getCalculatedRateList(request);
    }
}

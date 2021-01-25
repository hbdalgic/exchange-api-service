package com.exhange.api.model.reqres;

import com.exhange.api.model.BaseRequestModel;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ExchangeRateRequestModel extends BaseRequestModel {
    @NotNull(message = "fromCurrency can not be null")
    private String fromCurrency;
    @NotNull(message = "toCurrency can not be null")
    private String toCurrency;
    private LocalDate date;

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}

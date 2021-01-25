package com.exhange.api.model.reqres;

import com.exhange.api.model.BaseRequestModel;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.Min;
import java.time.LocalDate;

public class CalculateListRequestModel extends BaseRequestModel {
    private String trxId;
    private LocalDate trxDate;
    private int page;
    @Min(value = 0, message = "must be bigger zero(0)!")
    private int size;

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId;
    }

    public LocalDate getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(LocalDate trxDate) {
        this.trxDate = trxDate;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }
}

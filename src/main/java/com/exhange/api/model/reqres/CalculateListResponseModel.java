package com.exhange.api.model.reqres;

import com.exhange.api.model.BaseResponseModel;
import com.exhange.api.model.CalculateListModel;

import java.util.List;

public class CalculateListResponseModel  extends BaseResponseModel {
    private List<CalculateListModel> trxList;
    public List<CalculateListModel> getTrxList() {
        return trxList;
    }

    public void setTrxList(List<CalculateListModel> trxList) {
        this.trxList = trxList;
    }


}

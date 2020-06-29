
package com.cscodetech.freshfastfooddeliveryboy.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Ostatus {

    @SerializedName("order_data")
    private OrderData mOrderData;
    @SerializedName("ResponseCode")
    private String mResponseCode;
    @SerializedName("ResponseMsg")
    private String mResponseMsg;
    @SerializedName("Result")
    private String mResult;

    public OrderData getOrderData() {
        return mOrderData;
    }

    public void setOrderData(OrderData orderData) {
        mOrderData = orderData;
    }

    public String getResponseCode() {
        return mResponseCode;
    }

    public void setResponseCode(String responseCode) {
        mResponseCode = responseCode;
    }

    public String getResponseMsg() {
        return mResponseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        mResponseMsg = responseMsg;
    }

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        mResult = result;
    }

}

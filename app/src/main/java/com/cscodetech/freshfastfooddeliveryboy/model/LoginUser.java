
package com.cscodetech.freshfastfooddeliveryboy.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class LoginUser {

    @SerializedName("ResponseCode")
    private String mResponseCode;
    @SerializedName("ResponseMsg")
    private String mResponseMsg;
    @SerializedName("Result")
    private String mResult;
    @SerializedName("rider")
    private User mUser;
    @SerializedName("d_charge")
    private int dCharge;

    @SerializedName("currency")
    private String currency;

    public int getdCharge() {
        return dCharge;
    }

    public void setdCharge(int dCharge) {
        this.dCharge = dCharge;
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

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }


    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

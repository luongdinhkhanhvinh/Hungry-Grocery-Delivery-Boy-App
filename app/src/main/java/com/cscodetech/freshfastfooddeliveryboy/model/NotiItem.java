
package com.cscodetech.freshfastfooddeliveryboy.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class NotiItem {

    @SerializedName("date")
    private String mDate;
    @SerializedName("id")
    private String mId;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("rid")
    private String mRid;

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public String getRid() {
        return mRid;
    }

    public void setRid(String rid) {
        mRid = rid;
    }

}

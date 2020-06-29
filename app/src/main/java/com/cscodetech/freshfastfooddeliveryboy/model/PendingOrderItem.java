
package com.cscodetech.freshfastfooddeliveryboy.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class PendingOrderItem implements Parcelable {

    @SerializedName("astatus")
    private String mAstatus;
    @SerializedName("d_charge")
    private String mDCharge;
    @SerializedName("delivery")
    private String mDelivery;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("mobile")
    private String mMobile;
    @SerializedName("name")
    private String mName;
    @SerializedName("odate")
    private String mOdate;
    @SerializedName("p_method")
    private String mPMethod;
    @SerializedName("pickup")
    private String mPickup;
    @SerializedName("productinfo")
    private ArrayList<Productinfo> mProductinfo;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("timesloat")
    private String mTimesloat;
    @SerializedName("total")
    private String mTotal;

    @SerializedName("sign")
    private String sign;

    @SerializedName("orderid")
    private String orderid;

    protected PendingOrderItem(Parcel in) {
        mAstatus = in.readString();
        mDCharge = in.readString();
        mDelivery = in.readString();
        mEmail = in.readString();
        mMobile = in.readString();
        mName = in.readString();
        mOdate = in.readString();
        mPMethod = in.readString();
        mPickup = in.readString();
        mStatus = in.readString();
        mTimesloat = in.readString();
        mTotal = in.readString();
        orderid = in.readString();
        sign = in.readString();
    }

    public static final Creator<PendingOrderItem> CREATOR = new Creator<PendingOrderItem>() {
        @Override
        public PendingOrderItem createFromParcel(Parcel in) {
            return new PendingOrderItem(in);
        }

        @Override
        public PendingOrderItem[] newArray(int size) {
            return new PendingOrderItem[size];
        }
    };

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getAstatus() {
        return mAstatus;
    }

    public void setAstatus(String astatus) {
        mAstatus = astatus;
    }

    public String getDCharge() {
        return mDCharge;
    }

    public void setDCharge(String dCharge) {
        mDCharge = dCharge;
    }

    public String getDelivery() {
        return mDelivery;
    }

    public void setDelivery(String delivery) {
        mDelivery = delivery;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getMobile() {
        return mMobile;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getOdate() {
        return mOdate;
    }

    public void setOdate(String odate) {
        mOdate = odate;
    }

    public String getPMethod() {
        if(mPMethod.equalsIgnoreCase("Cash on delivery")){
            mPMethod="COD";
        }
        return mPMethod;
    }

    public void setPMethod(String pMethod) {
        mPMethod = pMethod;
    }

    public String getPickup() {
        return mPickup;
    }

    public void setPickup(String pickup) {
        mPickup = pickup;
    }

    public ArrayList<Productinfo> getProductinfo() {
        return mProductinfo;
    }

    public void setProductinfo(ArrayList<Productinfo> productinfo) {
        mProductinfo = productinfo;
    }

    public String getStatus() {
        String upperString = mStatus.substring(0, 1).toUpperCase() + mStatus.substring(1).toLowerCase();

        return upperString;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getTimesloat() {
        return mTimesloat;
    }

    public void setTimesloat(String timesloat) {
        mTimesloat = timesloat;
    }

    public String getTotal() {
        return mTotal;
    }

    public void setTotal(String total) {
        mTotal = total;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAstatus);
        dest.writeString(mDCharge);
        dest.writeString(mDelivery);
        dest.writeString(mEmail);
        dest.writeString(mMobile);
        dest.writeString(mName);
        dest.writeString(mOdate);
        dest.writeString(mPMethod);
        dest.writeString(mPickup);
        dest.writeString(mStatus);
        dest.writeString(mTimesloat);
        dest.writeString(mTotal);
        dest.writeString(orderid);
        dest.writeString(sign);
    }
}

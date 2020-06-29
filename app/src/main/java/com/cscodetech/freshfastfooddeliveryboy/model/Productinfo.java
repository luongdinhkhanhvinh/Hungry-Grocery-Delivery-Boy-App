
package com.cscodetech.freshfastfooddeliveryboy.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Productinfo implements Parcelable {

    @SerializedName("discount")
    private String mDiscount;
    @SerializedName("product_image")
    private String mProductImage;
    @SerializedName("product_name")
    private String mProductName;
    @SerializedName("product_price")
    private String mProductPrice;
    @SerializedName("product_qty")
    private String mProductQty;
    @SerializedName("product_weight")
    private String mProductWeight;

    protected Productinfo(Parcel in) {
        mDiscount = in.readString();
        mProductImage = in.readString();
        mProductName = in.readString();
        mProductPrice = in.readString();
        mProductQty = in.readString();
        mProductWeight = in.readString();
    }

    public static final Creator<Productinfo> CREATOR = new Creator<Productinfo>() {
        @Override
        public Productinfo createFromParcel(Parcel in) {
            return new Productinfo(in);
        }

        @Override
        public Productinfo[] newArray(int size) {
            return new Productinfo[size];
        }
    };

    public String getDiscount() {
        return mDiscount;
    }

    public void setDiscount(String discount) {
        mDiscount = discount;
    }

    public String getProductImage() {
        return mProductImage;
    }

    public void setProductImage(String productImage) {
        mProductImage = productImage;
    }

    public String getProductName() {
        return mProductName;
    }

    public void setProductName(String productName) {
        mProductName = productName;
    }

    public String getProductPrice() {
        return mProductPrice;
    }

    public void setProductPrice(String productPrice) {
        mProductPrice = productPrice;
    }

    public String getProductQty() {
        return mProductQty;
    }

    public void setProductQty(String productQty) {
        mProductQty = productQty;
    }

    public String getProductWeight() {
        return mProductWeight;
    }

    public void setProductWeight(String productWeight) {
        mProductWeight = productWeight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mDiscount);
        dest.writeString(mProductImage);
        dest.writeString(mProductName);
        dest.writeString(mProductPrice);
        dest.writeString(mProductQty);
        dest.writeString(mProductWeight);
    }
}

package com.example.organicgrocery.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderHistory {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("order_date_time")
    @Expose
    private String orderDateTime;
    @SerializedName("payment_type")
    @Expose
    private Integer paymentType;
    @SerializedName("payment_refrence")
    @Expose
    private String paymentRefrence;
    @SerializedName("bag")
    @Expose
    private List<Bag> bag = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(String orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentRefrence() {
        return paymentRefrence;
    }

    public void setPaymentRefrence(String paymentRefrence) {
        this.paymentRefrence = paymentRefrence;
    }

    public List<Bag> getBag() {
        return bag;
    }

    public void setBag(List<Bag> bag) {
        this.bag = bag;
    }

}

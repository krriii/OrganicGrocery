package com.example.organicgrocery.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WishlistResponse {

    @SerializedName("products")
    @Expose
    private List<Wishlist> products = null;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Wishlist> getProducts() {
        return products;
    }

    public void setProducts(List<Wishlist> products) {
        this.products = products;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

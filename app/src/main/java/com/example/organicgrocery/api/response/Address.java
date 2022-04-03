package com.example.organicgrocery.api.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Address implements Serializable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("province")
        @Expose
        private Integer province;
        @SerializedName("district")
        @Expose
        private String district;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("street")
        @Expose
        private String street;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getProvince() {
            return province;
        }

        public void setProvince(Integer province) {
            this.province = province;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

    }



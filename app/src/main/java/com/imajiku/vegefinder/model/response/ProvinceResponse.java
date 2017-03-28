package com.imajiku.vegefinder.model.response;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-10-08.
 */
public class ProvinceResponse {
    @SerializedName("data")
    private ProvinceResponseBody data;

    public ProvinceResponseBody getData() {
        return data;
    }

    public class ProvinceResponseBody {
        @SerializedName("data")
        private ArrayList<Province> data;
        @SerializedName("status")
        private String status;

        public String getStatus() {
            return status;
        }

        public ArrayList<Province> getProvinces() {
            return data;
        }

        public ArrayList<String> getProvinceNames() {
            ArrayList<String> provinces = new ArrayList<>();
            for (Province p : data) {
                provinces.add(p.getProvince());
            }
            return provinces;
        }

        public int getProvinceId(String province) {
            for (Province p : getProvinces()) {
                if (p.getProvince().equals(province)) {
                    return p.getId();
                }
            }
            return -1;
        }

        public String getProvinceName(int provinceId) {
            for (Province p : getProvinces()) {
                if (p.getId() == provinceId) {
                    return p.getProvince();
                }
            }
            return null;
        }
    }

    public class Province {
        @SerializedName("id")
        private int id;
        @SerializedName("country_id")
        private int countryId;
        @SerializedName("province")
        private String province;

        public int getId() {
            return id;
        }

        public int getCountryId() {
            return countryId;
        }

        public String getProvince() {
            return province;
        }
    }
}

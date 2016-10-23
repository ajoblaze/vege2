package com.imajiku.vegefinder.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-10-08.
 */
public class CityResponse {
    @SerializedName("data")
    private CityResponseBody data;

    public CityResponseBody getData() {
        return data;
    }

    public class CityResponseBody {
        @SerializedName("data")
        private ArrayList<City> data;
        @SerializedName("status")
        private String status;

        public String getStatus() {
            return status;
        }

        public ArrayList<City> getCities() {
            return data;
        }

        public ArrayList<String> getCityNames() {
            ArrayList<String> cities = new ArrayList<>();
            for (City c : data) {
                cities.add(c.getCity());
            }
            return cities;
        }

        public int getCityId(String city) {
            for (City p : getCities()) {
                if (p.getCity().equals(city)) {
                    return p.getId();
                }
            }
            return -1;
        }
    }

    public class City {
        @SerializedName("id")
        private int id;
        @SerializedName("province_id")
        private int provinceId;
        @SerializedName("city")
        private String city;

        public int getId() {
            return id;
        }

        public int getProvinceId() {
            return provinceId;
        }

        public String getCity() {
            return city;
        }
    }
}

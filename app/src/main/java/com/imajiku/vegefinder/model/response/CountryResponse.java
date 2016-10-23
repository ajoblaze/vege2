package com.imajiku.vegefinder.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-10-08.
 */
public class CountryResponse {
    @SerializedName("data")
    private CountryResponseBody data;

    public CountryResponseBody getData() {
        return data;
    }

    public class CountryResponseBody {
        @SerializedName("data")
        private ArrayList<Country> data;
        @SerializedName("status")
        private String status;

        public ArrayList<Country> getCountries() {
            return data;
        }

        public ArrayList<String> getCountryNames(){
            ArrayList<String> countries = new ArrayList<>();
            for(Country p : data){
                countries.add(p.getCountry());
            }
            return countries;
        }

        public int getCountryId(String country) {
            for(Country p : getCountries()){
                if(p.getCountry().equals(country)){
                    return p.getId();
                }
            }
            return -1;
        }

        public String getStatus() {
            return status;
        }
    }

    public class Country {
        @SerializedName("id")
        private int id;
        @SerializedName("country")
        private String country;
        @SerializedName("code")
        private String code;

        public int getId() {
            return id;
        }

        public String getCountry() {
            return country;
        }

        public String getCode() {
            return code;
        }
    }
}

package com.imajiku.vegefinder.model.presenter;


import android.content.Context;

import com.imajiku.vegefinder.activity.RegisterProfileActivity;
import com.imajiku.vegefinder.model.RegisterProfileModel;
import com.imajiku.vegefinder.model.response.ProvinceResponse;
import com.imajiku.vegefinder.model.presenter.view.RegisterProfileView;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-10-08.
 */
public class RegisterProfilePresenter {
    private RegisterProfileView view;
    private RegisterProfileModel model;
    private ProvinceResponse provinceResponse;

    public RegisterProfilePresenter(RegisterProfileView v) {
        this.view =v;
    }

    public RegisterProfileView getView() {
        return view;
    }

    public void setModel(RegisterProfileModel model) {
        this.model = model;
    }

    public void getProvince() {
        model.getProvince();
    }

    public void getCity(String province) {
        if(province.equals("")){
            return;
        }
        int provinceId = provinceResponse.getProvinceId(province);
        model.getCity(provinceId);
    }

    public void successGetProvince(ProvinceResponse response){
        this.provinceResponse = response;
        view.updateDropdown(RegisterProfileActivity.PROVINCE, response.getProvinces());
    }

    public void successGetCity(ArrayList<String> cities){
        view.updateDropdown(RegisterProfileActivity.CITY, cities);
    }

    private Context getContext(){
        return (Context) view;
    }

    public void submitRegister(String username, String email, String password) {
        model.register(username, email, password);
    }

    public void successRegister(String code, String email) {
        view.sendActivationCode(code, email);
    }
}


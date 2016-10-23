package com.imajiku.vegefinder.model.presenter.view;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-10-08.
 */
public interface RegisterProfileView {
    void updateDropdown(int type, ArrayList<String> content);

    void sendActivationCode(String code, String email);
}

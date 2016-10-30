package com.imajiku.vegefinder.model.view;

import java.util.ArrayList;

/**
 * Created by Alvin on 2016-10-08.
 */
public interface RegisterProfileView {
    void updateDropdown(int type, ArrayList<String> content);

    void successRegister(String code, String email);
}

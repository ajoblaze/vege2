package com.imajiku.vegefinder.utility;

/**
 * Created by Alvin on 2016-12-11.
 */
public class CurrentUser {
    private static int id;

    public static void setId(int id) {
        CurrentUser.id = id;
    }

    public static int getId() {
        return id;
    }
}

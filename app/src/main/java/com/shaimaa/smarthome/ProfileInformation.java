package com.shaimaa.smarthome;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class ProfileInformation {

    public static String getApiToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Api Token", MODE_PRIVATE);
        return sharedPreferences.getString("api_token", null);
    }

}

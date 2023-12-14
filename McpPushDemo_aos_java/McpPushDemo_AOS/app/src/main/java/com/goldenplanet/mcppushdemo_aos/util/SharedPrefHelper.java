package com.goldenplanet.mcppushdemo_aos.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kim Namhoon on 2023/12/14.
 */

public class SharedPrefHelper {
    private static final String PREFERENCE_KEY_USER_ID = "PREFERENCE_KEY_USER_ID";
    private static final String PREFERENCE_KEY_ACCOUNT = "PREFERENCE_KEY_ACCOUNT";
    private static final String PREFERENCE_KEY_DATASET = "PREFERENCE_KEY_DATASET";

    private static Context context;

    private SharedPrefHelper() {
    }


    public static void init(Context appContext) {
        context = appContext.getApplicationContext();
    }

    private static SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences("goldenplanet", Context.MODE_PRIVATE);
    }

    public static void setUserId(String userId) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(PREFERENCE_KEY_USER_ID, userId).apply();
    }

    public static String getUserId() {
        return getSharedPreferences().getString(PREFERENCE_KEY_USER_ID, "gp_nhkim");
    }

    public static void setAccount(String account) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(PREFERENCE_KEY_ACCOUNT, account).apply();
    }

    public static String getAccount() {
        return getSharedPreferences().getString(PREFERENCE_KEY_ACCOUNT, "GoldenPlanet");
    }

    public static void setDataSet(String dataSet) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(PREFERENCE_KEY_DATASET, dataSet).apply();
    }

    public static String getDataSet() {
        return getSharedPreferences().getString(PREFERENCE_KEY_DATASET, "gp_test");
    }



}

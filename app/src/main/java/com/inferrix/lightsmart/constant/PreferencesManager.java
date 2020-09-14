package com.inferrix.lightsmart.constant;

import android.content.Context;
import android.content.SharedPreferences;


public class PreferencesManager {

    //app login variables
    private static final String PREF_NAME = "com.inferrix.lightsmart";
    private static final String UID_ONE = "uid_one";
    private static final String UID_TWO = "uid_two";
    private static final String UID_THREE = "uid_three";
    private static final String UID_FOUR = "uid_four";
    private static final String UID_FIVE = "uid_five";
    private static final String UID_SIX = "uid_six";
    private static final String UID_SEVEN = "uid_seven";
    private static final String UID_EIGHT = "uid_eight";

    private static final String ITEM_ONE = "item_one";
    private static final String ITEM_TWO = "item_two";
    private static final String ITEM_THREE = "item_three";
    private static final String ITEM_FOUR = "item_four";
    private static final String ITEM_FIVE = "item_five";
    private static final String ITEM_SIX = "item_six";
    private static final String ITEM_SEVEN = "item_seven";
    private static final String ITEM_EIGHT = "item_eight";





    private static PreferencesManager sInstance;
    private final SharedPreferences mPref;

    private PreferencesManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    //for fragment
    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
    }

    //for getting instance
    public static synchronized PreferencesManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
        return sInstance;
    }

    public boolean clear() {
        return mPref.edit().clear().commit();
    }


    public String getUidOne() {
        return mPref.getString(UID_ONE, "");
    }

    public void setUidOne(String value) {
        mPref.edit().putString(UID_ONE, value).apply();
    }
    public String getUidTwo() {
        return mPref.getString(UID_TWO, "");
    }

    public void setUidTwo(String value) {
        mPref.edit().putString(UID_TWO, value).apply();
    }

    public String getUidThree() {
        return mPref.getString(UID_THREE, "");
    }

    public void setUidThree(String value) {
        mPref.edit().putString(UID_THREE, value).apply();
    }

    public String getUidFour() {
        return mPref.getString(UID_FOUR, "");
    }

    public void setUidFour(String value) {
        mPref.edit().putString(UID_FOUR, value).apply();
    }

    public String getUidFive() {
        return mPref.getString(UID_FIVE, "");
    }

    public void setUidFive(String value) {
        mPref.edit().putString(UID_FIVE, value).apply();
    }

    public String getUidSix() {
        return mPref.getString(UID_SIX, "");
    }

    public void setUidSix(String value) {
        mPref.edit().putString(UID_SIX, value).apply();
    }

    public String getUidSeven() {
        return mPref.getString(UID_SEVEN, "");
    }

    public void setUidSeven(String value) {
        mPref.edit().putString(UID_SEVEN, value).apply();
    }

    public String getUidEight() {
        return mPref.getString(UID_EIGHT, "");
    }

    public void setUidEight(String value) {
        mPref.edit().putString(UID_EIGHT, value).apply();
    }

    public String getItemOne() {
        return mPref.getString(ITEM_ONE, "");
    }

    public void setItemOne(String value) {
        mPref.edit().putString(ITEM_ONE, value).apply();
    }

    public String getItemTwo() {
        return mPref.getString(ITEM_TWO, "");
    }

    public void setItemTwo(String value) {
        mPref.edit().putString(ITEM_TWO, value).apply();
    }

    public String getItemThree() {
        return mPref.getString(ITEM_THREE, "");
    }

    public void setItemThree(String value) {
        mPref.edit().putString(ITEM_THREE, value).apply();
    }

    public String getItemFour() {
        return mPref.getString(ITEM_FOUR, "");
    }

    public void setItemFour(String value) {
        mPref.edit().putString(ITEM_FOUR, value).apply();
    }

    public String getItemFive() {
        return mPref.getString(ITEM_FIVE, "");
    }

    public void setItemFive(String value) {
        mPref.edit().putString(ITEM_FIVE, value).apply();
    }

    public String getItemSix() {
        return mPref.getString(ITEM_SIX, "");
    }

    public void setItemSix(String value) {
        mPref.edit().putString(ITEM_SIX, value).apply();
    }

    public String getItemSeven() {
        return mPref.getString(ITEM_SEVEN, "");
    }

    public void setItemSeven(String value) {
        mPref.edit().putString(ITEM_SEVEN, value).apply();
    }

    public String getItemEight() {
        return mPref.getString(ITEM_EIGHT, "");
    }

    public void setItemEight(String value) {
        mPref.edit().putString(ITEM_EIGHT, value).apply();
    }


}

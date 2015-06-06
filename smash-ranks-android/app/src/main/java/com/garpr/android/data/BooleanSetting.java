package com.garpr.android.data;


public class BooleanSetting extends Setting<Boolean> {


    BooleanSetting(final String key, final Boolean defaultValue) {
        super(key, defaultValue);
    }


    @Override
    public Boolean get() {
        return readSharedPreferences().getBoolean(mKey, mDefaultValue);
    }


    @Override
    public void set(final Boolean newValue) {
        writeSharedPreferences().putBoolean(mKey, mDefaultValue).apply();
    }


}
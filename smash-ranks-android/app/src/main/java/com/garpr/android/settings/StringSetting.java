package com.garpr.android.settings;


public final class StringSetting extends Setting<String> {


    StringSetting(final String key) {
        super(key);
    }


    StringSetting(final String key, final String defaultValue) {
        super(key, defaultValue);
    }


    @Override
    public String get() {
        return readSharedPreferences().getString(mKey, mDefaultValue);
    }


    @Override
    public void set(final String newValue) {
        writeSharedPreferences().putString(mKey, newValue).apply();
    }


}
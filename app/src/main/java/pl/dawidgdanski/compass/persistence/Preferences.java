package pl.dawidgdanski.compass.persistence;

import android.content.Context;
import android.content.SharedPreferences;

final class Preferences {

    private final SharedPreferences sharedPreferences;

    public Preferences(final String preferenceName, final Context applicationContext) {
        sharedPreferences = applicationContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
    }

    public void remove(final String key) {
        sharedPreferences.edit()
                .remove(key)
                .apply();
    }

    public void putString(final String key, final String value) {
        sharedPreferences.edit()
                .putString(key, value)
                .apply();
    }

    public String getString(final String key) {
        return sharedPreferences.getString(key, "");
    }

    public void putInt(final String key, final int value) {
        sharedPreferences.edit()
                .putInt(key, value)
                .apply();
    }

    public int getInt(final String key, final int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public int getInt(final String key) {
        return getInt(key, Integer.MIN_VALUE);
    }

    public void putBoolean(final String key, final boolean value) {
        sharedPreferences.edit()
                .putBoolean(key, value)
                .apply();
    }

    public void putLong(final String key, final long value) {
        sharedPreferences.edit()
                .putLong(key, value)
                .apply();
    }

    public long getLong(final String key, final long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    public boolean getBoolean(final String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public SharedPreferences.Editor edit() {
        return sharedPreferences.edit();
    }

    public void clear() {
        sharedPreferences.edit()
                .clear()
                .apply();
    }
}

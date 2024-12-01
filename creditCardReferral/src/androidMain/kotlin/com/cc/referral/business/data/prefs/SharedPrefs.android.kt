package com.cc.referral.business.data.prefs

import android.content.SharedPreferences

actual class SharedPrefs(
    private val sharedPreferences: SharedPreferences
) {
    actual fun putString(key: String, value: String?) {
        sharedPreferences.edit().apply {
            putString(key, value)
            apply()
        }
    }

    actual fun getString(key: String, defaultValue: String?): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    actual fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(key, value)
            apply()
        }
    }

    actual fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    actual fun putInt(key: String, value: Int) {
        sharedPreferences.edit().apply {
            putInt(key, value)
            apply()
        }
    }

    actual fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)

    }

    actual fun getLong(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)

    }

    actual fun putLong(key: String, value: Long): Long {
        sharedPreferences.edit().apply {
            putLong(key, value)
            apply()
        }
        return value
    }
}
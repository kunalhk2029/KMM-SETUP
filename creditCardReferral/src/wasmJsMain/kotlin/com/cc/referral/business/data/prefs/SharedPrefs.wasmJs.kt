package com.cc.referral.business.data.prefs

actual class SharedPrefs {
    actual fun putString(key: String, value: String?) {
    }

    actual fun getString(key: String, defaultValue: String?): String? {
        TODO("Not yet implemented")
    }

    actual fun putBoolean(key: String, value: Boolean) {
    }

    actual fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    actual fun putInt(key: String, value: Int) {
    }

    actual fun getInt(key: String, defaultValue: Int): Int {
        TODO("Not yet implemented")
    }

    actual fun getLong(key: String, defaultValue: Long): Long {
        TODO("Not yet implemented")
    }

    actual fun putLong(key: String, value: Long): Long {
        TODO("Not yet implemented")
    }
}
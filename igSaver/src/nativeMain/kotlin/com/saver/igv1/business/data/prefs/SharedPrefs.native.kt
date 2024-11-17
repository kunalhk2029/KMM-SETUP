package com.saver.igv1.business.data.prefs

import platform.Foundation.NSUserDefaults

actual class SharedPrefs() {

    private val nsUserDefaults: NSUserDefaults = NSUserDefaults.standardUserDefaults

    actual fun putString(key: String, value: String?) {
        nsUserDefaults.setObject(value, key)
    }

    actual fun getString(key: String, defaultValue: String?): String? {
        return nsUserDefaults.stringForKey(key)?: defaultValue
    }

    actual fun putBoolean(key: String, value: Boolean) {
        nsUserDefaults.setBool(value, key)
    }

    actual fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return nsUserDefaults.boolForKey(key)
    }

    actual fun putInt(key: String, value: Int) {
        nsUserDefaults.setInteger(value.toLong(), key)
    }

    actual fun getInt(key: String, defaultValue: Int): Int {
        return nsUserDefaults.integerForKey(key).toInt()
    }

    actual fun getLong(key: String, defaultValue: Long): Long {
        return nsUserDefaults.integerForKey(key)
    }

    actual fun putLong(key: String, value: Long): Long {
        nsUserDefaults.setInteger(value, key)
        return value
    }
}
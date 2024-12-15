package com.package_v1.package_v2.business.data.prefs

expect class SharedPrefs {
    fun putString(key: String, value: String?)
    fun getString(key: String, defaultValue: String?): String?
    fun putBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, defaultValue: Boolean): Boolean
    fun putInt(key: String, value: Int)
    fun getInt(key: String, defaultValue: Int): Int
    fun getLong(key: String, defaultValue: Long): Long
    fun putLong(key: String, value: Long): Long
}
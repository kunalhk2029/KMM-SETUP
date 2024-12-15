package com.package_v1.package_v2

import android.app.Application
import com.package_v1.package_v2.di.initKoin
import org.koin.android.ext.koin.androidContext


class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BaseApplication)
        }
    }
}
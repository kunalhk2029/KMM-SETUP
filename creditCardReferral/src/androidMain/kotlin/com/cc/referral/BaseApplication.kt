package com.cc.referral

import android.app.Application
import com.cc.referral.di.initKoin
import org.koin.android.ext.koin.androidContext


class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BaseApplication)
        }
    }
}
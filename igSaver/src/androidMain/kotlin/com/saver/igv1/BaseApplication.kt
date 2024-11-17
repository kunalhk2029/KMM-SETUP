package com.saver.igv1

import android.app.Application
import com.saver.igv1.di.initKoin
import com.saver.igv1.di.platformModule
import org.koin.android.ext.koin.androidContext


class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BaseApplication)
        }
    }
}
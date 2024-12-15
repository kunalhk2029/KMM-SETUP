package com.package_v1.package_v2.di

import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.package_v1.package_v2.business.data.db.PrimaryDatabase
import com.package_v1.package_v2.business.data.prefs.SharedPrefs
import com.package_v1.package_v2.utils.Constants.Constants
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule: Module = module {

    single<ContentResolver> {
        get<Context>().applicationContext.contentResolver
    }


}
actual val platformNetworkModule: Module = module {
    single<HttpClient> {
        HttpClient(OkHttp) {

            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
            }

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }
}
actual val platformCacheModule: Module = module {

    single<PrimaryDatabase> {
        val dbFile = get<Context>().getDatabasePath("primary_database.db")
        Room.databaseBuilder<PrimaryDatabase>(
            context = get<Context>().applicationContext,
            name = dbFile.absolutePath
        ).setDriver(BundledSQLiteDriver()).build()
    }

    singleOf(::SharedPrefs)

    single<SharedPreferences> {
        get<Context>().getSharedPreferences(Constants.MAIN_SHARED_PREFS, Context.MODE_PRIVATE)
    }
}
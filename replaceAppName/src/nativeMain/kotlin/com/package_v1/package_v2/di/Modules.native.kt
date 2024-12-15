package com.package_v1.package_v2.di

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.package_v1.package_v2.business.data.db.PrimaryDatabase
import com.package_v1.package_v2.business.data.prefs.SharedPrefs
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual val platformModule: Module = module {
    singleOf(::SharedPrefs)
}

actual val platformNetworkModule: Module = module {
    single<HttpClient> {
        HttpClient(Darwin) {

            install(Logging) {
                logger = Logger.DEFAULT
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
@OptIn(ExperimentalForeignApi::class)
actual val platformCacheModule: Module = module {
    single<PrimaryDatabase> {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        val dbFile = requireNotNull(documentDirectory?.path) + "/primary_database.db"

        Room.databaseBuilder<PrimaryDatabase>(
            name = dbFile
        ).setDriver(BundledSQLiteDriver()).build()
    }
}
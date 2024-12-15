package com.package_v1.package_v2.di

import com.package_v1.package_v2.business.data.db.PrimaryDatabase
import com.package_v1.package_v2.business.data.db.feature_1.ReplaceDaoName
import com.package_v1.package_v2.business.data.db.utils.DbRequestHandler
import com.package_v1.package_v2.business.data.network.utils.KtorHttpRequestHandler
import com.package_v1.package_v2.business.data.prefs.SharedPrefRepository
import com.package_v1.package_v2.business.data.prefs.SharedPrefRepositoryImpl
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val mainModule = module {

}

val repositoryModule = module {
    singleOf(::SharedPrefRepositoryImpl).bind<SharedPrefRepository>()
}

val viewModelsModule = module {
//    viewModelOf(::LoginViewModel)
}

val networkModule = module {
    single<Json> {
        Json {
            ignoreUnknownKeys = true
        }
    }

    singleOf(::KtorHttpRequestHandler)
}

val cacheModule = module {
    single<ReplaceDaoName> {
        get<PrimaryDatabase>().getReplaceDaoName()
    }
    singleOf(::DbRequestHandler)

}


expect val platformModule: Module
expect val platformNetworkModule: Module
expect val platformCacheModule: Module
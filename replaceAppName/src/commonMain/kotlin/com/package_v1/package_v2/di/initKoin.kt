package com.package_v1.package_v2.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(koinAppDeclaration: KoinAppDeclaration? = null) {
    startKoin {
        koinAppDeclaration?.invoke(this)
        modules(mainModule)
        modules(repositoryModule)
        modules(viewModelsModule)
        modules(networkModule)
        modules(cacheModule)
        modules(platformModule)
        modules(platformNetworkModule)
        modules(platformCacheModule)
    }
}
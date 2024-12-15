package com.package_v1.package_v2.di

import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module { }
actual val platformCacheModule: Module
    get() = module { }
actual val platformNetworkModule: Module
    get() = module{  }
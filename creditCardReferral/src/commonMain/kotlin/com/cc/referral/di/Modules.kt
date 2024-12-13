package com.cc.referral.di

import com.cc.referral.business.data.db.PrimaryDatabase
import com.cc.referral.business.data.db.stories.Dao
import com.cc.referral.business.data.db.utils.DbRequestHandler
import com.cc.referral.business.data.network.auth.AuthService
import com.cc.referral.business.data.network.auth.models.AuthServiceImpl
import com.cc.referral.business.data.network.utils.KtorHttpRequestHandler
import com.cc.referral.ui.main.kyc.KYCViewModel
import com.cc.referral.ui.onboarding.login.LoginViewModel
import com.cc.referral.ui.onboarding.register.RegisterViewModel
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {

    singleOf(::AuthServiceImpl).bind<AuthService>()

    singleOf(::DbRequestHandler)
    singleOf(::KtorHttpRequestHandler)

    single<Json> {
        Json {
            ignoreUnknownKeys = true
        }
    }

    single<Dao> {
        get<PrimaryDatabase>().storiesDao()
    }


    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::KYCViewModel)
}


expect val platformModule: Module
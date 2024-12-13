package com.cc.referral.business.domain

import kotlinx.serialization.Serializable


sealed class Screens {

    @Serializable
    data object Splash : Screens()

    @Serializable
    data object AppOverView : Screens()

    @Serializable
    data object Auth : Screens()

    @Serializable
    data object Register : Screens()

    @Serializable
    data object Login : Screens()

    @Serializable
    data object KYC1 : Screens()

    @Serializable
    data object KYC2 : Screens()

    @Serializable
    data object CardOffers : Screens()
}
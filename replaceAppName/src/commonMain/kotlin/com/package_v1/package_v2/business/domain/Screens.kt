package com.package_v1.package_v2.business.domain

import kotlinx.serialization.Serializable


sealed class Screens {

    @Serializable
    data object Splash : Screens()

    @Serializable
    data object AppOverView : Screens()

}
package com.cc.referral.business.data.network.auth.models

import kotlinx.serialization.Serializable


@Serializable
data class VerifyOtpResponseModel(
    val message: String? = null,
    val token: String? = null,
    val profile: Profile? = null,
)

@Serializable
data class Profile(
    val id: Int? = null,
    val mobile_number: String? = null,
    val username: String? = null,
    val is_verified: Boolean? = null,
)



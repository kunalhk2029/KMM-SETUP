package com.cc.referral.business.data.network.auth.models

import kotlinx.serialization.Serializable


@Serializable
data class SendOtpResponseModel(
    val message: String?=null
)

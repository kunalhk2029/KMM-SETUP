package com.cc.referral.business.data.network.utils

sealed class HttpsRequestMethod {
    data object GET : HttpsRequestMethod()
    data object POST : HttpsRequestMethod()
}
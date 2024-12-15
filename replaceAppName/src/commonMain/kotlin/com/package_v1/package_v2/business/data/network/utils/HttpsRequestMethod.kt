package com.package_v1.package_v2.business.data.network.utils

sealed class HttpsRequestMethod {
    data object GET : HttpsRequestMethod()
    data object POST : HttpsRequestMethod()
    data object PUT : HttpsRequestMethod()
    data object DELETE : HttpsRequestMethod()
}
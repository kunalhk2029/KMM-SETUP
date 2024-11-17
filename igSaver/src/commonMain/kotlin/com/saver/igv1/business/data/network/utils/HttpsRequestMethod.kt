package com.saver.igv1.business.data.network.utils

sealed class HttpsRequestMethod {
    data object GET : HttpsRequestMethod()
    data object POST : HttpsRequestMethod()
}
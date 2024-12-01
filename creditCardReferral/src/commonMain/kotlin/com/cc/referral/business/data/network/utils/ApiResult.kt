package com.cc.referral.business.data.network.utils

sealed class ApiResult {

    data class Success<T>(val data: T) : ApiResult()

    data class Error(val error: String?, val code: String?) : ApiResult()
    data class GenericResult(val error: String?) : ApiResult()
}


package com.saver.igv1.business.data.network.utils

sealed class InstagramApiError {
    data object LoginRequired : InstagramApiError()
    data object FreeLimitError : InstagramApiError()
    data class GenericError(val error: String?) : InstagramApiError()
}


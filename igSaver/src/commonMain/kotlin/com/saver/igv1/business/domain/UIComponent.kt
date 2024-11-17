package com.saver.igv1.business.domain

import com.saver.igv1.business.data.network.utils.InstagramApiError

sealed class UIComponent {

    data class Dialog(
        val title: String? = null,
        val description: String? = null,
    ) : UIComponent()

    data object None : UIComponent()

    data class Error(
        val message: String,
    ) : UIComponent()

    data class IgApiResponseError(
        val instagramApiError: InstagramApiError,
    ) : UIComponent()

    data class DownloadingSingleMediaDialog(
        val progress: Float,
        val downloadingLocationOption: DownloadingLocationOptions? = null
    ) : UIComponent()
}
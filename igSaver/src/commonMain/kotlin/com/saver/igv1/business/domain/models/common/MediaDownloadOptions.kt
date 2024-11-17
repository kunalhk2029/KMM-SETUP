package com.saver.igv1.business.domain.models.common

data class MediaDownloadOptions(
    val url: String? = null,
    val height: Int? = null,
    val width: Int? = null,
    val videoDuration: Double? = null,
    val size: String? = null,
    val isVideo: Boolean? = null
)


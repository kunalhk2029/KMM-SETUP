package com.saver.igv1.business.domain.models.common

import com.saver.igv1.business.domain.models.ig_user.InstagramUser
import com.saver.igv1.business.domain.models.stories.StoryInteractionsMetaData

data class ExternalStorageMediaData(
    val url: String? = null,
    val videoDuration: Double? = null,
    val isVideo: Boolean? = null,
    val user: InstagramUser? = null,
    val storyInteractionsMetaData: StoryInteractionsMetaData? = null,
)

package com.saver.igv1.business.domain.models.stories

import com.saver.igv1.business.domain.models.ig_user.InstagramUser
import kotlinx.serialization.Serializable


@Serializable
data class StoriesTrayInfo(
    val id: String?,
    var rankedPosition: Int?,
    var seenRankedPosition: Int?,
    val user: InstagramUser?,
    val mediaCount: Int?,
    val latestReelTime: Long?,
    var seenByUser: Boolean = false,
)

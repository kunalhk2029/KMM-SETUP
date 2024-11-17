package com.saver.igv1.business.domain.models.ig_user

import kotlinx.serialization.Serializable

@Serializable
data class InstagramUser(
    val pk: String? = null,
    val pk_id: String? = null,
    val username: String? = null,
    val full_name: String? = null,
    val is_private: Boolean? = null,
    val is_verified: Boolean? = null,
    val profile_pic_url: String? = null,
    val friendship_status: FriendshipStatus? = null,
)

@Serializable
data class FriendshipStatus(
    val following: Boolean? = null
)
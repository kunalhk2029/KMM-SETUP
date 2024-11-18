package com.saver.igv1.business.data.network.userProfileService.model

import kotlinx.serialization.Serializable


@Serializable
data class UserProfileDetailResponse(
    val data: Data? = null,
)

@Serializable
data class Data(
    val user: User? = null
)


@Serializable
data class User(
    val pk: String? = null,
    val id: String? = null,
    val full_name: String? = null,
    val username: String? = null,
    val hd_profile_pic_url_info: HdProfilePicUrlInfo? = null,
    val is_private: Boolean? = null,
    val is_verified: Boolean? = null,
    val profile_pic_url: String? = null,
)


@Serializable
data class HdProfilePicUrlInfo(
    val url: String? = null,
)
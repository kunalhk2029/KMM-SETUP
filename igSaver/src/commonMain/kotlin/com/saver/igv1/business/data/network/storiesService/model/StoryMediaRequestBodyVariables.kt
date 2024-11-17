package com.saver.igv1.business.data.network.storiesService.model

import kotlinx.serialization.Serializable


@Serializable
data class StoryMediaRequestBodyVariables(
    val first: Int? = null,
    val after: String? = null,
    val initial_reel_id: String? = null,
    val reel_ids: List<String>? = null,
)
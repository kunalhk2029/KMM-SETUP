package com.saver.igv1.business.data.db.utils

import androidx.room.TypeConverter
import com.saver.igv1.business.data.network.storiesService.model.StoryImageCandidate
import com.saver.igv1.business.data.network.storiesService.model.StoryVideoVersions
import com.saver.igv1.business.domain.models.ig_user.InstagramUser
import com.saver.igv1.business.domain.models.stories.StoryInteractionsMetaData
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json


class TypeConverters {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @TypeConverter
    fun igUserToJson(instagramUser: InstagramUser): String {
        return json.encodeToString(InstagramUser.serializer(), instagramUser)
    }

    @TypeConverter
    fun jsonToIgUser(jsonStr: String): InstagramUser {
        return json.decodeFromString(InstagramUser.serializer(), jsonStr)
    }


    @TypeConverter
    fun storyTouchInteractionsMetaDataToJson(storyTouchInteractionsMetaData: StoryInteractionsMetaData): String {
        return json.encodeToString(
            StoryInteractionsMetaData.serializer(),
            storyTouchInteractionsMetaData
        )
    }

    @TypeConverter
    fun jsonToStoryTouchInteractionsMetaData(jsonStr: String): StoryInteractionsMetaData {
        return json.decodeFromString(StoryInteractionsMetaData.serializer(), jsonStr)
    }


    @TypeConverter
    fun storyImageCandidateListToJson(list: List<StoryImageCandidate>): String {
        return json.encodeToString(ListSerializer(StoryImageCandidate.serializer()), list)
    }

    @TypeConverter
    fun jsonToStoryImageCandidateList(jsonStr: String): List<StoryImageCandidate> {
        return json.decodeFromString(ListSerializer(StoryImageCandidate.serializer()), jsonStr)
    }

    @TypeConverter
    fun storyVideoVersionsListToJson(list: List<StoryVideoVersions>): String {
        return json.encodeToString(ListSerializer(StoryVideoVersions.serializer()), list)
    }

    @TypeConverter
    fun jsonToStoryVideoVersionsList(jsonStr: String): List<StoryVideoVersions> {
        return json.decodeFromString(ListSerializer(StoryVideoVersions.serializer()), jsonStr)
    }


}
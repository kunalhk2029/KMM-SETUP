package com.saver.igv1.business.data.db.stories.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.saver.igv1.business.data.network.storiesService.model.StoryImageCandidate
import com.saver.igv1.business.data.network.storiesService.model.StoryVideoVersions
import com.saver.igv1.business.domain.models.ig_user.InstagramUser
import com.saver.igv1.business.domain.models.stories.StoryInteractionsMetaData

@Entity(tableName = "story_media_info")
data class StoryMediaInfoEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val isVideo: Boolean?,
    val mediaTakenAtTimeStamp: Long?,
    val imageMediaData: List<StoryImageCandidate>?,
    val videoMediaData: List<StoryVideoVersions>?,
    val isStoryMediaSeen: Boolean?,
    val storyTouchInteractionsMetaData: StoryInteractionsMetaData?,
    val user: InstagramUser?,
    val videoDuration: Double?
)

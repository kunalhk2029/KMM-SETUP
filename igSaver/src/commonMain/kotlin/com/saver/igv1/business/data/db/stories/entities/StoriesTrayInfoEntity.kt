package com.saver.igv1.business.data.db.stories.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.saver.igv1.business.domain.models.ig_user.InstagramUser


@Entity(tableName = "stories_tray_info")
data class StoriesTrayInfoEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val rankedPosition: Int?,
    val seenRankedPosition: Int?,
    val user: InstagramUser?,
    val mediaCount: Int?,
    val latestReelTime: Long?,
    val seenByUser: Boolean?
)
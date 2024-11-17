package com.saver.igv1.business.data.db.stories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import com.saver.igv1.business.data.db.stories.entities.StoriesTrayInfoEntity
import com.saver.igv1.business.data.db.stories.entities.StoryMediaInfoEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface StoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStoriesTrayInfo(storiesTrayInfo: List<StoriesTrayInfoEntity>): LongArray

    @Query("delete from stories_tray_info")
    suspend fun deleteStoriesTrayInfoEntity()

    @Query("select * from stories_tray_info order by rankedPosition asc")
    suspend fun getStoriesTrayInfo(): List<StoriesTrayInfoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStoryMediaItems(storyMediaInfoEntity: List<StoryMediaInfoEntity>): LongArray

    @Query("delete from story_media_info")
    suspend fun deleteStoriesMediaInfoEntity()

    @Query("select * from story_media_info")
    suspend fun getStoryMediaItems(): List<StoryMediaInfoEntity>

    @Query("SELECT * FROM story_media_info WHERE json_extract(user, '\$.username') = :userName  order by mediaTakenAtTimeStamp asc")
    suspend fun getStoryMediaItems(userName: String): List<StoryMediaInfoEntity>

    @Query("select * from story_media_info  order by mediaTakenAtTimeStamp asc")
    fun getStoryMediaItemFlow(): Flow<List<StoryMediaInfoEntity>>

    @Query(
        "SELECT * FROM story_media_info WHERE json_extract(user, '\$.username') = :userName and mediaTakenAtTimeStamp = :mediaTakenAtTimeStamp limit 1"
    )
    suspend fun getStoryMediaItem(
        userName: String,
        mediaTakenAtTimeStamp: Long
    ): StoryMediaInfoEntity?
}
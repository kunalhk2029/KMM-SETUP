package com.saver.igv1.business.domain

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Constraints
import com.saver.igv1.business.domain.models.stories.StoryFeedMediaMetaData
import com.saver.igv1.business.domain.models.stories.StoryHashtagsMetaData
import com.saver.igv1.business.domain.models.stories.StoryIgMentionMetaData
import com.saver.igv1.business.domain.models.stories.StoryInteractionsMetaData
import com.saver.igv1.business.domain.models.stories.StoryLocationMetaData
import com.saver.igv1.business.domain.models.stories.StoryMusicMetaData
import com.saver.igv1.business.domain.models.stories.StoryStickerMetaData
import kotlin.math.abs

object StoryTouchManager {

    fun getTouchedStoryInteractionsMetaData(
        offset: Offset,
        constraint: Constraints,
        storyTouchInteractionsMetaData: StoryInteractionsMetaData?
    ): StoryInteractionsMetaData? {

        storyTouchInteractionsMetaData?.storyFeedMediaMetaData?.forEach {
            if (it.x != null && it.y != null && it.height != null && it.width != null) {
                val start = abs((it.x - (it.width / 2))) * 100
                val end = abs((it.x + (it.width / 2))) * 100
                val top = abs((it.y - (it.height / 2))) * 100 - 1.25
                val bottom = abs((it.y + (it.height / 2))) * 100 + 1.25


                if (((offset.x / constraint.maxWidth) * 100 in start..end) &&
                    ((offset.y / constraint.maxHeight) * 100 in top..bottom)
                ) {
                    return StoryInteractionsMetaData(
                        storyFeedMediaMetaData = listOf(it)
                    )
                }
            }
        }

        storyTouchInteractionsMetaData?.storyIgMentionMetaData?.forEach {
            if (it.x != null && it.y != null && it.height != null && it.width != null) {
                val start = abs((it.x - (it.width / 2))) * 100
                val end = abs((it.x + (it.width / 2))) * 100
                val top = abs((it.y - (it.height / 2))) * 100 - 1.25
                val bottom = abs((it.y + (it.height / 2))) * 100 + 1.25


                if (((offset.x / constraint.maxWidth) * 100 in start..end) &&
                    ((offset.y / constraint.maxHeight) * 100 in top..bottom)
                ) {
                    return StoryInteractionsMetaData(
                        storyIgMentionMetaData = listOf(it)
                    )
                }
            }
        }

        storyTouchInteractionsMetaData?.storyStickerMetaData?.forEach {
            if (it.x != null && it.y != null && it.height != null && it.width != null) {
                val start = abs((it.x - (it.width / 2))) * 100
                val end = abs((it.x + (it.width / 2))) * 100
                val top = abs((it.y - (it.height / 2))) * 100 - 1.25
                val bottom = abs((it.y + (it.height / 2))) * 100 + 1.25


                if (((offset.x / constraint.maxWidth) * 100 in start..end) &&
                    ((offset.y / constraint.maxHeight) * 100 in top..bottom)
                ) {
                    return StoryInteractionsMetaData(
                        storyStickerMetaData = listOf(it)
                    )
                }
            }
        }

        storyTouchInteractionsMetaData?.storyHashtagsMetaData?.forEach {
            if (it.x != null && it.y != null && it.height != null && it.width != null) {
                val start = abs((it.x - (it.width / 2))) * 100
                val end = abs((it.x + (it.width / 2))) * 100
                val top = abs((it.y - (it.height / 2))) * 100 - 1.25
                val bottom = abs((it.y + (it.height / 2))) * 100 + 1.25


                if (((offset.x / constraint.maxWidth) * 100 in start..end) &&
                    ((offset.y / constraint.maxHeight) * 100 in top..bottom)
                ) {
                    return StoryInteractionsMetaData(
                        storyHashtagsMetaData = listOf(it)
                    )
                }
            }
        }

        storyTouchInteractionsMetaData?.storyLocationMetaData?.forEach {
            if (it.x != null && it.y != null && it.height != null && it.width != null) {
                val start = abs((it.x - (it.width / 2))) * 100
                val end = abs((it.x + (it.width / 2))) * 100
                val top = abs((it.y - (it.height / 2))) * 100 - 1.25
                val bottom = abs((it.y + (it.height / 2))) * 100 + 1.25


                if (((offset.x / constraint.maxWidth) * 100 in start..end) &&
                    ((offset.y / constraint.maxHeight) * 100 in top..bottom)
                ) {
                    return StoryInteractionsMetaData(
                        storyLocationMetaData = listOf(it)
                    )
                }
            }
        }

        return null
    }

}
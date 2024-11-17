package com.saver.igv1.utils.Constants

import com.saver.igv1.AndroidVersion.isVersionAbove29

object StorageDirectoryConstants {
    const val MAIN_DIRECTORY = "Ig Saver Media"
    const val STORIES_DIRECTORY = "Stories"
    const val MEDIA_FILE_SHARING_DIRECTORY = "Mediashared"
    const val MUSICSTORY_DIRECTORY = "StoriesMusic"
    const val LIVE_STORIES_CACHE_DIRECTORY = "LIVE_STORIES_CACHE_DIRECTORY"
    const val LIVE_STORIES_PROFILE_CACHE_DIRECTORY = "LIVE_STORIES_PROFILE_CACHE_DIRECTORY"

    const val DRIVE_MAIN_DIRECTROY = "Insta Saver"
    const val DRIVE_MEDIA_CACHE_DIRECTORY = "DRIVE_MEDIA_CACHE_DIRECTORY"
    const val DRIVE_MEDIA_PREVIEW_DIRECTORY = "DRIVE_MEDIA_PREVIEW_DIRECTORY"
    const val DRIVE_MEDIA_PLAYER_DIRECTORY = "DRIVE_MEDIA_PLAYER_DIRECTORY"

    val IMAGE_STORIES_BASE_DIRECTORY = if (isVersionAbove29())
        "Pictures/${MAIN_DIRECTORY}/${STORIES_DIRECTORY}"
    else "/storage/emulated/0/Pictures/${MAIN_DIRECTORY}/${STORIES_DIRECTORY}"

    val VIDEO_STORIES_BASE_DIRECTORY = if (isVersionAbove29())
        "Movies/${MAIN_DIRECTORY}/${STORIES_DIRECTORY}"
    else "/storage/emulated/0/Movies/${MAIN_DIRECTORY}/${STORIES_DIRECTORY}"

    val IMAGE_POSTS_BASE_DIRECTORY = if (isVersionAbove29())
        "Pictures/${MAIN_DIRECTORY}"
    else "/storage/emulated/0/Pictures/${MAIN_DIRECTORY}/Downloaded"

    val VIDEO_POSTS_BASE_DIRECTORY = if (isVersionAbove29())
        "Movies/${MAIN_DIRECTORY}"
    else "/storage/emulated/0/Movies/${MAIN_DIRECTORY}/Downloaded"
}
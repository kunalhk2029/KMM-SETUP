package com.package_v1.package_v2.utils.Constants

object Constants {
    const val MAIN_SHARED_PREFS = "MAIN_SHARED_PREFS"
    const val FCM_NOTIFICATION_ID = "456"
    const val FCM_NOTIFICATION_CHANNEL = "General"
    const val FCM_COMMON_TOPIC = "general"
    const val FCM_COMMON_SUBSCRIBED = "FCM_COMMON_SUBSCRIBED"
    const val FCM_AUTODOWNLOAD_PROMOTION = "FCM_AUTODOWNLOAD_PROMOTION"
    const val JSON_ERROR = "JSON_ERROR"
    const val API29_BELOW_CACHE_STORAGE = "API29_BELOW_CACHE_STORAGE"
    const val TRAY_REES_MAXID_IN_URL = 17
    const val LEFT_AND_RIGHT_MAXID_URL = 8
    const val LIVE_STORIES_MAX_CACHE = 300
    const val STORY_CACHE_WORKER_DELAY = 0L
    const val ON_BOARDING = "ON_BOARDING"
    const val IF_FINDER = "20.29."
    const val ADS_COMPANY_SERVICE = "ADS_COMPANY_SERVICE" // false FOR FACEBOOK true FOR ADMOB
    const val UNKNOWN_ERROR = "UNKNOWN_ERROR"
    const val STORIES_MEDIACONTENT_REQUEST_STATE = "STORIES_MEDIACONTENT_REQUEST_STATE"
    const val STORIES_MEDIACONTENT_REQUEST_WAITING_PERIOD = 360000L
    const val PLAYER_AD_REPEAT_INTERVAL = 3
    const val KEEP_LOGIN_ACTIVE = "KEEP_LOGIN_ACTIVE"

    const val DRIVE_MEDIA_THUMBNAIL_CACHE_LIMIT = 80
    const val DRIVE_MEDIA_CACHE_LIMIT = 300

    //Retry Policy
//    val RetryPolicy = DefaultRetryPolicy(10000, 2, 1F)


    /// Pagination
    var POSTS_PER_PAGE = 12
    val REELS_PER_PAGE = 9

    /////////////////////////////
    const val NOT_FAVOURITE = 2
    const val FAVOURITE = 1
    const val DRIVE_INTERNET_ERROR = "NetworkError"
    const val DRIVE_INTERNET_ERROR2 = "Unable to resolve host"
    const val DRIVE_STORAGE_FULL = "403 Forbidden"
    const val DRIVE_POSTS_NAME_IDENTIFIER = "!S!"
    const val DRIVE_FAVOURTIE_USER = "FAVOURTIE_USER"

    const val DRIVE_MEDIA_DOWNLOAD_SIZELIMIT = 350
}
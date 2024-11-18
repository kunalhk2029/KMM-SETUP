package com.saver.igv1.business.data.prefs

import com.saver.igv1.business.domain.DownloadingLocationOptions
import com.saver.igv1.business.domain.models.ig_user.InstagramUser

interface SharedPrefRepository {

    fun getSessionId(): String?

    fun getUserid(): String?

    fun getUserAgent(): String?

    fun getCsrfToken(): String?

    fun getStoryeffect(): String?

    fun resetAuthKeys()

    fun initAuthKeys(sessionId: String, csrfToken: String, userId: String, userAgent: String?)

    fun setStoryEffect(pt: String)

    fun setOfflinyEffect(pt: String)

    fun getTheme(): Int

    fun get_isLayoutUserwise(): Boolean

    fun set_isLayoutUserwise(boolean: Boolean)

    fun setOnBoarding(boolean: Boolean)

    fun getOnBoarding(): Boolean

    fun getAdsService(): Boolean

    fun setAdsService(boolean: Boolean)

    fun set_HAPTIC_STATUS(boolean: Boolean)

    fun get_HAPTIC_STATUS(): Boolean

    fun set_FCM_AUTODOWNLOAD_PROMOTION(boolean: Boolean)

    fun get_FCM_AUTODOWNLOAD_PROMOTION(): Boolean

    fun set_HIGHLIGHT_UNSEEN_LIVE_STORIES_ENABLE(boolean: Boolean)

    fun get_HIGHLIGHT_UNSEEN_LIVE_STORIES_ENABLE(): Boolean

    fun set_LIVE_STORIES_OFFLINE_STATUS(boolean: Boolean)

    fun get_LIVE_STORIES_OFFLINE_STATUS(): Boolean

    fun get_FCM_COMMON_SUBSCRIBED(): Boolean

    fun set_FCM_COMMON_SUBSCRIBED(boolean: Boolean)

    fun get_SEEN_FIRST_TIME_OPEN(): Boolean

    fun get_RATING_DIALOG_NOT_NOW(): Boolean

    fun set_RATING_DIALOG_NOT_NOW_TIME(time: Long)

    fun get_RATING_DIALOG_NOT_NOW_TIME(): Long

    fun set_RATING_DIALOG_NOT_NOW(boolean: Boolean)

    fun get_RATING_SHOW_NEVER_BOX(): Boolean

    fun set_RATING_SHOW_NEVER_BOX(boolean: Boolean)

    fun get_RATING_GIVEN(): Boolean

    fun set_RATING_GIVEN(boolean: Boolean)

    fun get_SHOW_RATING(): Boolean

    fun get_RATING_DIALOG_NOTSHOW_AGAIN(): Boolean

    fun set_RATING_DIALOG_NOTSHOW_AGAIN(boolean: Boolean)

    fun set_SEEN_FIRST_TIME_OPEN(boolean: Boolean)

    fun getDefaultHomeScreenExists(): Int

    fun setDefaultHomeScreenExists(index: Int)

    fun changetheme(id: Int)

    fun setRatingParams(boolean: Boolean)

    fun get_STORIES_MEDIACONTENT_REQUEST_STATE(): Boolean

    fun set_STORIES_MEDIACONTENT_REQUEST_STATE(boolean: Boolean)

    fun get_TOOLTIP_MULTISELECT_LIVESTORIES(): Boolean

    fun set_TTOOLTIP_MULTISELECT_LIVESTORIES(boolean: Boolean)

    fun get_TOOLTIP_MULTISELECT_POSTS(): Boolean

    fun set_TOOLTIP_MULTISELECT_POSTS(boolean: Boolean)

    fun get_TOOLTIP_MULTISELECT_HIGHLIGHTS(): Boolean

    fun set_TOOLTIP_MULTISELECT_HIGHLIGHTS(boolean: Boolean)

    fun get_TOOLTIP_MULTISELECT_DELETE(): Boolean

    fun set_TOOLTIP_MULTISELECT_DELETE(boolean: Boolean)

    fun get_TOOLTIP_PROFILE_MENTION(): Boolean

    fun set_TOOLTIP_PROFILE_MENTION(boolean: Boolean)

    fun get_TOOLTIP_MUSIC_PLAYER(): Boolean

    fun set_TOOLTIP_MUSIC_PLAYER(boolean: Boolean)

    fun get_TOOLTIP_STORY_INTERACTIONS(): Boolean

    fun set_TOOLTIP_STORY_INTERACTIONS(boolean: Boolean)

    fun get_TOOLTIP_ZOOM_MEDIA(): Boolean

    fun set_TOOLTIP_ZOOM_MEDIA(boolean: Boolean)

    fun get_API29_BELOW_CACHE_STORAGE(): Boolean

    fun set_API29_BELOW_CACHE_STORAGE(boolean: Boolean)

    fun get_LIVE_STORIES_HOST_INFO(): Boolean

    fun set_LIVE_STORIES_HOST_INFO(boolean: Boolean)

    fun get_DisableAdsAndPromo(): Boolean

    fun set_DisableAdsAndPromo(boolean: Boolean)

    fun get_showPromo(): Boolean

    fun set_showPromo(boolean: Boolean)

    fun getLoginInActiveTrackerLoggerTime(): Long

    fun setLoginInActiveTrackerLoggerTime(lastLoggedTime: Long)

    fun get_Is_User_Changed_HomeScreen(): Boolean

    fun set_Is_User_Changed_HomeScreen(boolean: Boolean)

    ////////////////////////////////////

    fun get_FAV_STORY_MODE(): Boolean

    fun set_FAV_STORY_MODE(boolean: Boolean)

    fun get_FAV_USER_MODE(): Boolean

    fun set_FAV_USER_MODE(boolean: Boolean)

    fun getDriveSyncStatus(): Boolean

    fun setDriveSyncStatus(boolean: Boolean)

    fun get_AUTODOWNLOAD_USERWISE_STORY_PLAYER_FLOW(): Int

    fun set_AUTODOWNLOAD_USERWISE_STORY_PLAYER_FLOW(index: Int)

    fun getAutodownloadStoryEffect(): String?

    fun setAutodownloadStoryEffect(pt: String)

    fun get_BOTTOM_NAV_TOOLTIP(): Boolean

    fun set_BOTTOM_NAV_TOOLTIP(boolean: Boolean)

    fun get_DOWNLOADING_OPTION(): DownloadingLocationOptions

    fun set_DOWNLOADING_OPTION(downloadingLocationOptions: DownloadingLocationOptions)

    fun getLoggedInUsername(): String?
    fun setLoggedInUsername(
        userName: String?
    )

    fun APP_OPENED_TIME(time: Long?): Long

}




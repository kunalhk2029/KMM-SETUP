package com.saver.igv1.business.data.prefs

import com.saver.igv1.Logger
import com.saver.igv1.business.domain.DownloadingLocationOptions
import com.saver.igv1.business.domain.getDownloadingLocationOption
import com.saver.igv1.utils.Constants.Constants
import com.saver.igv1.utils.Constants.InstagramAuthConstansts
import com.saver.igv1.utils.Constants.ServiceConstants
import com.saver.igv1.utils.Constants.SettingsConstants
import com.saver.igv1.utils.Constants.TooltipsConstants


class SharedPrefRepositoryImpl(private val pref: SharedPrefs) : SharedPrefRepository {

    override fun getSessionId(): String? {
        return pref.getString(InstagramAuthConstansts.SESSION_ID, null)
    }

    override fun getUserid(): String? {
        return pref.getString(InstagramAuthConstansts.USER_ID, null)
    }

    override fun getUserAgent(): String? {
        return pref.getString(InstagramAuthConstansts.USER_AGENT, null)
    }

    override fun getCsrfToken(): String? {
        return pref.getString(InstagramAuthConstansts.CSRF_TOKEN, null)
    }

    override fun getStoryeffect(): String? {
        return pref.getString(SettingsConstants.STORY_EFFECT, "Cube Effect")
    }

    override fun getTheme(): Int {
        return pref.getInt(SettingsConstants.THEME, 2)
    }

    override fun set_FCM_AUTODOWNLOAD_PROMOTION(boolean: Boolean) {
        pref.putBoolean(Constants.FCM_AUTODOWNLOAD_PROMOTION, boolean)
    }

    override fun get_FCM_AUTODOWNLOAD_PROMOTION(): Boolean {
        return pref.getBoolean(Constants.FCM_AUTODOWNLOAD_PROMOTION, true)
    }

    override fun get_SEEN_FIRST_TIME_OPEN(): Boolean {
        return pref.getBoolean(SettingsConstants.SEEN_FIRST_TIME_OPEN, true)
    }

    override fun set_SEEN_FIRST_TIME_OPEN(boolean: Boolean) {
        pref.putBoolean(SettingsConstants.SEEN_FIRST_TIME_OPEN, boolean)
    }

    override fun getDefaultHomeScreenExists(): Int {
        return pref.getInt(SettingsConstants.DEFAULT_HOME_SCREEN, -1)
    }

    override fun setDefaultHomeScreenExists(index: Int) {
        pref.putInt(SettingsConstants.DEFAULT_HOME_SCREEN, index)
    }

    override fun changetheme(id: Int) {
        pref.putInt(SettingsConstants.THEME, id)

    }

    override fun setRatingParams(boolean: Boolean) {
        var isSetThreeTimes = pref.getInt("isSetThreeTimes", 0)
        if (isSetThreeTimes > 3 && boolean) return
        Logger.log("848984894894 setRatingParams = " + isSetThreeTimes)
        isSetThreeTimes++
        if (!boolean) {
            Logger.log("848984894894 set_SHOW_RATING = 0")
            pref.putInt("isSetThreeTimes", 0)
        } else {
            pref.putInt("isSetThreeTimes", isSetThreeTimes)
        }

    }

    override fun get_isLayoutUserwise(): Boolean {
        return pref.getBoolean(SettingsConstants.USERWISE_MODE, true)
    }

    override fun set_isLayoutUserwise(boolean: Boolean) {

        pref.putBoolean(SettingsConstants.USERWISE_MODE, boolean)

    }

    override fun get_FCM_COMMON_SUBSCRIBED(): Boolean {
        return pref.getBoolean(Constants.FCM_COMMON_SUBSCRIBED, false)
    }

    override fun set_HIGHLIGHT_UNSEEN_LIVE_STORIES_ENABLE(boolean: Boolean) {

        pref.putBoolean(SettingsConstants.HIGHLIGHT_UNSEEN_LIVE_STORIES_ENABLE, boolean)

    }

    override fun get_HIGHLIGHT_UNSEEN_LIVE_STORIES_ENABLE(): Boolean {
        return pref.getBoolean(SettingsConstants.HIGHLIGHT_UNSEEN_LIVE_STORIES_ENABLE, true)
    }

    override fun set_HAPTIC_STATUS(boolean: Boolean) {

        pref.putBoolean(SettingsConstants.VIBRATION, boolean)

    }

    override fun get_HAPTIC_STATUS(): Boolean {
        return pref.getBoolean(SettingsConstants.VIBRATION, true)
    }

    override fun set_FCM_COMMON_SUBSCRIBED(boolean: Boolean) {

        pref.putBoolean(Constants.FCM_COMMON_SUBSCRIBED, boolean)

    }

    override fun get_RATING_DIALOG_NOT_NOW(): Boolean {
        return pref.getBoolean(SettingsConstants.RATING_DIALOG_NOT_NOW, false)
    }

    override fun set_RATING_DIALOG_NOT_NOW_TIME(time: Long) {

        pref.putLong(SettingsConstants.RATING_DIALOG_NOT_NOW_TIME, time)

    }

    override fun get_RATING_DIALOG_NOT_NOW_TIME(): Long {
        return pref.getLong(SettingsConstants.RATING_DIALOG_NOT_NOW_TIME, 0L)
    }

    override fun set_RATING_DIALOG_NOT_NOW(boolean: Boolean) {

        pref.putBoolean(SettingsConstants.RATING_DIALOG_NOT_NOW, boolean)

    }

    override fun get_RATING_SHOW_NEVER_BOX(): Boolean {
        return pref.getBoolean(SettingsConstants.RATING_SHOW_NEVER_BOX, false)
    }

    override fun set_RATING_SHOW_NEVER_BOX(boolean: Boolean) {
        pref.putBoolean(SettingsConstants.RATING_SHOW_NEVER_BOX, boolean)
    }

    override fun get_RATING_GIVEN(): Boolean {
        return pref.getBoolean(SettingsConstants.RATING_GIVEN, false)
    }

    override fun set_RATING_GIVEN(boolean: Boolean) {

        pref.putBoolean(SettingsConstants.RATING_GIVEN, boolean)

    }

    override fun get_SHOW_RATING(): Boolean {
        val isSetThreeTimes = pref.getInt("isSetThreeTimes", 0)
        Logger.log("848984894894 get_SHOW_RATING = " + isSetThreeTimes)
        return isSetThreeTimes >= 3
    }

    override fun get_RATING_DIALOG_NOTSHOW_AGAIN(): Boolean {
        return pref.getBoolean(SettingsConstants.RATING_DIALOG_NOTSHOW_AGAIN, false)
    }

    override fun set_RATING_DIALOG_NOTSHOW_AGAIN(boolean: Boolean) {
        pref.putBoolean(SettingsConstants.RATING_DIALOG_NOTSHOW_AGAIN, boolean)
    }

    override fun getAdsService(): Boolean {
        return pref.getBoolean(Constants.ADS_COMPANY_SERVICE, true)
    }

    override fun setAdsService(boolean: Boolean) {
        pref.putBoolean(Constants.ADS_COMPANY_SERVICE, boolean)
    }

    override fun setStoryEffect(pt: String) {
        pref.putString(SettingsConstants.STORY_EFFECT, pt)
    }

    override fun setOfflinyEffect(pt: String) {
        pref.putString(SettingsConstants.OFFLINE_EFFECT, pt)
    }

    override fun resetAuthKeys() {
        pref.putString(InstagramAuthConstansts.SESSION_ID, null)
        pref.putString(InstagramAuthConstansts.USER_ID, null)
        pref.putString(InstagramAuthConstansts.CSRF_TOKEN, null)

    }

    override fun initAuthKeys(
        sessionId: String,
        csrfToken: String,
        userId: String,
        userAgent: String?
    ) {
        pref.putString(InstagramAuthConstansts.SESSION_ID, sessionId)
        pref.putString(InstagramAuthConstansts.USER_ID, userId)
        pref.putString(InstagramAuthConstansts.CSRF_TOKEN, csrfToken)
        pref.putString(InstagramAuthConstansts.USER_AGENT, userAgent)
    }

    override fun getOnBoarding(): Boolean {
        return pref.getBoolean(Constants.ON_BOARDING, true)
    }

    override fun setOnBoarding(boolean: Boolean) {
        pref.putBoolean(Constants.ON_BOARDING, boolean)
    }

    override fun get_STORIES_MEDIACONTENT_REQUEST_STATE(): Boolean {
        return pref.getBoolean(Constants.STORIES_MEDIACONTENT_REQUEST_STATE, false)
    }

    override fun set_STORIES_MEDIACONTENT_REQUEST_STATE(boolean: Boolean) {
        pref.putBoolean(Constants.STORIES_MEDIACONTENT_REQUEST_STATE, boolean)
    }

    override fun get_TOOLTIP_MULTISELECT_LIVESTORIES(): Boolean {
        return pref.getBoolean(TooltipsConstants.TOOLTIP_MULTISELECT_LIVESTORIES, true)

    }

    override fun set_TTOOLTIP_MULTISELECT_LIVESTORIES(boolean: Boolean) {
        pref.putBoolean(TooltipsConstants.TOOLTIP_MULTISELECT_LIVESTORIES, boolean)
    }

    override fun get_TOOLTIP_MULTISELECT_POSTS(): Boolean {
        return pref.getBoolean(TooltipsConstants.TOOLTIP_MULTISELECT_POSTS, true)
    }

    override fun set_TOOLTIP_MULTISELECT_POSTS(boolean: Boolean) {
        pref.putBoolean(TooltipsConstants.TOOLTIP_MULTISELECT_POSTS, boolean)
    }

    override fun get_TOOLTIP_MULTISELECT_HIGHLIGHTS(): Boolean {
        return pref.getBoolean(TooltipsConstants.TOOLTIP_MULTISELECT_HIGHLIGHTS, true)
    }

    override fun set_TOOLTIP_MULTISELECT_HIGHLIGHTS(boolean: Boolean) {
        pref.putBoolean(TooltipsConstants.TOOLTIP_MULTISELECT_HIGHLIGHTS, boolean)
    }

    override fun get_TOOLTIP_MULTISELECT_DELETE(): Boolean {
        return pref.getBoolean(TooltipsConstants.TOOLTIP_MULTISELECT_DELETE, true)
    }

    override fun set_TOOLTIP_MULTISELECT_DELETE(boolean: Boolean) {
        pref.putBoolean(TooltipsConstants.TOOLTIP_MULTISELECT_DELETE, boolean)
    }

    override fun get_TOOLTIP_PROFILE_MENTION(): Boolean {
        return pref.getBoolean(TooltipsConstants.TOOLTIP_PROFILE_MENTION, true)
    }

    override fun set_TOOLTIP_PROFILE_MENTION(boolean: Boolean) {
        pref.putBoolean(TooltipsConstants.TOOLTIP_PROFILE_MENTION, boolean)
    }

    override fun get_TOOLTIP_MUSIC_PLAYER(): Boolean {
        return pref.getBoolean(TooltipsConstants.TOOLTIP_MUSIC_PLAYER, true)
    }

    override fun set_TOOLTIP_MUSIC_PLAYER(boolean: Boolean) {
        pref.putBoolean(TooltipsConstants.TOOLTIP_MUSIC_PLAYER, boolean)
    }

    override fun get_TOOLTIP_STORY_INTERACTIONS(): Boolean {
        return pref.getBoolean(TooltipsConstants.TOOLTIP_STORY_INTERACTIONS, true)
    }

    override fun set_TOOLTIP_STORY_INTERACTIONS(boolean: Boolean) {
        pref.putBoolean(TooltipsConstants.TOOLTIP_STORY_INTERACTIONS, boolean)
    }

    override fun get_TOOLTIP_ZOOM_MEDIA(): Boolean {
        return pref.getBoolean(TooltipsConstants.TOOLTIP_ZOOM_MEDIA, true)
    }

    override fun set_TOOLTIP_ZOOM_MEDIA(boolean: Boolean) {
        pref.putBoolean(TooltipsConstants.TOOLTIP_ZOOM_MEDIA, boolean)
    }

    override fun get_API29_BELOW_CACHE_STORAGE(): Boolean {
        return pref.getBoolean(Constants.API29_BELOW_CACHE_STORAGE, true)
    }

    override fun set_API29_BELOW_CACHE_STORAGE(boolean: Boolean) {
        pref.putBoolean(Constants.API29_BELOW_CACHE_STORAGE, boolean)
    }

    override fun set_LIVE_STORIES_OFFLINE_STATUS(boolean: Boolean) {
        pref.putBoolean(SettingsConstants.LIVE_STORIES_OFFLINE_STATUS, boolean)
    }

    override fun get_LIVE_STORIES_OFFLINE_STATUS(): Boolean {
        return pref.getBoolean(SettingsConstants.LIVE_STORIES_OFFLINE_STATUS, false)
    }

    override fun get_LIVE_STORIES_HOST_INFO(): Boolean {
        return pref.getBoolean(SettingsConstants.LIVE_STORIES_HOST_INFO, true)
    }

    override fun set_LIVE_STORIES_HOST_INFO(boolean: Boolean) {
        pref.putBoolean(SettingsConstants.LIVE_STORIES_HOST_INFO, boolean)
    }

    override fun get_DisableAdsAndPromo(): Boolean {
        return pref.getBoolean(SettingsConstants.DISABLE_ADS_PROMO, true)
    }

    override fun set_DisableAdsAndPromo(boolean: Boolean) {
        pref.putBoolean(SettingsConstants.DISABLE_ADS_PROMO, boolean)
    }

    override fun get_showPromo(): Boolean {
        return pref.getBoolean(SettingsConstants.SHOW_ADS_PROMO, true)
    }

    override fun set_showPromo(boolean: Boolean) {
        pref.putBoolean(SettingsConstants.SHOW_ADS_PROMO, boolean)
    }

    override fun get_Is_User_Changed_HomeScreen(): Boolean {
        return pref.getBoolean(SettingsConstants.IS_USER_CHANGED_HOMESCREEN, true)
    }

    override fun set_Is_User_Changed_HomeScreen(boolean: Boolean) {
        pref.putBoolean(SettingsConstants.IS_USER_CHANGED_HOMESCREEN, boolean)
    }

    override fun getLoginInActiveTrackerLoggerTime(): Long {
        return pref.getLong("TrackerLoggerTime", 0L)
    }

    override fun setLoginInActiveTrackerLoggerTime(lastLoggedTime: Long) {

        pref.putLong("TrackerLoggerTime", lastLoggedTime)

    }
    ///////////////////////


    override fun get_FAV_STORY_MODE(): Boolean {
        return pref.getBoolean(SettingsConstants.FAV_STORY_MODE, true)
    }

    override fun set_FAV_STORY_MODE(boolean: Boolean) {
        pref.putBoolean(SettingsConstants.FAV_STORY_MODE, boolean)
    }

    override fun get_FAV_USER_MODE(): Boolean {
        return pref.getBoolean(SettingsConstants.FAV_USER_MODE, true)
    }

    override fun set_FAV_USER_MODE(boolean: Boolean) {
        pref.putBoolean(SettingsConstants.FAV_USER_MODE, boolean)
    }

    override fun getDriveSyncStatus(): Boolean {
        return pref.getBoolean(ServiceConstants.DRIVE_SYNC, true)
    }

    override fun setDriveSyncStatus(boolean: Boolean) {
        pref.putBoolean(ServiceConstants.DRIVE_SYNC, boolean)
    }

    override fun get_AUTODOWNLOAD_USERWISE_STORY_PLAYER_FLOW(): Int {
        return pref.getInt(SettingsConstants.AUTODOWNLOAD_USERWISE_STORY_PLAYER_FLOW, 0)
    }

    override fun set_AUTODOWNLOAD_USERWISE_STORY_PLAYER_FLOW(index: Int) {

        pref.putInt(SettingsConstants.AUTODOWNLOAD_USERWISE_STORY_PLAYER_FLOW, index)

    }

    override fun getAutodownloadStoryEffect(): String? {
        return pref.getString(SettingsConstants.AUTODOWNLOAD_EFFECT, "Cube Effect")

    }

    override fun setAutodownloadStoryEffect(pt: String) {

        pref.putString(SettingsConstants.AUTODOWNLOAD_EFFECT, pt)

    }

    override fun get_BOTTOM_NAV_TOOLTIP(): Boolean {
        return pref.getBoolean(TooltipsConstants.BOTTOM_NAV_TOOLTIP, true)
    }

    override fun set_BOTTOM_NAV_TOOLTIP(boolean: Boolean) {

        pref.putBoolean(TooltipsConstants.BOTTOM_NAV_TOOLTIP, boolean)

    }

    override fun get_DOWNLOADING_OPTION(): DownloadingLocationOptions {
        val uiValue = pref.getString(
            SettingsConstants.DEFAULT_DOWNLOADING_OPTION,
            DownloadingLocationOptions.AskEveryTime.uiValue
        )
        return getDownloadingLocationOption(uiValue!!)
    }

    override fun set_DOWNLOADING_OPTION(downloadingLocationOptions: DownloadingLocationOptions) {
        pref.putString(SettingsConstants.DEFAULT_DOWNLOADING_OPTION, downloadingLocationOptions.uiValue)
    }

    override fun ACTIVE_PROFILE_USERNAME(username: String?): String? {
        if (username == "") {
            return pref.getString(SettingsConstants.USER_NAME, null)
        }

        pref.putString(SettingsConstants.USER_NAME, username)

        return pref.getString(SettingsConstants.USER_NAME, null)
    }

    override fun APP_OPENED_TIME(time: Long?): Long {
        if (time == null) return pref.getLong(SettingsConstants.APP_OPENED_TIME, 0L)
        time.let {
            pref.putLong(SettingsConstants.APP_OPENED_TIME, it)
        }
        return 0L
    }
}


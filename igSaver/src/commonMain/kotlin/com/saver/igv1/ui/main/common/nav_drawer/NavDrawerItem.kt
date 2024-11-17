package com.saver.igv1.ui.main.common.nav_drawer

import instagramsaverv1.igsaver.generated.resources.Res
import instagramsaverv1.igsaver.generated.resources.aboutus
import instagramsaverv1.igsaver.generated.resources.downloaded_media
import instagramsaverv1.igsaver.generated.resources.downloadv1
import instagramsaverv1.igsaver.generated.resources.exo_styled_controls_play
import instagramsaverv1.igsaver.generated.resources.help_and_feedback
import instagramsaverv1.igsaver.generated.resources.highlights
import instagramsaverv1.igsaver.generated.resources.ic_baseline_help_24
import instagramsaverv1.igsaver.generated.resources.ic_baseline_info_24
import instagramsaverv1.igsaver.generated.resources.ic_baseline_link_24
import instagramsaverv1.igsaver.generated.resources.ic_baseline_person_24
import instagramsaverv1.igsaver.generated.resources.ic_baseline_privacy_tip_24
import instagramsaverv1.igsaver.generated.resources.ic_baseline_settings_24
import instagramsaverv1.igsaver.generated.resources.ic_baseline_share_24
import instagramsaverv1.igsaver.generated.resources.ic_baseline_star_rate_24
import instagramsaverv1.igsaver.generated.resources.ic_instagram_reels
import instagramsaverv1.igsaver.generated.resources.link_download
import instagramsaverv1.igsaver.generated.resources.posts
import instagramsaverv1.igsaver.generated.resources.privacy_policy
import instagramsaverv1.igsaver.generated.resources.rate_us
import instagramsaverv1.igsaver.generated.resources.reels
import instagramsaverv1.igsaver.generated.resources.settings
import instagramsaverv1.igsaver.generated.resources.share_app
import instagramsaverv1.igsaver.generated.resources.stories
import instagramsaverv1.igsaver.generated.resources.title_favourites_user
import instagramsaverv1.igsaver.generated.resources.view_profile_pic
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource


sealed class NavDrawerItem(
    val stringRes: StringResource,
    val iconRes: DrawableResource,
    val isLoginRequired: Boolean = false
) {
    data object Stories : NavDrawerItem(Res.string.stories, Res.drawable.stories, true)
    data object TitleFavouritesUser :
        NavDrawerItem(Res.string.title_favourites_user, Res.drawable.ic_baseline_star_rate_24)

    data object Posts : NavDrawerItem(Res.string.posts, Res.drawable.posts, true)
    data object Reels : NavDrawerItem(Res.string.reels, Res.drawable.ic_instagram_reels, true)
    data object Highlights :
        NavDrawerItem(Res.string.highlights, Res.drawable.exo_styled_controls_play, true)

    data object DownloadedMedia :
        NavDrawerItem(Res.string.downloaded_media, Res.drawable.downloadv1)

    data object LinkDownload :
        NavDrawerItem(Res.string.link_download, Res.drawable.ic_baseline_link_24)

    data object ViewProfilePic :
        NavDrawerItem(Res.string.view_profile_pic, Res.drawable.ic_baseline_person_24, true)

    data object Settings : NavDrawerItem(Res.string.settings, Res.drawable.ic_baseline_settings_24)
    data object HelpAndFeedback :
        NavDrawerItem(Res.string.help_and_feedback, Res.drawable.ic_baseline_help_24)

    data object RateUs : NavDrawerItem(Res.string.rate_us, Res.drawable.ic_baseline_star_rate_24)
    data object ShareApp : NavDrawerItem(Res.string.share_app, Res.drawable.ic_baseline_share_24)
    data object AboutUs : NavDrawerItem(Res.string.aboutus, Res.drawable.ic_baseline_info_24)
    data object PrivacyPolicy :
        NavDrawerItem(Res.string.privacy_policy, Res.drawable.ic_baseline_privacy_tip_24)
}


val drawerItemsList = listOf(
    NavDrawerItem.Stories,
    NavDrawerItem.TitleFavouritesUser,
    NavDrawerItem.Posts,
    NavDrawerItem.Reels,
    NavDrawerItem.Highlights,
    NavDrawerItem.DownloadedMedia,
    NavDrawerItem.LinkDownload,
    NavDrawerItem.ViewProfilePic,
    NavDrawerItem.Settings,
    NavDrawerItem.HelpAndFeedback,
    NavDrawerItem.RateUs,
    NavDrawerItem.ShareApp,
    NavDrawerItem.AboutUs,
    NavDrawerItem.PrivacyPolicy,
)


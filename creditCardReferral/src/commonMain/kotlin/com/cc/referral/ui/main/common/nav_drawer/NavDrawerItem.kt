package com.cc.referral.ui.main.common.nav_drawer

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource


sealed class NavDrawerItem(
    val stringRes: StringResource,
    val iconRes: DrawableResource,
) {
//    data object DemoItem : NavDrawerItem(Res.string.stories, Res.drawable.stories)

}


val drawerItemsList: List<NavDrawerItem> = listOf()


package com.cc.referral.ui.main.common.nav_drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun NavDrawerBody(items: List<NavDrawerItem>, onItemClicked: (NavDrawerItem) -> Unit) {

    LazyColumn(
        modifier = Modifier.background(MaterialTheme.colors.surface)
    ) {
        items(items) {
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp).padding(start = 7.dp)
                    .clickable {
                        onItemClicked(it)
                    }) {

                Image(
                    painter = painterResource(it.iconRes),
                    modifier = Modifier.size(25.dp),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = stringResource(it.stringRes),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
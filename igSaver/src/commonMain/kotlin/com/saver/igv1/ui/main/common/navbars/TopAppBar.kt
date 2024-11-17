package com.saver.igv1.ui.main.common.navbars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopAppBar(
    title: String,
    isHomeScreenVisible: Boolean = false,
    onNavigationIconClick: () -> Unit = {},
) {

    TopAppBar(title = { Text(title) },
        backgroundColor = MaterialTheme.colors.surface,
        contentColor = MaterialTheme.colors.onSurface,
        navigationIcon = {
            Icon(
                imageVector = if (isHomeScreenVisible) Icons.Default.Menu else
                    Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "Menu",
                modifier = Modifier.clickable {
                    onNavigationIconClick()
                }.padding(start = 6.dp)
            )
        })
}
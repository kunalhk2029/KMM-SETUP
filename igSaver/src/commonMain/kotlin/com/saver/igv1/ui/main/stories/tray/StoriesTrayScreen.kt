package com.saver.igv1.ui.main.stories.tray

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.saver.igv1.ui.main.common.components.DefaultScreenUI
import com.saver.igv1.ui.main.stories.tray.component.HorizontalTrayItem
import com.saver.igv1.ui.main.stories.tray.component.VerticalTrayItem

@Composable
fun StoriesTrayScreen(
    storiesTrayState: StoriesTrayState,
    navController: NavController,
    onEvents: (StoriesTrayEvents) -> Unit,
    onNavEvents: (StoriesTrayNavEvents) -> Unit
) {

    DefaultScreenUI(
        progressBarState = storiesTrayState.progressBarState.collectAsState().value,
        navController = navController,
    ) {

        Column(modifier = Modifier.fillMaxSize().padding(vertical = 20.dp)) {

            LazyRow {
                items(storiesTrayState.trayInfo ?: listOf()) {
                    HorizontalTrayItem(it) {
                        onNavEvents(StoriesTrayNavEvents.NavigateToMultipleTrayPreview(it))
                    }
                }
            }

            LazyColumn {
                items(storiesTrayState.trayInfo ?: listOf()) {
                    VerticalTrayItem(it) {
                        onNavEvents(StoriesTrayNavEvents.NavigateToSingleTrayPreview(it))
                    }
                }
            }
        }
    }
}
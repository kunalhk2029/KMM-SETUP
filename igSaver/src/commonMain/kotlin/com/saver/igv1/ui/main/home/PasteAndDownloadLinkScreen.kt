package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.saver.igv1.ui.main.common.components.DefaultScreenUI
import instagramsaverv1.igsaver.generated.resources.Res
import instagramsaverv1.igsaver.generated.resources.download
import instagramsaverv1.igsaver.generated.resources.paste_link
import instagramsaverv1.igsaver.generated.resources.paste_story_reels_igtv_links
import org.jetbrains.compose.resources.stringResource


@Composable
fun PasteAndDownloadLinkScreen(navController: NavHostController) {

    DefaultScreenUI(isDrawerEnabled = true, isTopBarVisible = true, navController = navController) {

        Column(modifier = Modifier.fillMaxSize()) {

            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colors.surface,
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = stringResource(Res.string.paste_story_reels_igtv_links),
                    style = TextStyle(
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.padding(15.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
            ) {

                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.weight(0.5f)
                ) {

                    Text(
                        text = stringResource(Res.string.paste_link),
                        style = TextStyle(fontSize = 16.sp, color = MaterialTheme.colors.onPrimary),
                        modifier = Modifier.padding(10.dp),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colors.surface,
                    modifier = Modifier.weight(0.5f)
                ) {

                    Text(
                        text = stringResource(Res.string.download),
                        style = TextStyle(fontSize = 16.sp, color = MaterialTheme.colors.primary),
                        modifier = Modifier.padding(10.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
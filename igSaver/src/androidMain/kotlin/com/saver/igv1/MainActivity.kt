package com.saver.igv1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import com.saver.igv1.business.domain.MediaPlayerProgressManager
import com.saver.igv1.business.domain.MultipleMediaPlayerManager
import com.saver.igv1.business.domain.SingleMediaPlayerManager
import com.saver.igv1.business.domain.VideoPlayerManager
import com.saver.igv1.ui.AuthenticationActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import org.koin.compose.koinInject

class MainActivity : ComponentActivity(), AndroidPlatformSpecificMethods {

    private val mediaPlayerProgressManager by inject<MediaPlayerProgressManager>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(this)
        }
    }

    override fun StartLoginActivity() {
        val intent = Intent(this, AuthenticationActivity::class.java)
        startActivity(intent)
    }


    override fun onResume() {
        super.onResume()
        mediaPlayerProgressManager.handleMediaResume()
    }

    override fun onPause() {
        super.onPause()
        mediaPlayerProgressManager.handleMediaPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayerProgressManager.destroyAll()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
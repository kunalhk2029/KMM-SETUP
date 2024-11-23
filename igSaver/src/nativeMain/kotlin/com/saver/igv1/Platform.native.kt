package com.saver.igv1

import com.saver.igv1.business.data.prefs.SharedPrefRepository
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import platform.Foundation.NSDate
import platform.Foundation.NSHTTPCookie
import platform.Foundation.NSHTTPCookieStorage
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.Foundation.timeIntervalSince1970
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.WebKit.*
import platform.darwin.NSObject

actual object AndroidVersion {
    actual fun isVersionAbove29(): Boolean {
        TODO("Not yet implemented")

    }

    actual fun isVersionAbove33(): Boolean {
        TODO("Not yet implemented")
    }

}

actual class WebView {
    actual fun load(
        url: String,
        sharedPrefRepository: SharedPrefRepository
    ) {
        val viewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        WebViewIOS(viewController!!).loadUrl(
            "https://www.instagram.com/accounts/login/",
            sharedPrefRepository
        )
    }
}


class WebViewIOS(
    private val viewController: UIViewController
) {

    val wKWebViewConfiguration = WKWebViewConfiguration().apply {
        preferences.javaScriptEnabled = true
        preferences.javaScriptCanOpenWindowsAutomatically = true
    }

    @OptIn(ExperimentalForeignApi::class)
    private val webView: WKWebView = WKWebView(
        frame = viewController.view.frame,
        configuration = wKWebViewConfiguration
    )

    fun loadUrl(
        url: String,
        sharedPrefRepository: SharedPrefRepository
    ) {
        CoroutineScope(Dispatchers.IO).launch {

            var sessionId: String? = null
            var csrfToken: String? = null

            while (true) {
                delay(2000L)
                val nsUrl = NSURL(string = "https://i.instagram.com/api/v1/feed/reels_tray/")
                val cookies = NSHTTPCookieStorage.sharedHTTPCookieStorage.cookiesForURL(nsUrl)
                // Combine all the cookies into a single string (name=value; ...)


                wKWebViewConfiguration.websiteDataStore.httpCookieStore.getAllCookies {
                    (it as List<NSHTTPCookie>).find { it.name == "sessionid" }?.value?.let { sessionid ->

                        val cookie = buildString {

                            it.find { it.name == "mid" }?.value?.let {
                                append("mid=$it; ")
                            }

                            it.find { it.name == "dpr" }?.value?.let {
                                append("dpr=$it; ")
                            }

                            it.find { it.name == "datr" }?.value?.let {
                                append("datr=$it; ")
                            }

                            it.find { it.name == "ig_did" }?.value?.let {
                                append("ig_did=$it; ")
                            }

                            it.find { it.name == "wd" }?.value?.let {
                                append("wd=$it; ")
                            }

                            it.find { it.name == "csrftoken" }?.value?.let {
                                csrfToken = it
                                append("csrftoken=$it; ")
                            }

                            it.find { it.name == "ds_user_id" }?.value?.let {
                                append("ds_user_id=$it; ")
                            }

                            append("sessionid=$sessionid")

                        }
                        sessionId = cookie

                    }
                }
                if (sessionId != null && csrfToken != null) {
                    sharedPrefRepository.initAuthKeys(
                        sessionId = sessionId!!,
                        csrfToken = csrfToken!!,
                        userId = "userid",
                        null
                    )
                    if (sharedPrefRepository.getSessionId() != null) {
                        withContext(Dispatchers.Main) {
                            webView.removeFromSuperview()
                        }
                        break
                    }
                }
            }
        }
        val nsUrl = NSURL(string = url)
        val request = NSURLRequest(nsUrl)
        viewController.view.addSubview(webView)
        webView.navigationDelegate = object : NSObject(), WKNavigationDelegateProtocol {

            override fun webView(
                webView: WKWebView,
                navigationAction: WKNavigationAction,
                didBecomeDownload: WKDownload
            ) {
            }

            override fun webView(webView: WKWebView, didFinishNavigation: WKNavigation?) {
                println("Resource loaded: $url")
            }

            override fun webView(
                webView: WKWebView,
                navigationResponse: WKNavigationResponse,
                didBecomeDownload: WKDownload
            ) {
            }
        }

        webView.loadRequest(request)
    }
}

actual fun getCurrentTimeInMillis(): Long {
    return NSDate().timeIntervalSince1970.toLong() * 1000
}
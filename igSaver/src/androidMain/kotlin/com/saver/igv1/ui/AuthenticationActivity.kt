package com.saver.igv1.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.work.*
import com.saver.igv1.MainActivity
import com.saver.igv1.business.data.auth.InstagramAuthSessionManager
import com.saver.igv1.business.data.prefs.SharedPrefRepository
import com.saver.igv1.utils.Constants.Constants.KEEP_LOGIN_ACTIVE
import com.saver.igv1.utils.Constants.InstagramAuthConstansts.SHOW_CONFIRM_DIALOG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.*

class AuthenticationActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    val sharedPrefRepository by inject<SharedPrefRepository>()
    val instagramAuthSessionManager by inject<InstagramAuthSessionManager>()

    lateinit var cookie: CookieManager
    var count = false
    var logoutMode: Boolean = false//true for logout
    var foundsessionid = false
    var refreslayout: SwipeRefreshLayout? = null
    var webView: WebView? = null
    var savedSessionId: Channel<Boolean> = Channel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.saver.igv1.R.layout.activity_authentication)

        logoutMode = intent.getBooleanExtra("LOGOUT_MODE", false)
        refreslayout = findViewById(com.saver.igv1.R.id.refreshl)
        refreslayout?.setOnRefreshListener(this)
        refreslayout?.isRefreshing = true

        CoroutineScope(IO).launch {
//            liveStoriesCacheStorageService.deleteAllStories()
        }
        lifecycleScope.launch(IO) {
            /** Db Entities to clear
             * 1-dbentity10
             * 2-dbentity11
             * 3-dbentity24
             * 4-dbentity27
             */
//            appDao.deleteLiveStoriesTrayData()
//            appDao.deleteLiveStoriesData()
//            appDao.deleteAllIndividualHighlights()
//            appDao.deleteunSeenStories()
            sharedPrefRepository.ACTIVE_PROFILE_USERNAME(null)
            sharedPrefRepository.APP_OPENED_TIME(0L)
            com.saver.igv1.Logger.log("Debug 8929 logout clearing worker cleared entities..........")
        }

        sharedPrefRepository.resetAuthKeys()

        savedSessionId.receiveAsFlow().onEach {
            if (it) {
                val intent =
                    Intent(
                        this@AuthenticationActivity,
                        MainActivity::class.java
                    )
                if (!logoutMode) {
                    intent.putExtra(SHOW_CONFIRM_DIALOG, true)
                }
                startActivity(intent)
                finish()
            }
        }.launchIn(lifecycleScope)

        fetchsession()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun fetchsession() {
        webView = this.findViewById(com.saver.igv1.R.id.webclient)
        cookie = CookieManager.getInstance()
        cookie.acceptCookie()
        cookie.acceptThirdPartyCookies(webView)
        webView?.settings?.javaScriptCanOpenWindowsAutomatically = true
        webView?.settings?.javaScriptEnabled = true
        webView?.settings?.domStorageEnabled = true
        webView?.loadUrl("https://www.instagram.com/logout")
//        webView?.loadUrl("https://www.instagram.com/challenge/?next=/api/v1/feed/reels_tray/%253Fis_following_feed%253Dfalse")


        webView?.webViewClient = object : WebViewClient() {

            @SuppressLint("NewApi")
            override fun shouldInterceptRequest(
                view: WebView,
                request: WebResourceRequest
            ): WebResourceResponse? {
                return null
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (count == false) {
                    refreslayout?.isRefreshing = false
                    count = true
                }
            }


            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                if (!logoutMode) {
                    if (!foundsessionid && count) {
                        val f = cookie.getCookie("https://i.instagram.com/api/v1/feed/reels_tray/")
                        if (!f.isNullOrEmpty()) {
                            if (f.contains("session", true)) {
                                webView?.loadUrl("about:blank")
                                try {
                                    foundsessionid = true
                                    count = false
                                    val csrfindex = f.indexOf("csrftoken", 0, false)
                                    val csrfsubstr = f.substring(csrfindex)
                                    val csrflastindex = csrfsubstr.indexOf(";", 0, false)
                                    val csrf = csrfsubstr.subSequence(
                                        10,
                                        if (csrflastindex != -1) csrflastindex else csrfsubstr.length
                                    )
                                    val userAgent = webView?.settings?.userAgentString
                                    sharedPrefRepository.initAuthKeys(
                                        sessionId = f,
                                        csrfToken = csrf.toString(),
                                        userId = "userid",
                                        userAgent = userAgent
                                    )
//                                    sessionid = f
//                                    csrfToken = csrf.toString()
//                                    useragent = webView?.settings?.userAgentString.toString()
                                    val map =
                                        instagramAuthSessionManager.getRequestHeadersMap()

                                    if (sharedPrefRepository.getSessionId() != null) {
                                        lifecycleScope.launch {
                                            sharedPrefRepository.setLoginInActiveTrackerLoggerTime(
                                                System.currentTimeMillis()
                                            )
//                                            startKeepLogInActiveWorker()
//                                            webView?.clearCache(true)
//                                            cookie.removeAllCookies {
                                            lifecycleScope.launch {
                                                savedSessionId.send(true)
                                                savedSessionId.close()
                                            }
//                                            }
                                        }
                                    } else {
                                        foundsessionid = false
                                        count = false
                                        webView?.loadUrl("https://instagram.com/accounts/logout/")
                                    }
//                                    try {
//                                        CoroutineScope(IO).launch {
//                                            val index = f.indexOf("ds_user_id", 0, false)
//                                            val substr = f.substring(index)
//                                            var l = 11
//                                            for (i in 1..substr.substring(11).length) {
//                                                if (substr[i + 10].isDigit()) {
//                                                    l++
//                                                } else {
//                                                    break
//                                                }
//                                            }
//                                            val userid = substr.subSequence(11, l).toString()
//                                            val requestPostUrl =
//                                                "https://i.instagram.com/api/v1/feed/user/$userid/?count=12"
//                                            val request = object :
//                                                JsonObjectRequest(GET, requestPostUrl, null, {
//                                                    try {
//                                                        val countryCode =
//                                                            (this@AuthenticationActivity.getSystemService(
//                                                                Context.TELEPHONY_SERVICE
//                                                            ) as TelephonyManager).networkCountryIso
//                                                        val profileData = it.getJSONObject("user")
//                                                        val username =
//                                                            profileData.getString("username")
//                                                        val fullname =
//                                                            profileData.getString("full_name")
//                                                        sharedPrefRepository.ACTIVE_PROFILE_USERNAME(
//                                                            "$username fullname =$fullname"
//                                                        )
//                                                        Logger.log("6595959 = " + profileData.toString())
//                                                        val message =
//                                                            "Logged-In User Info Data username =$username fullname=$fullname Country Code =$countryCode"
//                                                        FirebaseCrashlytics.getInstance()
//                                                            .log(message)
//                                                        try {
//                                                            throw Exception(message)
//                                                        } catch (e: Exception) {
//                                                            e.message?.let {
//                                                                FirebaseCrashlytics.getInstance()
//                                                                    .recordException(e)
//                                                            }
//                                                        }
//                                                    } catch (e: Exception) {
//
//                                                    }
//                                                }, {
//                                                    Logger.log("6595959 error = " + it.message)
//                                                }) {
//                                                override fun getHeaders(): MutableMap<String, String> {
//                                                    return map
//                                                }
//                                            }
//                                            Volley.newRequestQueue(this@AuthenticationActivity)
//                                                .add(request)
//                                        }
//                                    } catch (e: Exception) {
//                                    }
                                } catch (e: Exception) {
                                    try {
                                        throw Exception("Authenticated Error Screen message=${e.message} seesinid =$f")
                                    } catch (e: Exception) {
                                        e.message?.let {
//                                            FirebaseCrashlytics.getInstance().recordException(e)
                                        }
                                    }
//                                    Logger.log("8994894894 = " + e.message)
                                    sharedPrefRepository.resetAuthKeys()
//                                    MaterialDialog(this@AuthenticationActivity).show {
//                                        title(null, "Some Error occured Retry Log-In")
//                                        positiveButton(null, getString(R.string.ok)) {
//                                        }
//                                    }.setOnDismissListener {
//                                        foundsessionid = false
//                                        count = false
//                                        webView?.loadUrl("https://instagram.com/accounts/logout/")
//                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (count) {
                        count = false
                        val workManager = WorkManager.getInstance(this@AuthenticationActivity)
                        workManager.cancelUniqueWork(KEEP_LOGIN_ACTIVE)
//                        MaterialDialog(this@AuthenticationActivity).show {
//                            cancelOnTouchOutside(false)
//                            setContentView(R.layout.confirm_login_dialog)
//                            findViewById<TextView>(R.id.note).visibility = View.GONE
//                            findViewById<TextView>(R.id.confirmheader).text =
//                                getString(R.string.confirm_log_out)
//                            findViewById<TextView>(R.id.message).text =
//                                getString(R.string.logoutmessage)
//                            findViewById<TextView>(R.id.understood).setOnClickListener {
//                                dismiss()
//                            }
//                        }.setOnDismissListener {
//                            MaterialDialog(this@AuthenticationActivity).show {
//                                title(null, "Logged-Out Successfully")
//                                positiveButton(null, "Log-In Again") {
//                                    logoutMode = false
//                                }
//                                negativeButton(null, getString(R.string.dismiss)) {
//
//                                }
//                            }.setOnDismissListener {
//                                if (logoutMode) {
//                                    lifecycleScope.launch {
//                                        savedSessionId.send(true)
//                                        savedSessionId.close()
//                                    }
//                                }
//                            }
//                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        refreslayout = null
        webView = null
    }

//    private fun startKeepLogInActiveWorker(): UUID {
//        val constraints =
//            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
//
//        val workManager = WorkManager.getInstance(this)
//
//        workManager.cancelUniqueWork(KEEP_LOGIN_ACTIVE)
//        val workerperiodicrequest = PeriodicWorkRequestBuilder<KeepLogInActiveWorker>(
//            5,
//            TimeUnit.HOURS
//        )
//            .setConstraints(constraints)
//            .build()
//        workManager.enqueueUniquePeriodicWork(
//            KEEP_LOGIN_ACTIVE,
//            ExistingPeriodicWorkPolicy.KEEP, workerperiodicrequest
//        )
//        return workerperiodicrequest.id
//    }

    override fun onRefresh() {
        webView?.loadUrl("about:blank")
        foundsessionid = false
        count = false
        webView?.loadUrl("https://instagram.com/accounts/logout/")
    }
}
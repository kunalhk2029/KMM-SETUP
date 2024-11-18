package com.saver.igv1.business.data.auth

import com.saver.igv1.Logger
import com.saver.igv1.business.data.prefs.SharedPrefRepository


class InstagramAuthSessionManager(private val sharedPrefRepository: SharedPrefRepository) {


    fun getSessionId(): String? {
        val sessionid = sharedPrefRepository.getSessionId()
        Logger.log("5646464 sessionid // $sessionid")
        return sessionid
    }

    fun getCsrfToken(): String? {
        val csrfToken = sharedPrefRepository.getCsrfToken()
        Logger.log("5646464 getCsrfToken // $csrfToken")
        return csrfToken
    }


    fun getRequestHeadersMap(): HashMap<String, String?> {
        val sessionid = sharedPrefRepository.getSessionId()
        val csrfToken = sharedPrefRepository.getCsrfToken()
        val requestHeadersMap: HashMap<String, String?> = HashMap()
        requestHeadersMap["cookie"] = sessionid
        requestHeadersMap["x-ig-app-id"] = "936619743392459"
        requestHeadersMap["user-agent"] =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.0.0 Safari/537.36"
        requestHeadersMap["authority"] = "i.instagram.com"
        requestHeadersMap["origin"] = "https://www.instagram.com"
        requestHeadersMap["referer"] = "https://www.instagram.com/"
        requestHeadersMap["x-asbd-id"] = "198387"
        requestHeadersMap["x-csrftoken"] = csrfToken
        requestHeadersMap["sec-ch-ua"] =
            "\"Google Chrome\";v=\"105\", \"Not)A;Brand\";v=\"8\", \"Chromium\";v=\"105\""
        requestHeadersMap["sec-ch-ua-mobile"] = "?0"
        requestHeadersMap["scheme"] = "https"
        requestHeadersMap["accept"] = "*/*"
        requestHeadersMap["accept-language"] = "en-US,en;q=0.9"
        requestHeadersMap["sec-ch-ua-platform"] = "Windows"
        requestHeadersMap["sec-fetch-site"] = "same-site"
        requestHeadersMap["sec-fetch-dest"] = "empty"
        requestHeadersMap["sec-fetch-mode"] = "cors"
        requestHeadersMap["x-requested-with"] = "XMLHttpRequest"
        return requestHeadersMap
    }

    fun getRequestBodyFormUrlParameterMap(
        variables: String
    ): HashMap<String, String?> {
        return hashMapOf(
//            "av" to "17841450617458960",
//            "__d" to "www",
//            "__user" to "0",
//            "__a" to "1",
//            "__req" to "y",
//            "__hs" to "20021.HYP:instagram_web_pkg.2.1..0.1",
//            "dpr" to "2",
//            "__ccg" to "UNKNOWN",
//            "__rev" to "1017666166",
//            "__s" to "yoj9v3:5p009n:1tdfv2",
//            "__hsi" to "7429752617756403048",
//            "__dyn" to "7xeUjG1mxu1syUbFp41twpUnwgU7SbzEdF8aUco2qwJxS0k24o1DU2_CwjE1xoswaq0yE462mcw5Mx62G5UswoEcE7O2l0Fwqo31w9a9wtUd8-U2zxe2GewGw9a361qw8Xxm16wUwtEvwww4WCwLyESE7i3vwDwHg2ZwrUdUbGwmk0zU8oC1Iwqo5q3e3zhA6bwIxe6V89F8uwm9EO6UaU3cG8BK4o",
//            "__csr" to "igogm_4MgEQASDFYhePinEIRifRJRD9ijnBVQBVkWXozGERDi9mjmuex6QiLynGCUBujCFeUGiJoytt4gybACyagW58CeAAgyqu5bgW4lztbAG4HzaAxfFy-RVkcRh9XV9FAiubwGBgvwBHV8SCleuA44aBw04UumWwt40kW3q543Rw3W943twxw5Ww1kO0a8CDwJw28o1jQ2q_i5wUAwqywdB9a6k2u1kzE3hVpy0kjwHpplggSnfDmkm1qwGzE3MUug7C3l0Kw9K0Hk1CwsE0UPoW00yro3kw0CNw",
//            "__comet_req" to "7",
//            "fb_dtsg" to "NAcMsI7MryH0eylp7hyfMQP7F7Qq6mud0LA5HRoxqMFd70ilu2bzHFw:17843676607167008:1729847069",
//            "jazoest" to "26303",
//            "lsd" to "JW6bsacAQwEdlDxXrgVciw",
//            "__spin_r" to "1017666166",
//            "__spin_b" to "trunk",
//            "__spin_t" to "1729874084",
//            "fb_api_caller_class" to "RelayModern",
//            "fb_api_req_friendly_name" to "PolarisStoriesV3ReelPageGalleryQuery",
            "variables" to variables,
//            "server_timestamps" to "true",
            "doc_id" to "8335556993194383"
        )
    }
}
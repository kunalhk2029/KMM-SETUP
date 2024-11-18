package com.saver.igv1.business.data.network.utils

import com.saver.igv1.business.data.auth.InstagramAuthSessionManager
import com.saver.igv1.business.data.network.userProfileService.model.UserProfileDetailResponse
import com.saver.igv1.business.data.prefs.SharedPrefRepository
import com.saver.igv1.business.domain.DataState
import com.saver.igv1.business.domain.ProgressBarState
import com.saver.igv1.business.domain.UIComponent
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.http.formUrlEncode
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.math.round

class KtorIGRequestHandler(
    val httpClient: HttpClient,
    val instagramAuthSessionManager: InstagramAuthSessionManager,
    val sharedPrefRepository: SharedPrefRepository
) {

    suspend inline fun <reified Response, Result> handleApiRequest(
        url: String,
        requestType: HttpsRequestMethod = HttpsRequestMethod.GET,
        requestHeaders: HashMap<String, String?> = instagramAuthSessionManager.getRequestHeadersMap(),
        requestBodyFormUrlParameterMap: HashMap<String, String?>? = null,
        crossinline handleResponse: (Response) -> Result,
    ): Flow<DataState<Result>> {
        return flow {

            emit(DataState.Loading(ProgressBarState.Loading))

            try {
                // Make the network request
                val response: HttpResponse =
                    if (requestType == HttpsRequestMethod.GET)
                        httpClient.get(url) {
                            headers {
                                requestHeaders.forEach { (key, value) ->
                                    value?.let { append(key, it) }
                                }
                            }
                        }
                    else
                        httpClient.post(url) {
                            headers {
                                requestHeaders.forEach { (key, value) ->
                                    value?.let { append(key, it) }
                                    append("Content-Type", "application/x-www-form-urlencoded")
                                }
                            }

                            setBody(
                                Parameters.build {
                                    requestBodyFormUrlParameterMap?.let {
                                        it.forEach { (key, value) ->
                                            value?.let { append(key, it) }
                                        }
                                    }
                                }.formUrlEncode()
                            )
                        }

                // Check if the response is successful
                emit(
                    if (response.status.isSuccess()) {
                        // Deserialize the response body using reified type
                        val responseBody: Response = response.body()
                        DataState.Data(handleResponse(responseBody))
                    } else {
                        // Handle non-successful response
                        println("Error: ${response.status}")
                        DataState.Response(
                            UIComponent.IgApiResponseError(
                                InstagramApiError.GenericError("Error: ${response.status}")
                            )
                        )
                    }
                )

                if (sharedPrefRepository.getLoggedInUsername() == null) {
                    makeInstagramRequest()
                }else{
                    println("654645 ALREADY Logged in username: ${sharedPrefRepository.getLoggedInUsername()}")
                }

            } catch (e: Exception) {
                println("Error: ${e.message}")

                emit(
                    DataState.Response(
                        UIComponent.IgApiResponseError(
                            InstagramApiError.GenericError("Error: ${e.message}")
                        )
                    )
                )
            } finally {
                emit(DataState.Loading(ProgressBarState.Idle))
            }
        }
    }


    suspend fun makeInstagramRequest() {
        try {
            val url = "https://www.instagram.com/graphql/query"
            val response: HttpResponse = httpClient.post(url) {
                headers {
                    append(HttpHeaders.Accept, "*/*")
                    append(HttpHeaders.AcceptLanguage, "en-GB,en-US;q=0.9,en;q=0.8")
                    append(HttpHeaders.ContentType, "application/x-www-form-urlencoded")
                    append(
                        HttpHeaders.Cookie,
                        instagramAuthSessionManager.getSessionId() ?: ""
                    )
                    append("Origin", "https://www.instagram.com")
                    append("Priority", "u=1, i")
                    append(HttpHeaders.Referrer, "https://www.instagram.com/checkagain2029/")
                    append("Sec-CH-Prefers-Color-Scheme", "dark")
                    append(
                        "Sec-CH-UA",
                        "\"Chromium\";v=\"130\", \"Google Chrome\";v=\"130\", \"Not?A_Brand\";v=\"99\""
                    )
                    append(
                        "Sec-CH-UA-Full-Version-List",
                        "\"Chromium\";v=\"130.0.6723.119\", \"Google Chrome\";v=\"130.0.6723.119\", \"Not?A_Brand\";v=\"99.0.0.0\""
                    )
                    append("Sec-CH-UA-Mobile", "?0")
                    append("Sec-CH-UA-Model", "\"\"")
                    append("Sec-CH-UA-Platform", "\"macOS\"")
                    append("Sec-CH-UA-Platform-Version", "\"14.5.0\"")
                    append("Sec-Fetch-Dest", "empty")
                    append("Sec-Fetch-Mode", "cors")
                    append("Sec-Fetch-Site", "same-origin")
                    append(
                        HttpHeaders.UserAgent,
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36"
                    )
                    append("X-ASBD-ID", "129477")
                    append(
                        "X-Bloks-Version-ID",
                        "09d4437a3b9f5707ed0adf4614de5f4d546124576e17ba49716eb9823d803aea"
                    )
                    append("X-CSRFToken", instagramAuthSessionManager.getCsrfToken() ?: "")
                    append("X-FB-Friendly-Name", "PolarisProfilePageContentQuery")
                    append("X-FB-LSD", "UvlN3hYntd_CX3H2a6M0Xu")
                    append("X-IG-App-ID", "936619743392459")
                }
                setBody(
                    "av=17841450617458960&__d=www&__user=0&__a=1&__req=2&__hs=20045.HYP%3Ainstagram_web_pkg.2.1..0.1&dpr=2&__ccg=EXCELLENT&__rev=1018277862&__s=8nsoi8%3Ad0ofrd%3Aijusw3&__hsi=7438670639849946366&__dyn=7xe5WwlEnwn8K2Wmm1twpUnwgU7S6EdF8aUco38w5ux60p-0LVE4W0qa0FE2awgo1EUhwnU6a3a0EA2C0iK0D830wae4UaEW2G0AEco5G0zE5W0Y81eEdEGdwtU662O0Lo6-3u2WE15E6O1FwlE6PhA6bwg8rAwHxW1oCz8rwHwcOEym5oqw&__csr=&__comet_req=7&fb_dtsg=NAcPDXc3X1f4-V9kQRThtXBZNPGazPnuQdxcT6K3yjBsqlSv-f1jxDA%3A17864955220006059%3A1730067251&jazoest=26242&lsd=UvlN3hYntd_CX3H2a6M0Xu&__spin_r=1018277862&__spin_b=trunk&__spin_t=1731950473&fb_api_caller_class=RelayModern&fb_api_req_friendly_name=PolarisProfilePageContentQuery&variables=%7B%22id%22%3A%2250551687248%22%2C%22render_surface%22%3A%22PROFILE%22%7D&server_timestamps=true&doc_id=9539110062771438"
                )
            }
            println(response.status)
            val userProfileDetailResponse = response.body() as UserProfileDetailResponse
            userProfileDetailResponse.data?.user?.let {
                it.username?.let { it1 ->
                    println("654645 Logged in username: $it1")
                    sharedPrefRepository.setLoggedInUsername(it1)
                }
            }
        } catch (e: Exception) {
        }
    }


    suspend fun getFileSize(url: String): Double? {
        return try {
            val response = httpClient.head(url)
            val contentLengthInBytes = response.headers[HttpHeaders.ContentLength]?.toLongOrNull()
            // Convert bytes to MB (bytes / 1024 / 1024)
            val contentLengthInMegaBytes = contentLengthInBytes?.let { it / (1024.0 * 1024.0) }
            contentLengthInMegaBytes?.let {
                round(contentLengthInMegaBytes * 100) / 100
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}


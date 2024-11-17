package com.saver.igv1.business.data.network.utils

import com.saver.igv1.business.data.auth.InstagramAuthSessionManager
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
    val instagramAuthSessionManager: InstagramAuthSessionManager
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


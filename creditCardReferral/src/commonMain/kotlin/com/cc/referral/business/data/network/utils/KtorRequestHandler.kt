package com.cc.referral.business.data.network.utils

import com.cc.referral.business.domain.DataState
import com.cc.referral.business.domain.ProgressBarState
import com.cc.referral.business.domain.UIComponent
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.onDownload
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.*
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.http.formUrlEncode
import io.ktor.http.isSuccess
import io.ktor.util.InternalAPI
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.math.round


class KtorHttpRequestHandler(
    val httpClient: HttpClient
) {
    suspend inline fun <reified Response> handleApiRequest(
        url: String,
        requestType: HttpsRequestMethod = HttpsRequestMethod.GET,
        requestHeaders: HashMap<String, String?>? = null,
        bodyContent: String? = null,
        urlParameters: HashMap<String, String?>? = null,
        formDataParameters: HashMap<String, String?>? = null,
        contentType: ContentType? = null,
        multiPartFormDataContent: MultiPartFormDataContent? = null,
        noinline downloadProgressCallback: ((Float) -> Unit)? = null,
        noinline uploadProgressCallback: ((Float) -> Unit)? = null,
    ): Flow<DataState<Response>> {
        return flow {

            emit(DataState.Loading(ProgressBarState.Loading))

            try {
                // Make the network request
                val response: HttpResponse =
                    if (requestType == HttpsRequestMethod.GET) {
                        httpClient.get(url) {
                            configRequest(
                                requestHeaders = requestHeaders,
                                bodyContent = bodyContent,
                                formDataParameters = formDataParameters,
                                urlParameters = urlParameters,
                                contentType = contentType,
                                multiPartFormDataContent = multiPartFormDataContent,
                                downloadProgressCallback = downloadProgressCallback,
                                uploadProgressCallback = uploadProgressCallback
                            )
                        }
                    } else if (requestType == HttpsRequestMethod.POST) {
                        httpClient.post(url) {
                            configRequest(
                                requestHeaders = requestHeaders,
                                bodyContent = bodyContent,
                                formDataParameters = formDataParameters,
                                urlParameters = urlParameters,
                                contentType = contentType,
                                multiPartFormDataContent = multiPartFormDataContent,
                                downloadProgressCallback = downloadProgressCallback,
                                uploadProgressCallback = uploadProgressCallback
                            )
                        }
                    } else if (requestType == HttpsRequestMethod.PUT) {
                        httpClient.put(url) {
                            configRequest(
                                requestHeaders = requestHeaders,
                                bodyContent = bodyContent,
                                formDataParameters = formDataParameters,
                                urlParameters = urlParameters,
                                contentType = contentType,
                                multiPartFormDataContent = multiPartFormDataContent,
                                downloadProgressCallback = downloadProgressCallback,
                                uploadProgressCallback = uploadProgressCallback
                            )
                        }
                    } else if (requestType == HttpsRequestMethod.DELETE) {
                        httpClient.delete(url) {
                            configRequest(
                                requestHeaders = requestHeaders,
                                bodyContent = bodyContent,
                                formDataParameters = formDataParameters,
                                urlParameters = urlParameters,
                                contentType = contentType,
                                multiPartFormDataContent = multiPartFormDataContent,
                                downloadProgressCallback = downloadProgressCallback,
                                uploadProgressCallback = uploadProgressCallback
                            )
                        }
                    } else {
                        httpClient.get(url) {
                            configRequest(
                                requestHeaders = requestHeaders,
                                bodyContent = bodyContent,
                                formDataParameters = formDataParameters,
                                urlParameters = urlParameters,
                                contentType = contentType,
                                multiPartFormDataContent = multiPartFormDataContent,
                                downloadProgressCallback = downloadProgressCallback,
                                uploadProgressCallback = uploadProgressCallback
                            )
                        }
                    }

                // Check if the response is successful
                emit(
                    if (response.status.isSuccess()) {
                        // Deserialize the response body using reified type
                        val responseBody: Response = response.body()
                        DataState.Data(responseBody)
                    } else {
                        // Handle non-successful response
                        DataState.Response(
                            UIComponent.Error(
                                "Something went wrong"
                            )
                        )

                    }
                )
            } catch (e: Exception) {
                println("Error: ${e.message}")

                when (e) {

                    is IOException -> {
                        emit(
                            DataState.Response(
                                UIComponent.Error(
                                    "Please check your N/W Connection"
                                )
                            )
                        )
                    }


                    is ClientRequestException -> {
                        emit(
                            DataState.Response(
                                UIComponent.Error(
                                    e.message
                                )
                            )
                        )
                    }

                    is SocketTimeoutException -> {
                        emit(
                            DataState.Response(
                                UIComponent.Error(
                                    e.message ?: "Something went wrong"
                                )
                            )
                        )
                    }


                    else -> {
                        emit(
                            DataState.Response(
                                UIComponent.Error(
                                    e.message ?: "Something went wrong"
                                )
                            )
                        )
                    }
                }

            } finally {
                emit(DataState.Loading(ProgressBarState.Idle))
            }
        }
    }
}

@OptIn(InternalAPI::class)
fun HttpRequestBuilder.configRequest(
    requestHeaders: HashMap<String, String?>?,
    bodyContent: String?,
    formDataParameters: HashMap<String, String?>?,
    urlParameters: HashMap<String, String?>?,
    contentType: ContentType?,
    multiPartFormDataContent: MultiPartFormDataContent?,
    downloadProgressCallback: ((Float) -> Unit)? = null,
    uploadProgressCallback: ((Float) -> Unit)? = null,
) {
    headers {
        requestHeaders?.forEach { (key, value) ->
            value?.let { append(key, it) }
        }
    }

    url {
        setAttributes {
            urlParameters?.forEach {
                it.value?.let { value -> parameters.append(it.key, value) }
            }
        }
    }

    formDataParameters?.let {
        val formData =
            Parameters.build { // specify the form data to send in the body of the request
                it.forEach { (key, value) ->
                    value?.let { append(key, it) }
                }
            }
        body = formData.formUrlEncode()
        method = HttpMethod.Post
    }

    contentType?.let { contentType(it) }

    bodyContent?.let {
        setBody(
            bodyContent
        )
    }

    onDownload { bytesSentTotal, contentLength ->
        println("Sent $bytesSentTotal bytes from $contentLength")
        val percentage = (bytesSentTotal.toFloat() / contentLength.toFloat()) * 100f
        downloadProgressCallback?.invoke(percentage)
    }

    if (multiPartFormDataContent != null) {
        body = multiPartFormDataContent
    }

    onUpload { bytesSentTotal, contentLength ->
        println("Sent $bytesSentTotal bytes from $contentLength")
        val percentage = (bytesSentTotal.toFloat() / contentLength.toFloat()) * 100f
        if (percentage.toInt() % 10 == 0) uploadProgressCallback?.invoke(percentage)
    }
}


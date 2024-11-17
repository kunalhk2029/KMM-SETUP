package com.saver.igv1.business.data.network.downloadService

import com.saver.igv1.business.domain.DataState
import com.saver.igv1.business.domain.UIComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.prepareGet
import io.ktor.http.contentLength
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.core.isEmpty
import io.ktor.utils.io.core.readBytes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext

class MediaDownloadServiceImpl(
    private val httpClient: HttpClient
) : MediaDownloadService {

    override suspend fun downloadMediaFile(
        url: String
    ): Flow<DataState<ByteArray>> {
        return channelFlow {
            withContext(Dispatchers.Default) {
                try {

                    val response = httpClient.prepareGet(url).execute()
                    val totalBytes =
                        response.contentLength()
                            ?: -1L // Get the total content length (if available)

                    val channel: ByteReadChannel = response.body()

                    var bytesRead = 0L
                    val byteArrayList = mutableListOf<Byte>()

                    // Read the data in chunks
                    while (!channel.isClosedForRead) {
                        ensureActive()
                        val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
                        while (!packet.isEmpty) {
                            ensureActive()
                            val bytes = packet.readBytes()
                            byteArrayList.addAll(bytes.toList())
                            bytesRead += bytes.size

                            // Calculate the progress
                            if (totalBytes > 0) {

                                val progress = (bytesRead.toFloat() / totalBytes * 100)

                                send(
                                    DataState.Response(
                                        UIComponent.DownloadingSingleMediaDialog(
                                            progress
                                        )
                                    )
                                )
                            }
                        }
                    }

                    byteArrayList.toByteArray().let {
                        send(DataState.Data(it))
                    }
                } catch (e: Exception) {
                    send(DataState.Response(UIComponent.Error(e.message ?: "Unknown Error")))
                }
            }
        }
    }
}
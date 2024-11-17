package com.saver.igv1.business.data.network.downloadService

import com.saver.igv1.business.domain.DataState
import kotlinx.coroutines.flow.Flow

const val DEFAULT_BUFFER_SIZE = 8192 // 8 KB (standard buffer size)

interface MediaDownloadService {

    suspend fun downloadMediaFile(
        url: String
    ): Flow<DataState<ByteArray>>

}
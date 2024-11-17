package com.saver.igv1.business.data.app_storage.external.utils

import java.io.OutputStream

typealias isStoredSuccessfully = Boolean

fun storeMediaFromByteArray(
    outputStream: OutputStream,
    byteArray: ByteArray,
    chunkSize: Int = 1024
): isStoredSuccessfully {
    var offset = 0
    var isStoredSuccessfully = false
    try {
        while (offset < byteArray.size) {
            val length = minOf(chunkSize, byteArray.size - offset)
            outputStream.write(byteArray, offset, length) // Write a chunk
            offset += length
        }
        outputStream.flush() // Ensure all data is written
        isStoredSuccessfully = true
    } catch (e: Exception) {

    } finally {
        outputStream.close() // Close the output stream to release resources
    }
    return isStoredSuccessfully
}
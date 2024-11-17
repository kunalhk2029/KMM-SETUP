package com.saver.igv1.business.data.app_storage.external.stories

import android.content.ContentResolver
import android.content.ContentValues
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.saver.igv1.AndroidVersion.isVersionAbove29
import com.saver.igv1.business.data.app_storage.external.utils.storeMediaFromByteArray
import com.saver.igv1.business.domain.DataState
import com.saver.igv1.business.domain.UIComponent
import com.saver.igv1.business.domain.models.stories.StoryMediaInfo
import com.saver.igv1.utils.Constants.StorageDirectoryConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

actual class StoriesExternalStorageServiceImpl(
    private val contentResolver: ContentResolver
) : StoriesExternalStorageService {

    override suspend fun saveStory(
        storyMediaInfo: StoryMediaInfo,
        byteArray: ByteArray
    ): Flow<DataState<SavedMediaUri>> = channelFlow {
        var uri: Uri?
        val username = storyMediaInfo.user?.username ?: ""
        val fullname = storyMediaInfo.user?.full_name ?: ""
        println("677688 username: $username")
        println("677688 fullname: $fullname")
//        if (isVersionAbove29()) {
            ContentValues().apply {
                put(
                    if (storyMediaInfo.isVideo == true)
                        MediaStore.Video.Media.DISPLAY_NAME else MediaStore.Images.Media.DISPLAY_NAME,
                    "$username##${storyMediaInfo.mediaTakenAtTimeStamp}##"
                )
                put(
                    if (storyMediaInfo.isVideo == true)
                        MediaStore.Video.Media.MIME_TYPE else MediaStore.Images.Media.MIME_TYPE,
                    if (storyMediaInfo.isVideo == true)
                        "video/mp4" else "image/jpeg"
                )
                put(
                    if (storyMediaInfo.isVideo == true)
                        MediaStore.Video.Media.RELATIVE_PATH else MediaStore.Images.Media.RELATIVE_PATH,
                    (if (storyMediaInfo.isVideo == true)
                        Environment.DIRECTORY_MOVIES
                    else Environment.DIRECTORY_PICTURES) + "/${StorageDirectoryConstants.MAIN_DIRECTORY}/${StorageDirectoryConstants.STORIES_DIRECTORY}/$username/$fullname"
                )
                uri = contentResolver.insert(
                    if (isVersionAbove29())
                        if (storyMediaInfo.isVideo == true)
                            MediaStore.Video.Media.getContentUri(
                                MediaStore.VOLUME_EXTERNAL_PRIMARY
                            ) else MediaStore.Images.Media.getContentUri(
                            MediaStore.VOLUME_EXTERNAL_PRIMARY
                        )
                    else MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    this
                )
            }

            if (uri == null) {
                send(DataState.Response(UIComponent.Dialog("Failed", "Failed to save story")))
            } else {
                contentResolver.openOutputStream(uri!!).use {
                    if (it != null) {
                        storeMediaFromByteArray(it, byteArray).let {
                            if (it) {
                                send(DataState.Data(uri.toString()))
                            } else {
                                send(
                                    DataState.Response(
                                        UIComponent.Dialog(
                                            "Failed",
                                            "Failed to save story"
                                        )
                                    )
                                )
                            }
                        }
                    } else {
                        contentResolver.delete(uri!!, null, null)
                        send(
                            DataState.Response(
                                UIComponent.Dialog(
                                    "Failed",
                                    "Failed to save story"
                                )
                            )
                        )
                    }
                }
            }
//        }
//        else {
//            var ext = ".jpg"
//            if (extension) {
//                ext = ".mp4"
//            }
//            lateinit var dr: File
//            if (extension) {
//                dr = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
//            } else {
//                dr = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//            }
//
//            if (!dr.exists()) {
//                dr.mkdir()
//            }
//
//            val fol = File(
//                dr.path,
//                if (isStory)
//                    "/${MAIN_DIRECTORY}/${StorageDirectoryConstants.STORIES_DIRECTORY}/" + "/$username/" + "/$fullname/"
//                else
//                    "/${MAIN_DIRECTORY}/Downloaded/" + "/$username/" + "/$fullname/"
//            )
//            fol.mkdirs()
//            val fileSavePath =
//                File(fol.path, if (isStory) "$username##$storytime##" + ext else name)
//            val fos =
//                FileOutputStream(fileSavePath)
//            if (storingprocess) {
//                fos.write(ips.readBytes())
//                fos.flush()
//                fos.close()
//                saved = true
//            } else {
//                storeMediaByParts(fos, ips)
//                saved = true
//            }
//            MediaScannerConnection.scanFile(
//                contextDependentMethods.getContext(),
//                arrayOf(fileSavePath.toString()),
//                null
//            ) { _, _ ->
//            }
//            ips.close()
//        }

    }
}
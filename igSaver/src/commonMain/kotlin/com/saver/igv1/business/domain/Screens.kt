package com.saver.igv1.business.domain

import kotlinx.serialization.Serializable


sealed class Screens {

    @Serializable
    data object Security : Screens()

    @Serializable
    data object PasteAndDownload : Screens()

    @Serializable
    data object Stories : Screens()

    @Serializable
    data class SingleTrayPreview(val trayItemPos: Int, val userName: String) : Screens()

    @Serializable
    data class MultipleTrayPreview(val trayItemPos: Int) : Screens()


    @Serializable
    data class SingleMediaPlayer(val mediaPosition:Int,val userName: String) : Screens()


}
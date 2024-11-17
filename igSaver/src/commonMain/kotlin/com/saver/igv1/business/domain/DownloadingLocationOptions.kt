package com.saver.igv1.business.domain

sealed class DownloadingLocationOptions(val uiValue: String) {
    data object AskEveryTime : DownloadingLocationOptions("Ask Every Time")
    data object InDrive : DownloadingLocationOptions("In-Drive")
    data object InPhone : DownloadingLocationOptions("In-Phone")
    data object InBoth : DownloadingLocationOptions("In-Both")
}

data class SelectedDownloadingLocationOptionData(
    val downloadingOption: DownloadingLocationOptions,
    val notAskAgainSelected: Boolean = false
)

fun getDownloadingLocationOption(uiValue: String): DownloadingLocationOptions {
    return when (uiValue) {
        DownloadingLocationOptions.AskEveryTime.uiValue -> {
            DownloadingLocationOptions.AskEveryTime
        }

        DownloadingLocationOptions.InDrive.uiValue -> {
            DownloadingLocationOptions.InDrive
        }

        DownloadingLocationOptions.InPhone.uiValue -> {
            DownloadingLocationOptions.InPhone
        }

        DownloadingLocationOptions.InBoth.uiValue -> {
            DownloadingLocationOptions.InBoth
        }

        else -> {
            DownloadingLocationOptions.AskEveryTime
        }
    }
}
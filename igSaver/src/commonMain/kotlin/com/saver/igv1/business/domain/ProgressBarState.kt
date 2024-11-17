package com.saver.igv1.business.domain

sealed class ProgressBarState {
    data object Loading : ProgressBarState()

    data object Idle : ProgressBarState()
}
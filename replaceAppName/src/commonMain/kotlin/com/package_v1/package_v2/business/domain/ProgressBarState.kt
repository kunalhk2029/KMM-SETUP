package com.package_v1.package_v2.business.domain

sealed class ProgressBarState {
    data object Loading : ProgressBarState()

    data object Idle : ProgressBarState()
}
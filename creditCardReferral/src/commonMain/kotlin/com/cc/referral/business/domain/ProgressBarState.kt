package com.cc.referral.business.domain

sealed class ProgressBarState {
    data object Loading : ProgressBarState()

    data object Idle : ProgressBarState()
}
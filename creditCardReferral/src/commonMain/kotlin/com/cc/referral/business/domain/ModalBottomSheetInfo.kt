package com.cc.referral.business.domain

import androidx.compose.runtime.MutableState

sealed class ModalBottomSheetInfo {

    data object None : ModalBottomSheetInfo()
    data class EnterOTP(
        val phoneNumber: String,
        val onVerifyClicked: (otp:String) -> Unit
    ) : ModalBottomSheetInfo()
}



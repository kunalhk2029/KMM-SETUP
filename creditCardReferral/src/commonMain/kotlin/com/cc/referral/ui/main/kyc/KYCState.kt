package com.cc.referral.ui.main.kyc

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.cc.referral.business.domain.ViewStateExt

data class KYCState(
    val name: MutableState<String?> = mutableStateOf(null),
    val mobileNumber: MutableState<String?> = mutableStateOf(null),
    val isWhatsAppUpdatesEnabled: Boolean = false,
    var isMale: MutableState<Boolean?> = mutableStateOf(null),
    var isTermsAndConditionsAccepted: MutableState<Boolean?> = mutableStateOf(null),
) : ViewStateExt()

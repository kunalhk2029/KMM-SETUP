package com.cc.referral.ui.onboarding.register

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.cc.referral.business.domain.ViewStateExt

data class RegisterState(
    val name: MutableState<String?> = mutableStateOf(null),
    val mobileNumber: MutableState<String?> = mutableStateOf(null),
    val isWhatsAppUpdatesEnabled: Boolean = false,
) : ViewStateExt()

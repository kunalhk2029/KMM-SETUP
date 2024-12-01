package com.cc.referral.ui.onboarding.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.cc.referral.business.domain.ViewStateExt

data class LoginState(
    val mobileNumber: MutableState<String?> = mutableStateOf(null),
    val isWhatsAppUpdatesEnabled: Boolean = false,
) : ViewStateExt()

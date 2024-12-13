package com.cc.referral.ui.onboarding.register

import com.cc.referral.business.domain.UIComponent

sealed class RegisterEvents() {

    data object RegisterWithOTP : RegisterEvents()
    data class UpdateWhatsAppUpdatesToggleState(val boolean: Boolean) : RegisterEvents()

    data class UpdateUIComponent(val uiComponent: UIComponent) : RegisterEvents()
    data object ResetOtpData : RegisterEvents()

    data class VerifyOTP(
        val otp: String,
    ) : RegisterEvents()
}

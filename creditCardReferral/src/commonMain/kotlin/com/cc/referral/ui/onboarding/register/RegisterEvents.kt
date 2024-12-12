package com.cc.referral.ui.onboarding.register

sealed class RegisterEvents() {

    data object RegisterWithOTP : RegisterEvents()
    data class UpdateWhatsAppUpdatesToggleState(val boolean: Boolean) : RegisterEvents()

    data class VerifyOTP(
        val otp: String,
    ) : RegisterEvents()
}

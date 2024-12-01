package com.cc.referral.ui.onboarding.register

sealed class RegisterEvents() {

    data class RegisterWithOTP(val name: String) : RegisterEvents()
    data class UpdateWhatsAppUpdatesToggleState(val boolean: Boolean) : RegisterEvents()
}

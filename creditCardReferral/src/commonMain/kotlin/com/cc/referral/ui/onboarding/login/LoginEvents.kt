package com.cc.referral.ui.onboarding.login

sealed class LoginEvents() {

    data class LoginWithOTP(val name: String) : LoginEvents()
    data class UpdateWhatsAppUpdatesToggleState(val boolean: Boolean) : LoginEvents()
}

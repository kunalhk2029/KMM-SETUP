package com.cc.referral.ui.onboarding.login

sealed class LoginNavEvents() {

    data object NavigateToCards : LoginNavEvents()
}

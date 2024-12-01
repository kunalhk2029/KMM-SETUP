package com.cc.referral.ui.onboarding.auth

sealed class AuthNavEvents {

    data object NavigateToRegisterScreen : AuthNavEvents()
    data object NavigateToLoginScreen : AuthNavEvents()
}
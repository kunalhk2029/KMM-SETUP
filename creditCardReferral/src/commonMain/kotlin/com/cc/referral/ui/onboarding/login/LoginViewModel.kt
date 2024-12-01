package com.cc.referral.ui.onboarding.login

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class LoginViewModel : ViewModel() {

    val state = MutableStateFlow(LoginState())


    fun onEvent(event: LoginEvents) {
        when (event) {
            is LoginEvents.LoginWithOTP -> {
                // Handle RegisterWithOTP event
            }

            is LoginEvents.UpdateWhatsAppUpdatesToggleState -> {
                state.value = state.value.copy(isWhatsAppUpdatesEnabled = event.boolean)
            }
        }
    }
}
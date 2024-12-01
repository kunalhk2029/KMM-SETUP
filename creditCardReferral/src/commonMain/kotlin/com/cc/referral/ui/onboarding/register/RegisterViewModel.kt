package com.cc.referral.ui.onboarding.register

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class RegisterViewModel : ViewModel() {

    val state = MutableStateFlow(RegisterState())


    fun onEvent(event: RegisterEvents) {
        when (event) {
            is RegisterEvents.RegisterWithOTP -> {
                // Handle RegisterWithOTP event
            }

            is RegisterEvents.UpdateWhatsAppUpdatesToggleState -> {
                state.value = state.value.copy(isWhatsAppUpdatesEnabled = event.boolean)
            }
        }
    }
}
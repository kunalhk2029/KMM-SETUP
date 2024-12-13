package com.cc.referral.ui.onboarding.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cc.referral.business.data.network.auth.AuthService
import com.cc.referral.business.domain.ModalBottomSheetInfo
import com.cc.referral.business.domain.parseDataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authService: AuthService
) : ViewModel() {

    val state = MutableStateFlow(RegisterState())


    fun onEvent(event: RegisterEvents) {
        when (event) {
            is RegisterEvents.RegisterWithOTP -> {
//                state.value.updateActiveModalBottomSheetInfo(ModalBottomSheetInfo.EnterOTP(
//                    state.value.mobileNumber.value ?: ""
//                ) {
//                    onEvent(RegisterEvents.VerifyOTP(it))
//                })
                viewModelScope.launch {
                    state.value.mobileNumber.value?.let {
                        authService.sendOtp(
                            it
                        ).collect {
                            parseDataState(state.value, it) {
                                state.value = state.value.copy(isOtpSent = true)
                                state.value.updateActiveModalBottomSheetInfo(ModalBottomSheetInfo.EnterOTP(
                                    state.value.mobileNumber.value ?: ""
                                ) {
                                    onEvent(RegisterEvents.VerifyOTP(it))
                                })
                            }
                        }
                    }
                }
            }

            is RegisterEvents.VerifyOTP -> {
                viewModelScope.launch {
                    state.value.mobileNumber.value?.let {
                        authService.verifyOtp(
                            it, event.otp
                        ).collect {
                            parseDataState(state.value, it) {
                                state.value = state.value.copy(isOtpVerified = true)
                            }
                        }
                    }
                }
            }

            is RegisterEvents.ResetOtpData -> {
                state.value = state.value.copy(isOtpSent = false, isOtpVerified = false)
            }

            is RegisterEvents.UpdateUIComponent -> {
                println("UIComponent event: ${event.uiComponent}")
                state.value.updateUiComponent(event.uiComponent)
            }

            is RegisterEvents.UpdateWhatsAppUpdatesToggleState -> {
                state.value = state.value.copy(isWhatsAppUpdatesEnabled = event.boolean)
            }
        }
    }
}
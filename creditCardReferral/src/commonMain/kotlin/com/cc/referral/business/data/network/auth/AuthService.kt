package com.cc.referral.business.data.network.auth

import com.cc.referral.business.data.network.auth.models.SendOtpResponseModel
import com.cc.referral.business.data.network.auth.models.VerifyOtpResponseModel
import com.cc.referral.business.domain.DataState
import kotlinx.coroutines.flow.Flow

interface AuthService {

    suspend fun sendOtp(phone: String): Flow<DataState<SendOtpResponseModel>>
    suspend fun verifyOtp(phone: String, otp: String): Flow<DataState<VerifyOtpResponseModel>>
}
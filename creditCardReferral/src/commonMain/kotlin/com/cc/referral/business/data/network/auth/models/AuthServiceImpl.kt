package com.cc.referral.business.data.network.auth.models

import com.cc.referral.business.data.network.auth.AuthService
import com.cc.referral.business.data.network.utils.EndPoints
import com.cc.referral.business.data.network.utils.HttpsRequestMethod
import com.cc.referral.business.data.network.utils.KtorHttpRequestHandler
import com.cc.referral.business.domain.DataState
import kotlinx.coroutines.flow.Flow

class AuthServiceImpl(
    private val ktorHttpRequestHandler: KtorHttpRequestHandler
) : AuthService {

    override suspend fun sendOtp(phone: String): Flow<DataState<SendOtpResponseModel>> {
        return ktorHttpRequestHandler.handleApiRequest(
            url = EndPoints.BASE_URL + EndPoints.SEND_OTP,
            requestType = HttpsRequestMethod.POST,
            requestHeaders = hashMapOf(
                "Content-Type" to "application/x-www-form-urlencoded"
            ),
            formDataParameters = hashMapOf(
                "mobile_number" to "tel:$phone",
            )
        )
    }

    override suspend fun verifyOtp(
        phone: String,
        otp: String
    ): Flow<DataState<VerifyOtpResponseModel>> {
        return ktorHttpRequestHandler.handleApiRequest(
            url = EndPoints.BASE_URL + EndPoints.VERIFY_OTP,
            requestType = HttpsRequestMethod.POST,
            requestHeaders = hashMapOf(
                "Content-Type" to "application/x-www-form-urlencoded"
            ),
            formDataParameters = hashMapOf(
                "mobile_number" to "tel:$phone",
                "otp" to otp,
            )
        )
    }
}
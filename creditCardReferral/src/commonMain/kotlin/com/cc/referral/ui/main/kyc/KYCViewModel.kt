package com.cc.referral.ui.main.kyc

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class KYCViewModel:ViewModel() {

    val state = MutableStateFlow(KYCState())
    
}
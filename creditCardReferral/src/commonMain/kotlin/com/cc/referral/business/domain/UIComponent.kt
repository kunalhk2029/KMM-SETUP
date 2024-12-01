package com.cc.referral.business.domain

sealed class UIComponent {

    data class Dialog(
        val title: String? = null,
        val description: String? = null,
    ) : UIComponent()

    data object None : UIComponent()

    data class Error(
        val message: String,
    ) : UIComponent()

}
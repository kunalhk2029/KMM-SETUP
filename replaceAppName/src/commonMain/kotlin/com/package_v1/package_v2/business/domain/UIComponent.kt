package com.package_v1.package_v2.business.domain

sealed class UIComponent {

    data class Dialog(
        val title: String? = null,
        val description: String? = null,
    ) : UIComponent()

    data object None : UIComponent()

    data class Error(
        val message: String,
    ) : UIComponent()

    data class SnackBar(
        val message: String,
    ) : UIComponent()

}
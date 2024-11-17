package com.saver.igv1.ui.main.common.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier

object ModifierExtensionUtils {

    private val interactionSource = MutableInteractionSource()

    fun Modifier.clickable(
        showClickIndication: Boolean = true,
        onClick: () -> Unit
    ): Modifier {
        if (showClickIndication) {
            return this.clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick.invoke()
            }
        }
        return this.clickable() {
            onClick.invoke()
        }
    }
}
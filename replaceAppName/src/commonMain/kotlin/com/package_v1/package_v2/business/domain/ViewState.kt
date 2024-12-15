package com.package_v1.package_v2.business.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


interface ViewState {
    val uiComponent: MutableStateFlow<UIComponent?>
    val progressBarState: MutableStateFlow<ProgressBarState>
    val activeModalBottomSheetInfo: MutableStateFlow<ModalBottomSheetInfo?>


    fun updateUiComponent(uiComponent: UIComponent)
    fun updateProgressBarState(progressBarState: ProgressBarState)
    fun updateActiveModalBottomSheetInfo(modalBottomSheetInfo: ModalBottomSheetInfo?)
}

open class ViewStateExt(
    override var progressBarState: MutableStateFlow<ProgressBarState> = MutableStateFlow(
        ProgressBarState.Idle
    ),
    override var activeModalBottomSheetInfo: MutableStateFlow<ModalBottomSheetInfo?> = MutableStateFlow(
        null
    ),
    override var uiComponent: MutableStateFlow<UIComponent?> = MutableStateFlow(null)
) : ViewState {

    override fun updateUiComponent(uiComponent: UIComponent) {
        this.uiComponent.value = uiComponent
        if (uiComponent is UIComponent.SnackBar) {
            CoroutineScope(Dispatchers.Main).launch {
                delay(4000L)
                this@ViewStateExt.uiComponent.value = null
            }
        }
    }

    override fun updateProgressBarState(progressBarState: ProgressBarState) {
        this.progressBarState.value = progressBarState
    }

    override fun updateActiveModalBottomSheetInfo(modalBottomSheetInfo: ModalBottomSheetInfo?) {
        this.activeModalBottomSheetInfo.value = modalBottomSheetInfo
    }
}
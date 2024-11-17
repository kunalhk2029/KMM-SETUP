package com.saver.igv1.business.domain

import kotlinx.coroutines.flow.MutableStateFlow


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
    }

    override fun updateProgressBarState(progressBarState: ProgressBarState) {
        this.progressBarState.value = progressBarState
    }

    override fun updateActiveModalBottomSheetInfo(modalBottomSheetInfo: ModalBottomSheetInfo?) {
        this.activeModalBottomSheetInfo.value = modalBottomSheetInfo
    }
}
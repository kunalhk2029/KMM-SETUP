package com.saver.igv1.business.domain


sealed class DataState<T> {

    data class Response<T>(
        val uiComponent: UIComponent
    ) : DataState<T>()

    data class Data<T>(
        val data: T? = null
    ) : DataState<T>()

    data class Loading<T>(
        val progressBarState: ProgressBarState = ProgressBarState.Idle
    ) : DataState<T>()
}

fun <T, S : ViewState> parseDataState(
    viewState: S,
    dataState: DataState<T>,
    handleData: (T?) -> Unit = {}
) {
    when (dataState) {

        is DataState.Response -> {
            viewState.updateUiComponent(dataState.uiComponent)
        }

        is DataState.Data -> {
            handleData(dataState.data)
        }

        is DataState.Loading -> {
            viewState.updateProgressBarState(dataState.progressBarState)
        }
    }
}
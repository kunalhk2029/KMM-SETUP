package com.saver.igv1.ui.main.stories.tray

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saver.igv1.business.domain.parseDataState
import com.saver.igv1.business.interactors.stories.InitStoriesTrayInfo
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class StoriesTrayViewModel(
    private val initStoriesTrayInfo: InitStoriesTrayInfo
) : ViewModel() {

    val state = mutableStateOf(StoriesTrayState())

    init {
        onEvent(StoriesTrayEvents.InitTrayData(false))
    }

    val reelMediaList = mutableListOf<String>()

    fun onEvent(storiesTrayEvents: StoriesTrayEvents) {
        when (storiesTrayEvents) {

            is StoriesTrayEvents.InitTrayData -> {
                viewModelScope.launch {
                    initStoriesTrayInfo(storiesTrayEvents.hitApi).onEach {
                        parseDataState(state.value, it) {
                            state.value = state.value.copy(trayInfo = it)
                        }
                    }.launchIn(viewModelScope)
                }
            }

            else -> {}
        }
    }
}
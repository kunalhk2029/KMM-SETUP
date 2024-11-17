package com.saver.igv1.business.data.db.utils

import com.saver.igv1.business.domain.DataState
import com.saver.igv1.business.domain.ProgressBarState
import com.saver.igv1.business.domain.UIComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DbRequestHandler {

    suspend fun <Result> handleDbRequest(
        dbCall: suspend () -> Result?
    ): Flow<DataState<Result?>> {
        return flow {
            emit(DataState.Loading(ProgressBarState.Loading))

            try {
                val response = dbCall()
                emit(DataState.Data(response))
            } catch (e: Exception) {
                emit(DataState.Response(UIComponent.Error("Error: ${e.message}")))
            } finally {
                emit(DataState.Loading(ProgressBarState.Idle))
            }
        }
    }
}


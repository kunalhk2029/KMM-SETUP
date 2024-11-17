package com.saver.igv1.business.interactors.stories

import com.saver.igv1.business.data.db.stories.StoriesDbService
import com.saver.igv1.business.data.db.utils.DbRequestHandler
import com.saver.igv1.business.data.network.storiesService.StoriesService
import com.saver.igv1.business.domain.DataState
import com.saver.igv1.business.domain.models.stories.StoriesTrayInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

class InitStoriesTrayInfo(
    private val igStoriesService: StoriesService,
    private val storiesDbService: StoriesDbService,
    private val dbRequestHandler: DbRequestHandler
) {
    suspend operator fun invoke(
        hitApiRequest: Boolean
    ): Flow<DataState<List<StoriesTrayInfo>?>> = channelFlow {

        var list: List<StoriesTrayInfo>

        if (hitApiRequest) {
            igStoriesService.getStoriesTrayInfo().collect {

                if (it is DataState.Loading || it is DataState.Response) {
                    send(it)
                }

                if (it is DataState.Data) {
                    it.data?.let { data ->
                        storiesDbService.insertStoriesTrayInfo(data).let {
                            list = storiesDbService.getStoriesTrayInfo()
                            send(DataState.Data(data = list))
                        }
                    }
                }
            }
        } else {
            dbRequestHandler.handleDbRequest {
                storiesDbService.getStoriesTrayInfo()
            }.collect {
                send(it)
            }
        }
    }
}
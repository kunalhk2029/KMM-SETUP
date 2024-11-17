package com.saver.igv1.business.interactors.stories

import com.saver.igv1.business.data.db.stories.StoriesDbService
import com.saver.igv1.business.data.network.storiesService.StoriesService
import com.saver.igv1.business.data.network.storiesService.model.StoryMediaRequestBodyVariables
import com.saver.igv1.business.domain.DataState
import com.saver.igv1.business.domain.models.stories.StoriesTrayInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class FetchStoryMediaData(
    private val igStoriesService: StoriesService,
    private val storiesDbService: StoriesDbService,
    private val json: Json
) {
    suspend operator fun invoke(
        trayPosition: Int?,
        reelIdsAlreadyQueuedList: MutableList<String>
    ) {

        val mapOfIdListWithNoLatestStoryAvailable = hashMapOf<Int?, StoriesTrayInfo?>()

        val trayIdsList = storiesDbService.getStoriesTrayInfo()

        trayIdsList.forEach {

            var isLatestMediaNotExistsInDb = false
            it.user?.username?.let { it1 ->
                it.latestReelTime?.let { it2 ->
                    println(
                        "564645 latest  ${it.user.username} at ${it.latestReelTime}"
                    )

                    isLatestMediaNotExistsInDb = storiesDbService.getStoryMediaItem(
                        userName = it1, mediaTakenAtTimeStamp = it2
                    ) == null
                }
            }

            if (isLatestMediaNotExistsInDb) {
                mapOfIdListWithNoLatestStoryAvailable[it.rankedPosition] = it
            } else {
                println("564645 latest media exists in db for ${it.user?.username} at ${it.latestReelTime}")
            }
        }

        val reelIdsListToBeFetched = mutableListOf<String>()

        trayPosition?.let { trayPosition ->

            val isStoriesOfTrayBehindSelectedTrayNotAvailable =
                mapOfIdListWithNoLatestStoryAvailable[trayPosition - 1] != null

            val isStoriesOfSelectedTrayNotAvailable =
                mapOfIdListWithNoLatestStoryAvailable[trayPosition] != null

            val isStoriesOfTrayAfterSelectedTrayNotAvailable =
                mapOfIdListWithNoLatestStoryAvailable[trayPosition + 1] != null || mapOfIdListWithNoLatestStoryAvailable[trayPosition + 2] != null

            if (isStoriesOfTrayBehindSelectedTrayNotAvailable) {
                mapOfIdListWithNoLatestStoryAvailable[trayPosition - 1]?.id?.let { it1 ->
                    addToReelIdsList(
                        it1,
                        reelIdsAlreadyQueuedList,
                        reelIdsListToBeFetched
                    )
                }
            }

            if (isStoriesOfSelectedTrayNotAvailable) {
                mapOfIdListWithNoLatestStoryAvailable[trayPosition]?.id?.let { it1 ->
                    addToReelIdsList(
                        it1,
                        reelIdsAlreadyQueuedList,
                        reelIdsListToBeFetched
                    )
                }
            }

            if (isStoriesOfTrayAfterSelectedTrayNotAvailable) {

                mapOfIdListWithNoLatestStoryAvailable[trayPosition + 1]?.id?.let { it1 ->
                    addToReelIdsList(
                        it1,
                        reelIdsAlreadyQueuedList,
                        reelIdsListToBeFetched
                    )
                }

                mapOfIdListWithNoLatestStoryAvailable[trayPosition + 2]?.id?.let { it1 ->
                    addToReelIdsList(
                        it1,
                        reelIdsAlreadyQueuedList,
                        reelIdsListToBeFetched
                    )
                }
            }
            println("564645 reelIdsListToBeFetched: $reelIdsListToBeFetched")

        }


        CoroutineScope(Dispatchers.Default).launch {

            if (reelIdsListToBeFetched.isNotEmpty()) {
                val storyMediaRequestBodyVariables = StoryMediaRequestBodyVariables(
                    initial_reel_id = reelIdsListToBeFetched.firstOrNull(),
                    reel_ids = reelIdsListToBeFetched,
                    first = reelIdsListToBeFetched.size
                )
                val variables = json.encodeToString(
                    StoryMediaRequestBodyVariables.serializer(),
                    storyMediaRequestBodyVariables
                )

                println("564645 getStoriesMediaInfo collected: $variables")

                igStoriesService.getStoriesMediaInfo(
                    variables,trayIdsList
                ).collect {
                    if (it is DataState.Data) {
                        it.data?.let { storyMediaList ->
                            storiesDbService.insertStoryMediaItems(storyMediaList).let { _ ->
                                updateReelIdsAlreadyQueuedList(
                                    reelIdsAlreadyQueuedList,
                                    reelIdsListToBeFetched
                                )
                            }
                        }
                    }
                }
            }
        }
    }
//    suspend operator fun invoke(
//        trayPosition: Int?,
//        reelIdsAlreadyQueuedList: MutableList<String>,
//        userName: String?
//    ): Flow<DataState<List<StoryMediaInfo>?>> = channelFlow {
//
//        val mapOfIdListWithNoLatestStoryAvailable = hashMapOf<Int?, StoriesTrayInfo?>()
//
//        val trayIdsList = storiesDbService.getStoriesTrayInfo()
//
//        trayIdsList.forEach {
//
//            var isLatestMediaNotExistsInDb = false
//            it.user?.username?.let { it1 ->
//                it.latestReelTime?.let { it2 ->
//                    println(
//                        "564645 latest  ${it.user.username} at ${it.latestReelTime}"
//                    )
//
//                    isLatestMediaNotExistsInDb = storiesDbService.getStoryMediaItem(
//                        userName = it1, mediaTakenAtTimeStamp = it2
//                    ) == null
//                }
//            }
//
//            if (isLatestMediaNotExistsInDb) {
//                mapOfIdListWithNoLatestStoryAvailable[it.rankedPosition] = it
//            } else {
//                println("564645 latest media exists in db for ${it.user?.username} at ${it.latestReelTime}")
//            }
//        }
//
//        val reelIdsListToBeFetched = mutableListOf<String>()
//
//        trayPosition?.let { trayPosition ->
//
//            val isStoriesOfTrayBehindSelectedTrayNotAvailable =
//                mapOfIdListWithNoLatestStoryAvailable[trayPosition - 1] != null
//
//            val isStoriesOfSelectedTrayNotAvailable =
//                mapOfIdListWithNoLatestStoryAvailable[trayPosition] != null
//
//            val isStoriesOfTrayAfterSelectedTrayNotAvailable =
//                mapOfIdListWithNoLatestStoryAvailable[trayPosition + 1] != null || mapOfIdListWithNoLatestStoryAvailable[trayPosition + 2] != null
//
//            if (isStoriesOfTrayBehindSelectedTrayNotAvailable) {
//                mapOfIdListWithNoLatestStoryAvailable[trayPosition - 1]?.id?.let { it1 ->
//                    addToReelIdsList(
//                        it1,
//                        reelIdsAlreadyQueuedList,
//                        reelIdsListToBeFetched
//                    )
//                }
//            }
//
//            if (isStoriesOfSelectedTrayNotAvailable) {
//                mapOfIdListWithNoLatestStoryAvailable[trayPosition]?.id?.let { it1 ->
//                    addToReelIdsList(
//                        it1,
//                        reelIdsAlreadyQueuedList,
//                        reelIdsListToBeFetched
//                    )
//                }
//            }
//
//            if (isStoriesOfTrayAfterSelectedTrayNotAvailable) {
//
//                mapOfIdListWithNoLatestStoryAvailable[trayPosition + 1]?.id?.let { it1 ->
//                    addToReelIdsList(
//                        it1,
//                        reelIdsAlreadyQueuedList,
//                        reelIdsListToBeFetched
//                    )
//                }
//
//                mapOfIdListWithNoLatestStoryAvailable[trayPosition + 2]?.id?.let { it1 ->
//                    addToReelIdsList(
//                        it1,
//                        reelIdsAlreadyQueuedList,
//                        reelIdsListToBeFetched
//                    )
//                }
//            }
//            println("564645 reelIdsListToBeFetched: $reelIdsListToBeFetched")
//
//        }
//
//        var g = true
//
//        CoroutineScope(Dispatchers.Default).launch {
//
//            if (reelIdsListToBeFetched.isNotEmpty()) {
//                val storyMediaRequestBodyVariables = StoryMediaRequestBodyVariables(
//                    initial_reel_id = reelIdsListToBeFetched.firstOrNull(),
//                    reel_ids = reelIdsListToBeFetched,
//                    first = reelIdsListToBeFetched.size
//                )
//                val variables = json.encodeToString(
//                    StoryMediaRequestBodyVariables.serializer(),
//                    storyMediaRequestBodyVariables
//                )
//
//                println("564645 getStoriesMediaInfo collected: $variables")
//
//
//                var storyMediaList = listOf<StoryMediaInfo>()
//                igStoriesService.getStoriesMediaInfo(
//                    variables
//                ).collect {
//
//
//                    if (it is DataState.Loading && it.progressBarState == ProgressBarState.Idle) {
//                        if (storyMediaList.isNotEmpty()) {
//                            storiesDbService.insertStoryMediaItems(storyMediaList).let { _ ->
//                                updateReelIdsAlreadyQueuedList(
//                                    reelIdsAlreadyQueuedList,
//                                    reelIdsListToBeFetched
//                                )
//                                val dbList = userName?.let { it1 ->
//                                    storiesDbService.getStoryMediaItems(
//                                        it1
//                                    )
//                                } ?: run {
//                                    storiesDbService.getStoryMediaItems()
//                                }
//                                send(DataState.Data(data = dbList))
//                                send(it)
//                                g = false
//                            }
//                        } else {
//                            updateReelIdsAlreadyQueuedList(
//                                reelIdsAlreadyQueuedList,
//                                reelIdsListToBeFetched
//                            )
//                            val dbList = userName?.let { it1 ->
//                                storiesDbService.getStoryMediaItems(
//                                    it1
//                                )
//                            } ?: run {
//                                storiesDbService.getStoryMediaItems()
//                            }
//                            send(DataState.Data(data = dbList))
//                            send(it)
//                            g = false
//                        }
//                    } else if (it is DataState.Data) {
//                        it.data?.let { data ->
//                            storyMediaList = data
//                        }
//                    } else {
//                        send(it)
//                    }
//                }
//            } else {
//                send(DataState.Loading(progressBarState = ProgressBarState.Loading))
//                if (reelIdsAlreadyQueuedList.contains(mapOfIdListWithNoLatestStoryAvailable[trayPosition]?.id)) {
//                    // Response is not returned yet for network call made for this tray id
//                    while (reelIdsAlreadyQueuedList.isNotEmpty()) {
//                        delay(1000)
//                    }
//                    val dbList = userName?.let { storiesDbService.getStoryMediaItems(it) }
//                        ?: run {
//                            storiesDbService.getStoryMediaItems()
//                        }
//                    send(DataState.Data(data = dbList))
//                    send(DataState.Loading(progressBarState = ProgressBarState.Idle))
//                    g = false
//                } else {
//                    val dbList = userName?.let { storiesDbService.getStoryMediaItems(it) }
//                        ?: run {
//                            storiesDbService.getStoryMediaItems()
//                        }
//                    send(DataState.Loading(progressBarState = ProgressBarState.Idle))
//                    send(DataState.Data(data = dbList))
//                    g = false
//                }
//            }
//
//        }
//
//        while (g) {
//            delay(1000)
//        }
//    }

    private fun updateReelIdsAlreadyQueuedList(
        reelIdsAlreadyQueuedList: MutableList<String>,
        reelIdsToBeFetchedList: MutableList<String>,
    ) {
        reelIdsToBeFetchedList.forEach {
            reelIdsAlreadyQueuedList.remove(it)
        }
        println("564645 getStoriesMediaInfo collected: $reelIdsToBeFetchedList")
        println("564645 updated reelIdsAlreadyQueuedList : $reelIdsAlreadyQueuedList")
    }

    private fun addToReelIdsList(
        id: String,
        reelIdsAlreadyQueuedList: MutableList<String>,
        reelIdsToBeFetchedList: MutableList<String>,
    ): MutableList<String> {

        if (!reelIdsAlreadyQueuedList.contains(id)) {
            reelIdsAlreadyQueuedList.add(id)
            reelIdsToBeFetchedList.add(id)
        }
        return reelIdsToBeFetchedList
    }
}
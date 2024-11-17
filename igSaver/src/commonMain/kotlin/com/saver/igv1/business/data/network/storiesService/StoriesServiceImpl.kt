package com.saver.igv1.business.data.network.storiesService

import com.saver.igv1.business.data.auth.InstagramAuthSessionManager
import com.saver.igv1.business.data.network.storiesService.model.StoriesTrayDTO
import com.saver.igv1.business.data.network.storiesService.model.StoryMediaInfoDTO
import com.saver.igv1.business.data.network.utils.HttpsRequestMethod
import com.saver.igv1.business.data.network.utils.IGEndPoints
import com.saver.igv1.business.data.network.utils.KtorIGRequestHandler
import com.saver.igv1.business.domain.DataState
import com.saver.igv1.business.domain.models.stories.StoriesTrayInfo
import com.saver.igv1.business.domain.models.stories.StoryMediaInfo
import kotlinx.coroutines.flow.Flow

class StoriesServiceImpl(
    private val ktorIGRequestHandler: KtorIGRequestHandler,
    private val storiesMapper: StoryMediaInfoMapper,
    private val instagramAuthSessionManager: InstagramAuthSessionManager
) : StoriesService {

    override suspend fun getStoriesTrayInfo(): Flow<DataState<List<StoriesTrayInfo>?>> =
        ktorIGRequestHandler.handleApiRequest<StoriesTrayDTO, List<StoriesTrayInfo>?>(
            IGEndPoints.STORIES_TRAY_DATA,
        ) {
            it.tray.map { it.mapToStoriesTrayInfo() }
        }

    override suspend fun getStoriesMediaInfo(
        variables: String,
        user: List<StoriesTrayInfo>
    ): Flow<DataState<List<StoryMediaInfo>?>> {
        return ktorIGRequestHandler.handleApiRequest<StoryMediaInfoDTO, List<StoryMediaInfo>?>(
            IGEndPoints.IG_GRAPHQL_QUERY,
            requestType = HttpsRequestMethod.POST,
            requestBodyFormUrlParameterMap = instagramAuthSessionManager.getRequestBodyFormUrlParameterMap(
                variables = variables
            )
        ) { dto ->
            storiesMapper.user = user
            storiesMapper.mapToDomain(dto)
        }
    }

    override suspend fun getStoriesFileSize(
        url: String
    ): Double? {
        return ktorIGRequestHandler.getFileSize(url)
    }
}
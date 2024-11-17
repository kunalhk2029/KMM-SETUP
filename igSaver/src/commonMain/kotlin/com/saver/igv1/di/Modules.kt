package com.saver.igv1.di

import com.saver.igv1.business.data.auth.InstagramAuthSessionManager
import com.saver.igv1.business.data.db.PrimaryDatabase
import com.saver.igv1.business.data.db.stories.StoriesDao
import com.saver.igv1.business.data.db.stories.StoriesDbService
import com.saver.igv1.business.data.db.stories.StoriesDbServiceImpl
import com.saver.igv1.business.data.db.stories.mappers.StoriesMediaInfoEntityMapper
import com.saver.igv1.business.data.db.stories.mappers.StoriesTrayEntityMapper
import com.saver.igv1.business.data.db.utils.DbRequestHandler
import com.saver.igv1.business.data.network.downloadService.MediaDownloadService
import com.saver.igv1.business.data.network.downloadService.MediaDownloadServiceImpl
import com.saver.igv1.business.data.network.storiesService.StoriesService
import com.saver.igv1.business.data.network.storiesService.StoriesServiceImpl
import com.saver.igv1.business.data.network.storiesService.StoryMediaInfoMapper
import com.saver.igv1.business.data.network.utils.KtorIGRequestHandler
import com.saver.igv1.business.data.prefs.SharedPrefRepository
import com.saver.igv1.business.data.prefs.SharedPrefRepositoryImpl
import com.saver.igv1.business.domain.MediaPlayerProgressManager
import com.saver.igv1.business.domain.MultipleMediaPlayerManager
import com.saver.igv1.business.domain.SingleMediaPlayerManager
import com.saver.igv1.business.interactors.stories.GetStoriesMediaData
import com.saver.igv1.business.interactors.stories.FetchStoryMediaData
import com.saver.igv1.business.interactors.stories.InitStoriesTrayInfo
import com.saver.igv1.ui.main.stories.preview.multipletray.MultipleTrayPreviewViewModel
import com.saver.igv1.ui.main.stories.preview.singletray.SingleTrayPreviewViewModel
import com.saver.igv1.ui.main.stories.tray.StoriesTrayViewModel
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {

    singleOf(::SharedPrefRepositoryImpl).bind<SharedPrefRepository>()
    singleOf(::StoriesServiceImpl).bind<StoriesService>()
    singleOf(::MediaDownloadServiceImpl).bind<MediaDownloadService>()
    singleOf(::StoriesDbServiceImpl).bind<StoriesDbService>()

    singleOf(::InstagramAuthSessionManager)
    singleOf(::InitStoriesTrayInfo)
    singleOf(::FetchStoryMediaData)
    singleOf(::GetStoriesMediaData)

    singleOf(::KtorIGRequestHandler)
    singleOf(::DbRequestHandler)
    singleOf(::StoryMediaInfoMapper)

    viewModelOf(::StoriesTrayViewModel)
    viewModelOf(::SingleTrayPreviewViewModel)
    viewModelOf(::MultipleTrayPreviewViewModel)

    singleOf(::StoriesTrayEntityMapper)
    singleOf(::StoriesMediaInfoEntityMapper)

    singleOf(::MediaPlayerProgressManager)
    factoryOf(::MultipleMediaPlayerManager)
    factoryOf(::SingleMediaPlayerManager)

    single<Json> {
        Json {
            ignoreUnknownKeys = true
        }
    }

    single<StoriesDao> {
        get<PrimaryDatabase>().storiesDao()
    }
}


expect val platformModule: Module
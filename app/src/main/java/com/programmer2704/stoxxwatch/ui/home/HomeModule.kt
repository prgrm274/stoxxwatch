package com.programmer2704.stoxxwatch.ui.home

import com.programmer2704.stoxxwatch.data.repo.WatchListRepository
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @Provides
    fun presenter(repository: WatchListRepository) : HomeContract.Presenter {
        return HomePresenter(repository)
    }
}
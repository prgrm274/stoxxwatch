package com.programmer2704.stoxxwatch.ui.viewstock

import com.programmer2704.stoxxwatch.data.DatabaseManager
import com.programmer2704.stoxxwatch.data.repo.WatchListRepository
import dagger.Module
import dagger.Provides

@Module
class ViewStockModule{

    @Provides
    fun presenter(repository: WatchListRepository) : ViewStockContract.Presenter{
        return ViewStockPresenter(repository)
    }

    @Provides
    fun watchListRepository(database: DatabaseManager): WatchListRepository {
        return WatchListRepository(database)
    }
}
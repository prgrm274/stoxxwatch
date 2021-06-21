package com.programmer2704.stoxxwatch.app

import com.programmer2704.stoxxwatch.data.DatabaseManager
import com.programmer2704.stoxxwatch.ui.home.HomeComponent
import com.programmer2704.stoxxwatch.ui.search.SearchComponent
import com.programmer2704.stoxxwatch.ui.viewstock.ViewStockComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, SubcomponentsModule::class])
interface AppComponent {
    fun homeComponent() : HomeComponent.Factory
    fun searchComponent() : SearchComponent.Factory
    fun viewStockComponent(): ViewStockComponent.Factory
    fun database(): DatabaseManager
}
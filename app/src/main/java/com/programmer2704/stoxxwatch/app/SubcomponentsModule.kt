package com.programmer2704.stoxxwatch.app

import com.programmer2704.stoxxwatch.ui.home.HomeComponent
import com.programmer2704.stoxxwatch.ui.search.SearchComponent
import com.programmer2704.stoxxwatch.ui.viewstock.ViewStockComponent
import dagger.Module

@Module(subcomponents = [
    HomeComponent::class,
    SearchComponent::class,
    ViewStockComponent::class
])
class SubcomponentsModule
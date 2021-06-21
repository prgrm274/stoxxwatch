package com.programmer2704.stoxxwatch.ui.search

import com.programmer2704.stoxxwatch.annotations.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules=[SearchModule::class])
interface SearchComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SearchComponent
    }

    fun inject(activity: Search)
}
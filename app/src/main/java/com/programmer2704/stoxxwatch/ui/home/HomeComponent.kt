package com.programmer2704.stoxxwatch.ui.home

import com.programmer2704.stoxxwatch.annotations.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [HomeModule::class])
interface HomeComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): HomeComponent
    }

//    fun inject(activity: Home)
    fun inject(fragment: HomeFragment)
}
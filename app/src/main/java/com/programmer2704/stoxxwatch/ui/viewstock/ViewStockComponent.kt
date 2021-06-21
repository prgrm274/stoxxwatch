package com.programmer2704.stoxxwatch.ui.viewstock

import com.programmer2704.stoxxwatch.annotations.ActivityScope
import dagger.Subcomponent


@ActivityScope
@Subcomponent(modules = [ViewStockModule::class])
interface ViewStockComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ViewStockComponent
    }

    fun inject(activity: ViewStock)
}
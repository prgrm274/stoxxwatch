package com.programmer2704.stoxxwatch.ui.search

import com.programmer2704.stoxxwatch.data.repo.CompanyRepository
import dagger.Module
import dagger.Provides

@Module
class SearchModule {

    @Provides
    fun presenter(repository: CompanyRepository) : SearchContract.Presenter{
        return SearchPresenter(repository)
    }
}
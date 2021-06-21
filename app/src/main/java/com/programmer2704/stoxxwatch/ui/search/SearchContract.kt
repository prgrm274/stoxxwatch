package com.programmer2704.stoxxwatch.ui.search

import com.programmer2704.stoxxwatch.data.models.Company
import com.programmer2704.stoxxwatch.mvp.BasePresenter
import com.programmer2704.stoxxwatch.mvp.BaseView

interface SearchContract {

    interface Presenter: BasePresenter<View> {
        fun getCompanies() : List<Company>
        fun search(filter: String)
    }

    interface View: BaseView {
        fun onSearchResult(list: List<Company>)
    }
}
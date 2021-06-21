package com.programmer2704.stoxxwatch.ui.search

import com.programmer2704.stoxxwatch.annotations.ActivityScope
import com.programmer2704.stoxxwatch.data.models.Company
import com.programmer2704.stoxxwatch.data.repo.CompanyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityScope
class SearchPresenter @Inject constructor(
    private var repository: CompanyRepository
): SearchContract.Presenter {

    var view: SearchContract.View? = null
    private val companies = ArrayList<Company>()
    private var searchTerm = ""
    override fun getCompanies() = companies

    override fun search(filter: String) {
        searchTerm = filter
        if (searchTerm == "") {
            view?.onSearchResult(listOf())
            return
        }

        GlobalScope.launch(Dispatchers.IO) {
            val list = repository.search(filter)
            list.forEach{ println(it)}
            launch(Dispatchers.Main){
                view?.onSearchResult(list)
            }
        }

    }



    override fun attach(view: SearchContract.View) {
        this.view = view
    }

    override fun drop() {
        this.view = null
    }
}
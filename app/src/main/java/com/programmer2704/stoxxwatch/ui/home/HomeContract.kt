package com.programmer2704.stoxxwatch.ui.home

import com.programmer2704.stoxxwatch.data.models.WatchListItem
import com.programmer2704.stoxxwatch.mvp.BasePresenter
import com.programmer2704.stoxxwatch.mvp.BaseView

interface HomeContract {

    interface Presenter : BasePresenter<View>{

        fun getWatchListItems()
        fun deleteItem(item: WatchListItem, adapterPosition: Int)
    }

    interface View: BaseView{
        fun onWatchListItems(items: List<WatchListItem>)
        fun onItemDeleted(adapterPosition: Int)
    }
}
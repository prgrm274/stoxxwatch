package com.programmer2704.stoxxwatch.ui.viewstock

import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse
import com.programmer2704.stoxxwatch.data.models.Company
import com.programmer2704.stoxxwatch.mvp.BasePresenter
import com.programmer2704.stoxxwatch.mvp.BaseView

interface ViewStockContract {

    interface Presenter: BasePresenter<View> {
        fun fetchIntraday(symbol: String?)
        fun fetchMonthly(symbol: String?)
        fun fetchWeekly(symbol: String?)
        fun fetchDaily(symbol: String?)
        fun fetchQuote(symbol: String?)
        fun addToWatchList(company: Company)
        fun updateIfInWatchList(company: Company)
    }

    interface View: BaseView {
        fun onQuoteResult(response: QuoteResponse)
        fun onTimeSeriesResult(response: TimeSeriesResponse)
        fun onItemAddedToWatchList(status: Boolean)
        fun onWatchListItemsExceeded()
        fun onItemInWatchList()
    }
}
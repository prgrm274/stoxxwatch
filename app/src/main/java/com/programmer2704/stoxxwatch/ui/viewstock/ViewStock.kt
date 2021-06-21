package com.programmer2704.stoxxwatch.ui.viewstock

import android.graphics.Color
import android.os.Bundle
import android.view.View.GONE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse
import com.programmer2704.stoxxwatch.R
import com.programmer2704.stoxxwatch.app.App
import com.programmer2704.stoxxwatch.data.models.Company
import com.programmer2704.stoxxwatch.util.Constants.EXTRA_STOCK_NAME
import com.programmer2704.stoxxwatch.util.Constants.EXTRA_STOCK_SYMBOL
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.activity_view_stock.*
import java.text.DecimalFormat
import javax.inject.Inject


class ViewStock : AppCompatActivity(), ViewStockContract.View {

    @Inject
    lateinit var presenter: ViewStockContract.Presenter
    lateinit var component: ViewStockComponent
    var name: String? = ""
    var symbol: String? = ""
    private val fmt = DecimalFormat("#,##0.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_stock)
        component = (applicationContext as App).component.viewStockComponent().create()
        component.inject(this)
        name = intent.getStringExtra(EXTRA_STOCK_NAME)
        symbol = intent.getStringExtra(EXTRA_STOCK_SYMBOL)
        initUi()
    }

    private fun initUi(){
        presenter.fetchIntraday(symbol)
        presenter.fetchQuote(symbol)

        companyNameTv.text = name
        companySymbolTv.text = symbol
        lineChartGraph.setNoDataText("")
        lineChartGraph.setPinchZoom(true)
        lineChartGraph.axisRight.isEnabled = false
        lineChartGraph.axisLeft.isEnabled = true
        lineChartGraph.axisLeft.axisLineColor = Color.parseColor("#FFFFFFFF")
        lineChartGraph.axisLeft.setDrawAxisLine(false)
        lineChartGraph.axisLeft.setDrawTopYLabelEntry(false)
        lineChartGraph.axisLeft.setDrawLabels(false)
        lineChartGraph.xAxis.setDrawLabels(false)
        lineChartGraph.xAxis.isEnabled = false
        lineChartGraph.legend.isEnabled = false
        lineChartGraph.description.isEnabled = false
        lineChartGraph.fitScreen()

        addBtn.setOnClickListener {
            presenter.addToWatchList(Company(name = name, symbol = symbol))
        }
        
        backBtn.setOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()
        presenter.attach(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.drop()
    }

    override fun onQuoteResult(response: QuoteResponse) {
        if(response.errorMessage == null){
            previousCloseTv.text = fmt.format(response.previousClose)
            openTv.text = fmt.format(response.open)
            highTv.text = fmt.format(response.high)
            lowTv.text = fmt.format(response.low)
            volumeTv.text = fmt.format(response.volume)
            bannerHighTv.text = fmt.format(response.high)
            val sign : String = if (response.changePercent > 0) "+" else ""
            percentChangeTv.text =  sign + fmt.format(response.changePercent) + "%"

            val resource: Int = if (response.changePercent > 0)
                R.drawable.background_positive_change else R.drawable.background_negative_change

            percentChangeTv.setBackgroundResource(resource)

            presenter.updateIfInWatchList(Company(name = name, symbol = symbol))

        }else{
            Toast.makeText(this, response.errorMessage, Toast.LENGTH_LONG).show()
        }



    }

    override fun onTimeSeriesResult(response: TimeSeriesResponse) {
        progressBar.visibility = GONE
        if(response.errorMessage == null){
            val entries = mutableListOf<Entry>()
            for(i in (response.stockUnits.size - 1) downTo 0 ){
                entries.add(Entry((response.stockUnits.size - i).toFloat(), response.stockUnits[i].high.toFloat()))
            }
            val dataSet = LineDataSet(entries, "")
            dataSet.lineWidth = 1f
            dataSet.setDrawCircles(false)
            dataSet.setDrawValues(false)
            dataSet.resetColors()
            dataSet.color = Color.parseColor("#FFFFFFFF")
            dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
            val data = LineData(dataSet)
            lineChartGraph.data = data
            lineChartGraph.invalidate()
        }else{
            Toast.makeText(this, response.errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    override fun onItemAddedToWatchList(status: Boolean) {
        val state = if (status) " " else " not "
        Toast.makeText(this, "Item" + state + "added to watchlist", Toast.LENGTH_LONG).show()
    }

    override fun onWatchListItemsExceeded() {
        Toast.makeText(this, "You can only watch up to 5 companies", Toast.LENGTH_LONG).show()
    }

    override fun onItemInWatchList() {
        Toast.makeText(this, "You are watching this item", Toast.LENGTH_LONG).show()
    }
}

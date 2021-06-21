package com.programmer2704.stoxxwatch.data

import android.content.Context
import android.util.Log
import com.programmer2704.stoxxwatch.BuildConfig
import com.programmer2704.stoxxwatch.data.models.Company
import com.programmer2704.stoxxwatch.data.models.MyObjectBox
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import io.objectbox.kotlin.boxFor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class AppDatabaseManager @Inject constructor(var context : Context): DatabaseManager {

     var boxStore: BoxStore
         private set

    init {

        boxStore = com.programmer2704.stoxxwatch.data.models.MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()

        if (com.programmer2704.stoxxwatch.BuildConfig.DEBUG) {
            val started =
                AndroidObjectBrowser(boxStore).start(context.applicationContext)
            Log.i("ObjectBrowser", "Started: $started")
        }

    }

    override fun setupCompanyList() {
        GlobalScope.launch (Dispatchers.IO) {
            NasdaqDownloader.download(object : NasdaqDownloader.DownloadCallback {
                override fun onSuccess(result: String) {
                    insertCompaniesIntoDatabase(result)
                }
                override fun onFailure(error: String?) {
                    Log.e("DatabaseManager error", error!!)
                }
            })
        }
    }

    private fun insertCompaniesIntoDatabase(result: String){
        val companyBox: Box<Company> = boxStore.boxFor()
        companyBox.removeAll()
        val lines = result.split("\n")
        for(n in 1 until lines.size){
            val components = lines[n].split("|")
            try {
                companyBox.put(
                    Company(
                        symbol = components[0],
                        name = components[1].split("-")[0]
                    )
                )
            }catch (e: IndexOutOfBoundsException){
                e.printStackTrace()
            }

        }
    }

    override fun boxStore(): BoxStore {
        return boxStore
    }


}
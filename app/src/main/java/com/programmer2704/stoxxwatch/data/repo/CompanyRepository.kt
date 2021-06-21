package com.programmer2704.stoxxwatch.data.repo

import com.programmer2704.stoxxwatch.data.DatabaseManager
import com.programmer2704.stoxxwatch.data.models.Company
import com.programmer2704.stoxxwatch.data.models.Company_
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import javax.inject.Inject

class CompanyRepository @Inject constructor(database: DatabaseManager){

    private val box: Box<Company> = database.boxStore().boxFor()

    fun search(query: String): List<Company> {
        return box.query()
            .startsWith(com.programmer2704.stoxxwatch.data.models.Company_.name, query)
            .or()
            .startsWith(com.programmer2704.stoxxwatch.data.models.Company_.symbol, query)
            .build()
            .find()
            .take(10)
    }

}
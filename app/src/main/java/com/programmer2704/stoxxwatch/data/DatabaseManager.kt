package com.programmer2704.stoxxwatch.data

import io.objectbox.BoxStore

interface DatabaseManager {

    fun setupCompanyList()
    fun boxStore(): BoxStore
}
package com.programmer2704.stoxxwatch.mvp

interface BasePresenter<T> {
    fun attach(view: T)
    fun drop()
}
package com.programmer2704.stoxxwatch.util

import android.view.View

interface ClickListener {
    fun onClick(view: View?, position: Int)
    fun onLongClick(view: View?, position: Int)
}
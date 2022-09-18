package com.ezatpanah.restcountriesaapi_mvvm.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import coil.load
import com.ezatpanah.restcountriesaapi_mvvm.R
import com.ezatpanah.restcountriesaapi_mvvm.utils.prettyCount

@BindingAdapter("app:loadFlag")
fun ImageView.loadImage(url: String?) {
    load(url) {
        placeholder(R.drawable.placeholder)
    }
}

@BindingConversion
fun longToStr(value: Long?): String? {
    return value?.prettyCount()
}

@BindingAdapter("app:visible")
fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}




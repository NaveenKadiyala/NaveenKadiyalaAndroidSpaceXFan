package com.moschip.spacexfan.helper

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("app:loadImage")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(AppController.getInstance().applicationContext).load(url)
        .into(view)
}

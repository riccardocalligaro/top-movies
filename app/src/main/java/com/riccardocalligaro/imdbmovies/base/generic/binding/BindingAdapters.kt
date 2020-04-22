package com.riccardocalligaro.imdbmovies.base.generic.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    if (url == null) {
        imageView.setImageDrawable(null)
    } else {
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    }
}

@BindingAdapter(value = ["imageFullUrl", "imageThumbnail"], requireAll = true)
fun setImageUrlProgressiveLoading(imageView: ImageView, url: String?, thumbnailUrl: String?) {
    if (url == null) {
        imageView.setImageDrawable(null)
    } else {


        Glide.with(imageView.context)
            .load(url)
            .thumbnail(
                Glide.with(imageView.context).load(
                    thumbnailUrl
                ).transition(DrawableTransitionOptions.withCrossFade())
            )
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }
}
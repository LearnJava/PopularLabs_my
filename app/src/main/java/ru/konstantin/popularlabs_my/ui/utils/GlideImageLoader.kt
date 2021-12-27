package ru.konstantin.popularlabs_my.ui.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class GlideImageLoader: ImageLoader<ImageView> {

    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container.context)
            .asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .load(url)
            .circleCrop()
            .into(container)
    }
}
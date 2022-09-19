package com.onoffrice.zeldasapi.utils

import android.widget.ImageView
import com.onoffrice.zeldasapi.R
import com.squareup.picasso.Picasso

class PicassoUtils {
    fun loadImage(urlImage: String, view:ImageView) {
        Picasso.get()
            .load(urlImage)
            .placeholder(R.drawable.ic_zelda_generic_image)
            .error(R.drawable.ic_zelda_generic_image)
            .into(view)
    }
}
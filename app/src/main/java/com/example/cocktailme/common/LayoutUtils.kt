package com.example.cocktailme.common

import android.view.View

class LayoutUtils {
    companion object {
        fun crossFade(viewsToShow: List<View>, viewsToHide: List<View>, duration: Int = 500) {
            viewsToHide.forEach {
                it.gone()
            }
            viewsToShow.forEach {
                it.fadeIn()
            }
        }
    }
}

fun View.fadeIn(duration: Int = 500) {
    this.apply {
        alpha = 0f
        visible()

        animate()
            .alpha(1f)
            .setDuration(duration.toLong())
            .setListener(null)
    }
}
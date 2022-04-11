package com.example.cocktailme.presentation

import android.view.View
import androidx.core.view.isVisible

class LayoutUtils {
    companion object {
        fun crossFade(viewToShow: View, viewToHide: View, duration: Int = 500) {
            if (!viewToShow.isVisible) {
                viewToHide.visibility = View.GONE
                viewToShow.fadeIn()
            }
        }
    }
}

fun View.fadeIn(duration: Int = 500) {
    this.apply {
        alpha = 0f
        visibility = View.VISIBLE

        animate()
            .alpha(1f)
            .setDuration(duration.toLong())
            .setListener(null)
    }
}
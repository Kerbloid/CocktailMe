package com.example.cocktailme.presentation.ui.common

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE

fun View.visible(visible: Boolean) {
    visibility = if (visible) VISIBLE else GONE
}

fun View.visible() {
    visibility = VISIBLE
}

fun View.gone(gone: Boolean) {
    visibility = if (gone) GONE else VISIBLE
}

fun View.gone()  {
    visibility = GONE
}
package com.example.cocktailme.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(
    private val marginLeft: Int,
    private val marginTop: Int,
    private val marginRight: Int,
    private val marginBottom: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            left = marginLeft
            top = marginTop
            right = marginRight
            bottom = marginBottom
        }
    }
}
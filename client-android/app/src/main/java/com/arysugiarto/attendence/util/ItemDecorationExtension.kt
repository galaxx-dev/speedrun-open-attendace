package com.arysugiarto.attendence.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

private fun gridItemDecoration(
    spanCount: Int,
    spacing: MarginItem,
    includeEdge: Boolean
) = object : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column: Int = position % spanCount

        if (includeEdge) {
            outRect.left =
                spacing.left - column * spacing.left / spanCount // spacing - column * ((1f / spanCount) * spacing)
            outRect.right =
                (column + 1) * spacing.right / spanCount // (column + 1) * ((1f / spanCount) * spacing)
            if (position < spanCount) { // top edge
                outRect.top = spacing.first
            }
            outRect.bottom = spacing.last // item bottom
        } else {
            outRect.left = column * spacing.left / spanCount // column * ((1f / spanCount) * spacing)
            outRect.right =
                spacing.right - (column + 1) * spacing.right / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing.top // item top
            }
        }
    }
}

fun RecyclerView.attachGridItemDecoration(
    spanCount: Int,
    includeEdge: Boolean,
    margin: MarginItem
) { addItemDecoration(gridItemDecoration(spanCount, margin, includeEdge)) }
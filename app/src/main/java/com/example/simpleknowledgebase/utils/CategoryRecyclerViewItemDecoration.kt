package com.example.simpleknowledgebase.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class CategoryRecyclerViewItemDecoration(private val spaceSize: Int): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = spaceSize
            }
            outRect.bottom = spaceSize


        // alternative by usage of 'with()'; but not shorter in this case
        /*
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = spaceSize
            }
            bottom = spaceSize
        }
        */

    }

}
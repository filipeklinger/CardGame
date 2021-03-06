package br.com.si.ufrrj.carta

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CardItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            left =  spaceHeight
            right = spaceHeight
            bottom = spaceHeight
        }
    }
}
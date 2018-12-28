package com.example.mf.experiments

import android.content.Context
import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager

class CustomTransformer(context: Context) : ViewPager.PageTransformer {

    private val maxTranslateOffsetX: Int
    private var viewPager: ViewPager? = null

    init {
        this.maxTranslateOffsetX = dp2px(context, 180f)
    }

    override fun transformPage(view: View, position: Float) {
        if (viewPager == null) {
            viewPager = view.parent as ViewPager
        }

        val leftInScreen = view.left - viewPager!!.scrollX
        val centerXInViewPager = leftInScreen + view.measuredWidth / 2
        val offsetX = centerXInViewPager - viewPager!!.measuredWidth / 2
        val offsetRate = (offsetX.toFloat() * 1f) / viewPager!!.measuredWidth
        val scaleFactor = 1 - Math.abs(offsetRate)
        if (scaleFactor > 0) {
            view.scaleX = scaleFactor
            view.scaleY = scaleFactor
            view.translationX = -maxTranslateOffsetX * offsetRate
        }
    }

}

class PagerTransformerExtreme(context: Context) : ViewPager.PageTransformer{
    private var  viewPager : ViewPager? = null
    private val maxOffset : Int
    init {
        maxOffset = dp2px(context,180f)
    }
    override fun transformPage(view: View, position: Float) {
        if (viewPager == null) {
            viewPager = view.parent as ViewPager
        }
        Log.d(
            "EXTREME_TRANSFORMER",
            "viewLeft ${view.left} viewPager.ScrollX ${viewPager!!.scrollX} viewMeasureWidth ${view.measuredWidth} viewPagerMeasureWidth ${viewPager!!.measuredWidth}"
        )
        val leftInScreen = view.left - viewPager!!.scrollX
        val centerXInViewPager = leftInScreen + view.measuredWidth / 2
        val offsetX = centerXInViewPager - viewPager!!.measuredWidth / 2
        val offsetRate = (offsetX.toFloat() * 0.5f) / viewPager!!.measuredWidth
        val scaleFactor = 1 - Math.abs(offsetRate)
        Log.d(
            "EXTREME_TRANSFORMER",
            "$scaleFactor $maxOffset"
        )
        if (scaleFactor > 0) {
            view.scaleX = scaleFactor
            view.scaleY = scaleFactor
            view.translationX = -maxOffset * offsetRate
        }
        Log.d("HEYCAN",position.toString())
    }

}

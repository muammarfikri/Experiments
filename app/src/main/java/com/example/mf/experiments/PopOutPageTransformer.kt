package com.example.mf.experiments

import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager

class PopOutPageTransformer(pPager: ViewPager, pAdapter: CardFragmentPagerAdapter) : ViewPager.PageTransformer,
    ViewPager.OnPageChangeListener {

    private val pager = pPager
    private val adapter = pAdapter

    init {
        pager.addOnPageChangeListener(this)
    }
    override fun onPageScrollStateChanged(state: Int) {

    }

    private var lastOffset: Float = 0f

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        Log.d("TransformAndRollOut", "$position, $positionOffset, $positionOffsetPixels")
//        var currentPos: Int
//        var nextPos: Int
//        var realOffset: Float
//        if(lastOffset > positionOffset){
//            currentPos = position + 1
//            nextPos = position
//            realOffset = 1 - positionOffset
//        }else{
//            currentPos = position
//            nextPos = position + 1
//            realOffset = positionOffset
//        }

        for(pos in 0 until adapter.count){
            if(pos == position){
                val fl1 = -30f
                adapter.getCardViewAt(position)!!.apply {
                    animate().translationY(fl1)
                    animate().alpha(1f)
                }
            }else{
                adapter.getCardViewAt(pos)!!.apply {
                    animate().translationY(0f)
                    animate().alpha(0.2f)
                }
            }
        }

        lastOffset = positionOffset
    }


    override fun onPageSelected(position: Int) {
    }

    override fun transformPage(view: View, position: Float) {
    }
}
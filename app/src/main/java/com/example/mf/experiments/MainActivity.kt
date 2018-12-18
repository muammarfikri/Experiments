package com.example.mf.experiments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val fragmentPagerAdapter = CardFragmentPagerAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPager()


    }

    private fun initPager() {
        pager.adapter = fragmentPagerAdapter
        pager.offscreenPageLimit = fragmentPagerAdapter.count
        pager.currentItem = 1
        pager.setPageTransformer(false, PopOutPageTransformer(pager, fragmentPagerAdapter))
    }
}

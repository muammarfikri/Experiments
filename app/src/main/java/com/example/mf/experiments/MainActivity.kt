package com.example.mf.experiments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {

    private val fragmentPagerAdapter = CardFragmentPagerAdapter(supportFragmentManager)
    private val uiScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_2)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_cont, ListFragment()).commit()
    }

    private fun initPager() {
        pager.adapter = fragmentPagerAdapter
        pager.offscreenPageLimit = fragmentPagerAdapter.count
        pager.currentItem = 1
        pager.setPageTransformer(false, PopOutPageTransformer(pager, fragmentPagerAdapter))
    }
}


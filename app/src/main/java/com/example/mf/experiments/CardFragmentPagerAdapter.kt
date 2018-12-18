package com.example.mf.experiments

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class CardFragmentPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    var fragmentList : MutableList<ItemFragment> = ArrayList()

    init {
        for (_i in 0 until 5) addCardFragment(ItemFragment())
    }

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int  = fragmentList.size

    fun getCardViewAt(pos: Int) = fragmentList[pos].getCard()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        return super.instantiateItem(container, position)
        var fragment = super.instantiateItem(container, position)
        fragmentList[position] = fragment as ItemFragment
        return fragment
    }

    fun addCardFragment(fragment: ItemFragment) = fragmentList.add(fragment)

}
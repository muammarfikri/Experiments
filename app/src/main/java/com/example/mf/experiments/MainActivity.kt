package com.example.mf.experiments

import android.content.Context
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Slide
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.detail_fragment.*

class MainActivity : AppCompatActivity(), FragmentInterface {
    override fun goBack(view: View) {

    }

    override fun goToDetail(view: View) {
        val detailFragment = DetailFragment()
        detailFragment.sharedElementEnterTransition = ChangeBounds()
        detailFragment.enterTransition = Slide(Gravity.BOTTOM)
        detailFragment.exitTransition = Slide(Gravity.END)
        detailFragment.sharedElementReturnTransition = ChangeBounds()
        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.frame_container, detailFragment)
            .addSharedElement(view, "shared2")
            .addToBackStack(null)
            .commit()
    }


    private val fragmentPagerAdapter = CardFragmentPagerAdapter(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.experiments1)
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container,Fragment1())
            .addToBackStack(null)
            .commit()
    }

    private fun initPager() {
        pager.adapter = fragmentPagerAdapter
        pager.offscreenPageLimit = fragmentPagerAdapter.count
        pager.currentItem = 1
        pager.setPageTransformer(false, PopOutPageTransformer(pager, fragmentPagerAdapter))
    }
}

interface FragmentInterface {
    fun goToDetail(view: View)
    fun goBack(view: View)
}

class Fragment1 : Fragment() {
    var root : View? = null
    var listener: FragmentInterface? = null
    override fun onAttach(context: Context) {
        if (context is FragmentInterface) listener = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.first_half_fragment, container, false)
        return root
    }

    override fun onStart() {
        exitTransition = Slide(Gravity.TOP)
        enterTransition = Slide(Gravity.TOP)
        root!!.findViewById<ImageView>(R.id.imageShared).setOnClickListener {
            listener?.goToDetail(it)
        }
        super.onStart()
    }
}

class Fragment2 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.second_half_fragment, container, false)
    }
}

class DetailFragment : Fragment() {
    var listener: FragmentInterface? = null
    override fun onAttach(context: Context) {
        if (context is FragmentInterface) listener = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.detail_fragment, container, false)
        root.findViewById<View>(R.id.imageShared2).setOnClickListener {
            listener?.goBack(it)
        }
        postponeEnterTransition()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       Glide.with(this).load("https://picsum.photos/200").into(imageShared2)
        startPostponedEnterTransition()
    }

}

package com.example.mf.experiments

import android.graphics.Path
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.*
import com.example.mf.experiments.retrofit.RetrofitProvider
import kotlinx.android.synthetic.main.fragment_list_user.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListFragment : Fragment() {
    init {
        enterTransition = Fade()
        exitTransition = Slide(Gravity.START)
    }
    private val rvAdapter = UserAdapter(object : BaseRVInterface {
        override fun goToDetail(uri: ImageView) {
            val df = DetailFragment.newInstance(uri.transitionName)
            val ts =
                TransitionSet().setOrdering(TransitionSet.ORDERING_TOGETHER)
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeTransform())
                    .addTransition(ChangeImageTransform())
            df.sharedElementEnterTransition = ts
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_cont, df)
                .addSharedElement(uri, uri.transitionName)
                .addToBackStack(null)
                .commit()
        }
    })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_user, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listUsers.layoutManager = LinearLayoutManager(this.activity)
        listUsers.adapter = rvAdapter
        GlobalScope.launch {
            val result = RetrofitProvider.apiService.getUsers().await()
            activity!!.runOnUiThread {
                rvAdapter.addToList(result)
            }
        }
    }
}
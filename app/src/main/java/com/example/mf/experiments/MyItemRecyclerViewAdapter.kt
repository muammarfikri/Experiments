package com.example.mf.experiments

import android.media.Image
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.Gravity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.transition.Fade
import com.bumptech.glide.Glide


import com.example.mf.experiments.ItemFragment2.OnListFragmentInteractionListener
import com.example.mf.experiments.dummy.DummyContent.DummyItem

import kotlinx.android.synthetic.main.fragment_item2.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
    private val mValues: MutableList<DummyItem>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item2, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        Glide.with(holder.mIdView).load("https://picsum.photos/20$position").into(holder.mIdView)
        holder.mContentView.text = item.content
        ViewCompat.setTransitionName(holder.mContentView,"$position - shared")
        with(holder.mContentView) {
            setOnClickListener{
                mListener?.goToDetail(it)
            }
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: ImageView = mView.item_image
        val mContentView: TextView = mView.content

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}

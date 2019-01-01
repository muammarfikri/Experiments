package com.example.mf.experiments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRVAdapter<T>() : RecyclerView.Adapter<BaseRVAdapter.BaseVHolder>() {
    abstract fun list() : ArrayList<T>
    abstract fun layout() : Int
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVHolder {
            return BaseVHolder(LayoutInflater.from(parent.context).inflate(layout(),parent,false))
    }
    override fun getItemCount(): Int = list().size
    class BaseVHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
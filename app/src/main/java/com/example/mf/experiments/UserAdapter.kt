package com.example.mf.experiments

import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.mf.experiments.retrofit.UserResponse

class UserAdapter(_listener : BaseRVInterface) : BaseRVAdapter<UserResponse>(){
    var listener = _listener
    var userList = ArrayList<UserResponse>()
    override fun list(): ArrayList<UserResponse> = userList
    fun addToList(li :List<UserResponse>){
        userList.addAll(li)
        notifyItemRangeInserted(list().lastIndex, li.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVHolder {
        Log.d("MAMA", "loaded")
        return super.onCreateViewHolder(parent, viewType)
    }
    override fun layout(): Int = R.layout.item_user
    override fun onBindViewHolder(holder: BaseVHolder, position: Int) {
        Log.d("MAMA", "loaded $position")
        val response = userList[position]
        val view = holder.itemView
        view.apply {
            findViewById<TextView>(R.id.name).text = response.name
            val imageView = findViewById<ImageView>(R.id.useravi)
            imageView.load(response.avatarURI)
            imageView.transitionName = response.avatarURI
            setOnClickListener {
                listener.goToDetail(imageView)
            }
        }
    }
}
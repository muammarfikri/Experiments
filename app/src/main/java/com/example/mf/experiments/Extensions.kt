package com.example.mf.experiments

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(uri : String){
    Glide.with(this).load(uri).into(this)
}
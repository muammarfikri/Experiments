package com.example.mf.experiments

import android.content.Context

fun dp2px(context: Context, dipValue: Float): Int {
    val m = context.resources.displayMetrics.density
    return (dipValue * m + 0.5f).toInt()
}
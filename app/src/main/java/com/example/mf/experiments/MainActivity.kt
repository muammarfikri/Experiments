package com.example.mf.experiments

import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.ab_activity.*

class MainActivity : AppCompatActivity() {
    private val matrix = Matrix()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ab_activity)
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
//            val range = appBarLayout.totalScrollRange - iconIV.top
//            iconIV.alpha = (1.0f - (verticalOffset/2) / range)
//            Log.d("MAMA", "$verticalOffset $range }")
//            Log.d("MAMA","${-verticalOffset} ${iconIV.top}")
            val a = -verticalOffset
            val b = iconIV.top
            val c = appBarLayout.totalScrollRange
            fun scale(start:Int, scaleStartX:Int, scaleEndX:Int, startOffset: Int = 0,endOffset:Int  = 0) : Float{
//                val startX = scaleStartX
//                val endX= scaleEndX
//                val d = endX - scaleStartX
//                val e = -d
//                val f = c - a
//                val result = -f.toFloat() / e.toFloat()
//                Log.d("MAMA","$result")
//                return result
                return 0f
            }
            Log.d("MAMA","$verticalOffset $c")
            iconIV.alpha = 1f - (a.toFloat() / c.toFloat())
            imageView2.scaleX = 1f + ((a.toFloat() / c.toFloat())/2)
            imageView2.scaleY = 1f + ((a.toFloat() / c.toFloat())/2)
        })
    }


    fun toast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    fun generateList(): List<Int> {
        return List(5) { 0 }
    }
}

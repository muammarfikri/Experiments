package com.example.mf.experiments

import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.palette.graphics.Palette
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.ab_activity.*

class MainActivity : AppCompatActivity() {
    private val matrix = Matrix()
    lateinit var color1 : Palette.Swatch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ab_activity)
        Palette.from(
            BitmapFactory.decodeResource(this.resources,
                R.drawable.spider)).generate {
            color1 = it!!.dominantSwatch!!
            collapser.contentScrim = ColorDrawable(color1.titleTextColor)
        }
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
//            val range = appBarLayout.totalScrollRange - iconIV.top
//            iconIV.alpha = (1.0f - (verticalOffset/2) / range)
//            Log.d("MAMA", "$verticalOffset $range }")
//            Log.d("MAMA","${-verticalOffset} ${iconIV.top}")
            val a = -verticalOffset
            val b = iconIV.top
            val c = appBarLayout.totalScrollRange
            val vg = tLayout as ViewGroup
            val toolbar = vg.getChildAt(0)
            val bottomX = toolbar.bottom
//            Log.d("MAMA","$a $bottomX")
            val scale = a.toFloat() / c.toFloat()
            val scale2 = if(a > bottomX) {
                ((a.toFloat() - bottomX) / (c.toFloat() - bottomX) * 2)
            }else{
                0f
            }
            val scale3 = ((a.toFloat()) / (c.toFloat())) * 2
            val point = Point()
            windowManager.defaultDisplay.getSize(point)
            Log.d("MAMA","${point.x}")
            detail_content.translationY = a.toFloat() / 2
            poster.translationX = scale2
            detail_content.alpha = 1f - scale2
            poster.scaleX = 1f + (scale /2)
            poster.scaleY = 1f + (scale /2)
            tLayout.alpha = scale
            play.alpha = 1f - (scale * 2)
        })
    }


    fun toast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    fun generateList(): List<Int> {
        return List(5) { 0 }
    }
}

package com.example.mf.experiments

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.Explode
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showMe.setOnClickListener {
            TransitionManager.beginDelayedTransition(linearLayout2, Slide(Gravity.TOP))
            imageView3.apply {
                if (this.visibility == View.GONE) {
                    this.visibility = View.VISIBLE
                } else {
                    this.visibility = View.GONE
                }
            }
        }
        imageView3.setOnClickListener {
            Toast.makeText(this, "Just do it", Toast.LENGTH_SHORT).show()
            window.enterTransition = Explode()
            window.exitTransition = Explode()
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                imageView3,
                "imageShared"
            ).toBundle()
            startActivity(Intent(this, SecondActivity::class.java), options)
        }
    }

}

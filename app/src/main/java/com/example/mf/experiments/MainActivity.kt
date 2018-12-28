package com.example.mf.experiments

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.experiments2.*

class MainActivity : AppCompatActivity() {
    private val fragments = MutableList(5) { ContainerFragment() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.experiments2)
        fillViewPager()
    }

    private fun fillViewPager() {
        pager.setPageTransformer(false,PagerTransformerExtreme(this))
        pager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment = fragments[position]
            override fun getCount(): Int = fragments.size
        }
    }

}


class DialogF : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Remove").setPositiveButton("No more", DialogInterface.OnClickListener { dialog, id ->
                toaster("Yello", context!!)
            })
            builder.create()
        } ?: throw IllegalStateException("No null")
    }
}


@JvmOverloads
fun Activity.toaster(msg: String = "nono") {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun toaster(msg: String = "nono", ctx: Context) {
    Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
}

fun Activity.snacker(view: View, msg: String) {
    Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).setAction("DO it") {
        toaster("Did it")
    }.show()
}
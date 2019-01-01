package com.example.mf.experiments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.fragment_detail_user.*

class DetailFragment : Fragment() {
    private val glideListener = object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            startPostponedEnterTransition()
            return false
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_detail_user, container, false)
        postponeEnterTransition()
        arguments!!.getString("avi").apply {
            val imageView = root.findViewById<ImageView>(R.id.bigIV)
            imageView.transitionName = this
            Glide.with(imageView).load("https://picsum.photos/500").listener(glideListener)
                .into(imageView)
        }
        return root
    }

    companion object {
        fun newInstance(uri: String): DetailFragment {
            val bundle = Bundle()
            bundle.putString("avi", uri)
            val df = DetailFragment()
            df.arguments = bundle
            return df
        }
    }
}

package com.example.binder.swipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.binder.R


class SwipeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_swipe, container, false)
    }
    fun onImageClick(view: View) {
        when (view.id) {
            R.id.book_button -> {
                val likeBookImageView = requireView().findViewById<ImageView>(R.id.like_book)
                val disBookImageView = requireView().findViewById<ImageView>(R.id.dis_book)

                likeBookImageView.visibility = View.VISIBLE
                disBookImageView.visibility = View.VISIBLE
            }
            R.id.like, R.id.dislike, R.id.like_book, R.id.dis_book -> {
                // send
            }
        }
    }
}
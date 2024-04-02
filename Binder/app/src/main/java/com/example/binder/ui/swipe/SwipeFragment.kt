package com.example.binder.ui.swipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.binder.R
import com.example.binder.databinding.FragmentSwipeBinding


class SwipeFragment : Fragment() {

    private var _binding: FragmentSwipeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSwipeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
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
package com.example.binder.ui.swipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
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

        binding.bookButton.setOnClickListener {
            val likeBookImageView = requireView().findViewById<ImageView>(R.id.like_book)
            val disBookImageView = requireView().findViewById<ImageView>(R.id.dis_book)

            likeBookImageView.visibility = View.VISIBLE
            disBookImageView.visibility = View.VISIBLE
        }
        val sendListener = OnClickListener {
            // send
        }
        binding.like.setOnClickListener(sendListener)
        binding.dislike.setOnClickListener(sendListener)
        binding.likeBook.setOnClickListener(sendListener)
        binding.disBook.setOnClickListener(sendListener)
        return root
    }
}
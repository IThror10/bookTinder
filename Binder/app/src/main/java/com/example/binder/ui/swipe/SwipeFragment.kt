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

    private var isBookButtonClicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSwipeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.bookButton.setOnClickListener {
            val likeBookImageView = requireView().findViewById<ImageView>(R.id.like)
            val disBookImageView = requireView().findViewById<ImageView>(R.id.dislike)

            likeBookImageView.visibility = View.VISIBLE
            disBookImageView.visibility = View.VISIBLE

            isBookButtonClicked = true
        }
        val sendListener = OnClickListener {

        }
        binding.like.setOnClickListener(sendListener)
        binding.dislike.setOnClickListener(sendListener)
        return root
    }
    private fun sendBookReaction(liked: Boolean) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
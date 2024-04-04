package com.example.binder.ui.swipe

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.binder.ErrorUtils
import com.example.binder.R
import com.example.binder.app.BinderApplication
import com.example.binder.bearer
import com.example.binder.databinding.FragmentSwipeBinding
import com.example.binder.model.Giveaway
import com.example.binder.setBitmap
import com.example.binder.ui.profile.setVisible
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.LinkedList

class SwipeFragment : Fragment() {

    private var _binding: FragmentSwipeBinding? = null
    private val binding get() = _binding!!

    private var isReadBook = false

    private lateinit var likeBook: ImageView
    private lateinit var dislikeBook: ImageView

    private lateinit var readLikeBook: ImageView
    private lateinit var readDislikeBook: ImageView

    private var currentGiveaway: Giveaway? = null
    private var giveawayList: LinkedList<Giveaway> = LinkedList()

    private lateinit var bookPhoto: ImageView
    private lateinit var bookTitle: TextView
    private lateinit var bookAuthor: TextView
    private lateinit var bookYear: TextView
    private lateinit var bookDesc: TextView
    private lateinit var giveawayDesc: TextView

    private lateinit var noGiveaway: TextView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSwipeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        likeBook = binding.like
        dislikeBook = binding.dislike
        readLikeBook = binding.pinkBook1
        readDislikeBook = binding.pinkBook2

        bookPhoto = binding.bookPhoto
        bookTitle = binding.swipeBookTitle
        bookAuthor = binding.swipeBookAuthor
        bookYear = binding.swipeBookYear
        bookDesc = binding.swipeBookDescription
        giveawayDesc = binding.giveawayBookDescription

        noGiveaway = binding.noCurrentSwipe

        updateUI()
        binding.bookButton.setOnClickListener {
            isReadBook = !isReadBook
            if (isReadBook) {
                readLikeBook.setVisible(true)
                readDislikeBook.setVisible(true)
            } else {
                readLikeBook.setVisible(false)
                readDislikeBook.setVisible(false)
            }
        }
        val sendListener: (Boolean) -> OnClickListener = { isLiked ->
            OnClickListener {
                if (currentGiveaway == null) return@OnClickListener
                val completable =
                    if (isReadBook) BinderApplication.instance.binderApi.rate(
                        bearer(),
                        currentGiveaway!!.book.id,
                        isLiked
                    )
                    else BinderApplication.instance.binderApi.swipe(
                        bearer(),
                        currentGiveaway!!.id,
                        isLiked
                    )
                completable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.i("swipe", it.toString())
                        isReadBook = false
                        readLikeBook.setVisible(false)
                        readDislikeBook.setVisible(false)
                        nextGiveaway()
                    }, { ErrorUtils.showMessage(it, requireContext(), "swipe") })
            }
        }
        binding.like.setOnClickListener(sendListener(true))
        binding.dislike.setOnClickListener(sendListener(false))
        getRecGiveaways()
        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CheckResult")
    private fun getRecGiveaways() {
        BinderApplication.instance.binderApi.getRecommendGiveaways(bearer())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i("getRecommendGiveaways", it.toString())
                giveawayList = LinkedList(it)
                if (giveawayList.isNotEmpty()) nextGiveaway()
            }, { ErrorUtils.showMessage(it, requireContext(), "getRecommendGiveaways") })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun nextGiveaway() {
        currentGiveaway = if (giveawayList.isEmpty()) {
            getRecGiveaways()
            null
        } else giveawayList.removeFirst()
        updateUI()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateUI() {
        if (currentGiveaway == null) bookPhoto.setImageResource(R.drawable.book)
        else bookPhoto.setBitmap(currentGiveaway?.photo)
        noGiveaway.setVisible(currentGiveaway == null)
        bookTitle.text = currentGiveaway?.book?.title ?: ""
        bookAuthor.text = currentGiveaway?.book?.author ?: ""
        bookYear.text = currentGiveaway?.book?.year?.toString() ?: ""
        bookDesc.text = currentGiveaway?.book?.description ?: ""
        giveawayDesc.text = currentGiveaway?.description ?: ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
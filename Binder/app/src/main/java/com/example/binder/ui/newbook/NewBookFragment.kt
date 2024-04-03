package com.example.binder.ui.newbook

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.binder.ErrorUtils
import com.example.binder.app.BinderApplication
import com.example.binder.bearer
import com.example.binder.currentUser
import com.example.binder.databinding.FragmentNewBookBinding
import com.example.binder.int
import com.example.binder.model.Book
import com.example.binder.model.Giveaway
import com.example.binder.str
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewBookFragment : Fragment() {

    private var _binding: FragmentNewBookBinding? = null
    private val binding get() = _binding!!

    private lateinit var bookTitleET: EditText
    private lateinit var bookAuthorET: EditText
    private lateinit var bookYearET: EditText
    private lateinit var bookDescET: EditText
    private lateinit var giveawayDescET: EditText
    private lateinit var giveawayLocET: EditText
    private lateinit var suggestRV: RecyclerView
    private lateinit var submitButton: Button

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewBookBinding.inflate(inflater, container, false)
        val root: View = binding.root

        bookTitleET = binding.newBookTitle
        bookAuthorET = binding.newBookAuthorName
        bookYearET = binding.newBookYear
        bookDescET = binding.newBookDescription
        giveawayDescET = binding.newBookGiveawayDescription
        giveawayLocET = binding.newBookLocation
        suggestRV = binding.suggestBooksRv
        submitButton = binding.newBookSubmit

        submitButton.setOnClickListener {
            if (bookTitleET.text.isBlank()) return@setOnClickListener
            if (bookAuthorET.text.isBlank()) return@setOnClickListener
            if (bookYearET.text.isBlank()) return@setOnClickListener
            if (bookDescET.text.isBlank()) return@setOnClickListener
            if (giveawayDescET.text.isBlank()) return@setOnClickListener
            if (giveawayLocET.text.isBlank()) return@setOnClickListener
            val book =
                Book(-1, bookTitleET.str(), bookAuthorET.str(), bookYearET.int(), bookDescET.str())
            val giveaway = Giveaway(-1, currentUser.userId, giveawayDescET.str(), book)
            BinderApplication.instance.binderApi.createGiveaway(bearer(), giveaway)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.i("Created giveaway!", it.toString())
                }, { ErrorUtils.showMessage(it, this.requireContext()) })
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
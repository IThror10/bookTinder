package com.example.binder.ui.newbook

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.example.binder.ui.rv.BookAdapter
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

    private lateinit var bookAdapter: BookAdapter
    private var bookSet: Boolean = false

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewBookBinding.inflate(inflater, container, false)
        val root: View = binding.root
        bookAdapter = BookAdapter(this::chooseBook)

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
        bookTitleET.addTextChangedListener { if (bookSet) bookSet = false else suggestBooks(it.toString()) }
        return root
    }

    @SuppressLint("CheckResult")
    fun suggestBooks(name: String) {
        if (name.isBlank() || name.contains('\n')) updateUI(listOf())
        else BinderApplication.instance.binderApi.getSuggestedBooks(bearer(), name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                updateUI(it)
                Log.i("Suggested books!", it.toString())
            }, { ErrorUtils.showMessage(it, this.requireContext()) })
    }

    private fun updateUI(books: List<Book>) {
        bookAdapter.setData(books)
        suggestRV.layoutManager = LinearLayoutManager(context)
        suggestRV.adapter = bookAdapter
    }

    private fun chooseBook(book: Book) {
        bookSet = true
        bookTitleET.setText(book.title)
        bookAuthorET.setText(book.author)
        bookYearET.setText(book.year.toString())
        bookDescET.setText(book.description)
        updateUI(listOf())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
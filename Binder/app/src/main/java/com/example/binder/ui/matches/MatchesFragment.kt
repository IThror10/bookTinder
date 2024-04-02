package com.example.binder.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.binder.databinding.FragmentMatchesBinding
import com.example.binder.model.Book
import com.example.binder.ui.rv.BookAdapter

class MatchesFragment : Fragment() {

    private var _binding: FragmentMatchesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var bookRV: RecyclerView
    private lateinit var bookAdapter: BookAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        bookAdapter = BookAdapter()
        bookRV = binding.matchesRvBooks
        updateUI()
        return root
    }

    fun updateUI() {
        bookAdapter.setData(listOf(
            Book("Author", "Book", 2024),
            Book("Author", "Book", 2024),
            Book("Author", "Book", 2024),
            Book("Author", "Book", 2024),
            Book("Author", "Book", 2024),
            Book("Author", "Book", 2024),
            Book("Author", "Book", 2024),
            Book("Author", "Book", 2024),
            Book("Author", "Book", 2024),
            Book("Author", "Book", 2024),
            Book("Author", "Book", 2024),
            Book("Author", "Book", 2024),
            Book("Author", "Book", 2024),
            Book("Author", "Book", 2024),
            Book("Author", "Book", 2024),
            Book("Author", "Book", 2024),
            Book("Author", "Book", 2024),
            Book("Author", "Book", 2024),
            Book("Author", "Book", 2024),
            Book("Author", "Book", 2024)
        ))
        bookRV.layoutManager = LinearLayoutManager(context)
        bookRV.adapter = bookAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
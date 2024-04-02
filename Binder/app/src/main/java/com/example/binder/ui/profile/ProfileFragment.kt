package com.example.binder.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.binder.databinding.FragmentProfileBinding
import com.example.binder.model.Book
import com.example.binder.model.Profile
import com.example.binder.ui.rv.BookAdapter

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var bookRV: RecyclerView
    private lateinit var bookAdapter: BookAdapter
    private lateinit var profileName: EditText
    private lateinit var profilePersonal: EditText

    var profile = Profile("John Doe", "Hello!\nI'm John", listOf(
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        bookAdapter = BookAdapter()
        bookRV = binding.profileRvBooks
        profileName = binding.profileName
        profilePersonal = binding.profilePersonalInfo

        // Editable false
        profileName.keyListener = null
        profilePersonal.keyListener = null

        updateUI()
        return root
    }

    fun updateUI() {
        profileName.setText(profile.name)
        profilePersonal.setText(profile.personal)
        bookAdapter.setData(profile.books)
        bookRV.layoutManager = LinearLayoutManager(context)
        bookRV.adapter = bookAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
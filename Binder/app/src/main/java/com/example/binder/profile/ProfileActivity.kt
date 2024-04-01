package com.example.binder.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.binder.R
import com.example.binder.model.Book

class ProfileActivity : AppCompatActivity() {

    private lateinit var bookRV: RecyclerView
    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        bookAdapter = BookAdapter()
        bookRV = findViewById(R.id.profile_rv_books)
        updateUI()
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
        bookRV.layoutManager = LinearLayoutManager(this)
        bookRV.adapter = bookAdapter
    }
}
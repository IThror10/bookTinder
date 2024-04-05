package com.example.binder.ui.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.binder.R
import com.example.binder.model.Book

class BookAdapter(
    val onClick: (Book) -> Unit
) : RecyclerView.Adapter<BookViewHolder>() {

    private var data: List<Book> = listOf()

    fun setData(data: List<Book>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BookViewHolder(inflater.inflate(R.layout.book_item, parent, false)).apply {
            this.itemView.setOnClickListener {
                val book = data[this.adapterPosition]
                onClick(book)
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = data[position]
        holder.bind(book)
    }
}
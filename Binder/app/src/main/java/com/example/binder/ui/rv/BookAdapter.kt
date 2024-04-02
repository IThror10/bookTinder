package com.example.binder.ui.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.binder.R
import com.example.binder.model.Book
import com.example.binder.model.toInfoStr

class BookAdapter : RecyclerView.Adapter<BookViewHolder>() {

    private var data: List<Book> = listOf()

    fun setData(data: List<Book>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BookViewHolder(inflater.inflate(R.layout.book_item, parent, false))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = data[position]
        holder.bind(book)
    }
}

class BookViewHolder(root: View): RecyclerView.ViewHolder(root) {

    private val bookInfo: TextView = root.findViewById(R.id.book_item_book_info)

    fun bind(book: Book) {
        bookInfo.text = book.toInfoStr()
    }

}
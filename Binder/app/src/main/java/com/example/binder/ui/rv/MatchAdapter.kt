package com.example.binder.ui.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.binder.R
import com.example.binder.model.Match
import com.example.binder.model.toInfoStr

class MatchAdapter(
    val context: Context
) : RecyclerView.Adapter<BookViewHolder>() {

    private var data: List<Match> = listOf()

    fun setData(data: List<Match>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BookViewHolder(inflater.inflate(R.layout.book_item, parent, false)).apply {
            itemView.findViewById<TextView>(R.id.book_item_book_info).setOnClickListener {
                val match = data[this.adapterPosition]
                showPopup(match)
            }
        }
    }

    private fun showPopup(match: Match) {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.match_book_popup, null)
        val myBook: TextView = view.findViewById(R.id.popup_my_book)
        val matchBook: TextView = view.findViewById(R.id.popup_match_book)

        myBook.text = match.ours.book.toInfoStr()
        matchBook.text = match.other.book.toInfoStr()

        val alertDialog = AlertDialog.Builder(context).apply {
            setView(view)
            setCancelable(false)
            setPositiveButton(R.string.ok, null)
        }.create()

        alertDialog.show()
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val match = data[position]
        holder.bind(match.other.book)
    }
}
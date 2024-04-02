package com.example.binder.ui.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.binder.R
import com.example.binder.model.Match
import com.example.binder.model.toInfoStr

class MatchAdapter(
    val fragment: Fragment
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
        val inflater = LayoutInflater.from(fragment.context)
        val view = inflater.inflate(R.layout.match_book_popup, null)
        val myBook: TextView = view.findViewById(R.id.popup_my_book)
        val matchBook: TextView = view.findViewById(R.id.popup_match_book)

        myBook.text = match.myBook.toInfoStr()
        matchBook.text = match.matchBook.toInfoStr()

        val alertDialog = AlertDialog.Builder(fragment.requireContext()).apply {
            setView(view)
            setCancelable(false)
            setPositiveButton(R.string.ok, null)
        }.create()

        alertDialog.show()
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val match = data[position]
        holder.bind(match.matchBook)
    }
}
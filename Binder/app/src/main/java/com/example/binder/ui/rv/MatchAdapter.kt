package com.example.binder.ui.rv

import android.content.Context
import android.os.Build
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.binder.PhotoUtils
import com.example.binder.R
import com.example.binder.model.Match
import com.example.binder.model.toInfoStr
import com.example.binder.setBitmap

class MatchAdapter(
    val context: Context
) : RecyclerView.Adapter<BookViewHolder>() {

    private var data: List<Match> = listOf()

    fun setData(data: List<Match>) {
        this.data = data
        notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BookViewHolder(inflater.inflate(R.layout.book_item, parent, false)).apply {
            itemView.findViewById<TextView>(R.id.book_item_book_info).setOnClickListener {
                val match = data[this.adapterPosition]
                showPopup(match)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showPopup(match: Match) {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.match_book_popup, null)
        val ourTitle: TextView = view.findViewById(R.id.popup_my_book)
        val otherTitle: TextView = view.findViewById(R.id.popup_match_book)
        val ourBookPhoto: ImageView = view.findViewById(R.id.our_book_photo)
        val otherBookPhoto: ImageView = view.findViewById(R.id.other_book_photo)
        val otherProfilePhoto: ImageView = view.findViewById(R.id.popup_profile_match_photo)
        val otherProfileName: TextView = view.findViewById(R.id.popup_profile_match_name)
        val otherProfilePersonal: TextView = view.findViewById(R.id.popup_profile_match_personal)

        ourTitle.text = match.ours.book.toInfoStr()
        otherTitle.text = match.other.book.toInfoStr()

        ourTitle.movementMethod = ScrollingMovementMethod()
        otherTitle.movementMethod = ScrollingMovementMethod()
        otherProfileName.movementMethod = ScrollingMovementMethod()
        otherProfilePersonal.movementMethod = ScrollingMovementMethod()

        ourBookPhoto.setBitmap(match.ours.photo)
        otherBookPhoto.setBitmap(match.other.photo)
        ourBookPhoto.setOnClickListener { PhotoUtils.showPhoto(context, match.ours.photo, R.drawable.book) }
        otherBookPhoto.setOnClickListener { PhotoUtils.showPhoto(context, match.other.photo, R.drawable.book) }

        match.other.user?.let {
            otherProfilePhoto.setBitmap(it.photo)
            otherProfileName.text = it.name
            otherProfilePersonal.text = it.personal
        }

        otherProfilePhoto.setOnClickListener { PhotoUtils.showPhoto(context, match.other.user?.photo, R.drawable.avatar) }

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
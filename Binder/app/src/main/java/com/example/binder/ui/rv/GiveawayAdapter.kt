package com.example.binder.ui.rv

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.binder.ErrorUtils
import com.example.binder.R
import com.example.binder.app.BinderApplication
import com.example.binder.bearer
import com.example.binder.model.Book
import com.example.binder.model.Giveaway
import com.example.binder.model.toInfoStr
import com.example.binder.setBitmap
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GiveawayAdapter(
    val context: Context,
    val updateUI: () -> Unit
) : RecyclerView.Adapter<BookViewHolder>() {

    private var data: List<Giveaway> = listOf()

    fun setData(data: List<Giveaway>) {
        this.data = data
        notifyDataSetChanged()
    }

    @SuppressLint("CheckResult")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BookViewHolder(inflater.inflate(R.layout.giveaway_item, parent, false)).apply {
            itemView.findViewById<ImageView>(R.id.giveaway_remove_img).setOnClickListener {
                val giveaway = data[this.adapterPosition]
                setData(data.filter { it.id != giveaway.id })
                BinderApplication.instance.binderApi.deleteGiveaway(bearer(), giveaway.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.i("deleteGiveaway", giveaway.id.toString())
                        updateUI()
                    }, { ErrorUtils.showMessage(it, context, "deleteGiveaway") })
            }
            itemView.findViewById<View>(R.id.giveaway_item).setOnClickListener {
                val giveaway = data[this.adapterPosition]
                showGiveaway(giveaway)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showGiveaway(giveaway: Giveaway) {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.popup_edit_book, null)
        val bookTitleET: TextView = view.findViewById(R.id.new_book_title)
        val bookAuthorET: TextView = view.findViewById(R.id.new_book_author_name)
        val bookYearET: TextView = view.findViewById(R.id.new_book_year)
        val bookDescET: TextView = view.findViewById(R.id.new_book_description)
        val giveawayDescET: TextView = view.findViewById(R.id.new_book_giveaway_description)
        val bookPhoto: ImageView = view.findViewById(R.id.new_book_photo)

        bookTitleET.text = giveaway.book.title
        bookAuthorET.text = giveaway.book.author
        bookYearET.text = giveaway.book.year.toString()
        bookDescET.text = giveaway.book.description
        giveawayDescET.text = giveaway.description
        bookPhoto.setBitmap(giveaway.photo)

        val alertDialog = AlertDialog.Builder(context).apply {
            setView(view)
            setCancelable(false)
            setPositiveButton(R.string.ok, null)
        }.create()
        alertDialog.show()
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = data[position].book
        holder.bind(book)
    }
}

class BookViewHolder(root: View) : RecyclerView.ViewHolder(root) {

    private val bookInfo: TextView = root.findViewById(R.id.book_item_book_info)

    fun bind(book: Book) {
        bookInfo.text = book.toInfoStr()
    }

}
package com.example.binder.ui.rv

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.binder.ErrorUtils
import com.example.binder.R
import com.example.binder.app.BinderApplication
import com.example.binder.bearer
import com.example.binder.model.Book
import com.example.binder.model.Giveaway
import com.example.binder.model.toInfoStr
import com.example.binder.setBitmap
import com.example.binder.ui.profile.setEditable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GiveawayAdapter(val context: Context) : RecyclerView.Adapter<BookViewHolder>() {

    private var data: List<Giveaway> = listOf()

    fun setData(data: List<Giveaway>) {
        this.data = data
        notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BookViewHolder(inflater.inflate(R.layout.giveaway_item, parent, false)).apply {
            itemView.findViewById<ImageView>(R.id.giveaway_edit_img).setOnClickListener {
                val giveaway = data[this.adapterPosition]
                editGiveaway(giveaway)
            }
            itemView.findViewById<View>(R.id.giveaway_item).setOnClickListener {
                val giveaway = data[this.adapterPosition]
                showGiveaway(giveaway)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun editGiveaway(giveaway: Giveaway) {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.popup_edit_book, null)
        val bookTitleET: EditText = view.findViewById(R.id.new_book_title)
        val bookAuthorET: EditText = view.findViewById(R.id.new_book_author_name)
        val bookYearET: EditText = view.findViewById(R.id.new_book_year)
        val bookDescET: EditText = view.findViewById(R.id.new_book_description)
        val giveawayDescET: EditText = view.findViewById(R.id.new_book_giveaway_description)
        val suggestRV: RecyclerView = view.findViewById(R.id.suggest_books_rv)
        val bookPhoto: ImageView = view.findViewById(R.id.new_book_photo)

        var bookSet = false
        lateinit var bookAdapter: BookAdapter

        fun updateUI(books: List<Book>) {
            bookAdapter.setData(books)
            suggestRV.layoutManager = LinearLayoutManager(context)
            suggestRV.adapter = bookAdapter
        }
        fun chooseBook(book: Book) {
            bookSet = true
            bookTitleET.setText(book.title)
            bookAuthorET.setText(book.author)
            bookYearET.setText(book.year.toString())
            bookDescET.setText(book.description)
            updateUI(listOf())
        }

        fun suggestBooks(name: String) {
            @SuppressLint("CheckResult")
            if (name.isBlank() || name.contains('\n')) updateUI(listOf())
            else BinderApplication.instance.binderApi.getSuggestedBooks(bearer(), name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    updateUI(it)
                    Log.i("Suggested books!", it.toString())
                }, { ErrorUtils.showMessage(it, context, "getSuggestedBooks") })
        }

        bookAdapter = BookAdapter{chooseBook(it)}

        bookTitleET.setText(giveaway.book.title)
        bookAuthorET.setText(giveaway.book.author)
        bookYearET.setText(giveaway.book.year.toString())
        bookDescET.setText(giveaway.book.description)
        giveawayDescET.setText(giveaway.description)
        bookPhoto.setBitmap(giveaway.photo)

        bookTitleET.addTextChangedListener { if (bookSet) bookSet = false else suggestBooks(it.toString()) }

        val alertDialog = AlertDialog.Builder(context).apply {
            setView(view)
            setCancelable(false)
            setPositiveButton(R.string.submit, null)
            setNeutralButton("Cancel") { _, _ -> run {} }
        }.create()

        alertDialog.setOnShowListener {
            val button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            button.setOnClickListener {
                // send to edit
            }
        }
        alertDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showGiveaway(giveaway: Giveaway) {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.popup_edit_book, null)
        val bookTitleET: EditText = view.findViewById(R.id.new_book_title)
        val bookAuthorET: EditText = view.findViewById(R.id.new_book_author_name)
        val bookYearET: EditText = view.findViewById(R.id.new_book_year)
        val bookDescET: EditText = view.findViewById(R.id.new_book_description)
        val giveawayDescET: EditText = view.findViewById(R.id.new_book_giveaway_description)
        val bookPhoto: ImageView = view.findViewById(R.id.new_book_photo)


        bookTitleET.setText(giveaway.book.title)
        bookAuthorET.setText(giveaway.book.author)
        bookYearET.setText(giveaway.book.year.toString())
        bookDescET.setText(giveaway.book.description)
        giveawayDescET.setText(giveaway.description)
        bookPhoto.setBitmap(giveaway.photo)

        bookTitleET.setEditable(false)
        bookAuthorET.setEditable(false)
        bookYearET.setEditable(false)
        bookDescET.setEditable(false)
        giveawayDescET.setEditable(false)

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

class BookViewHolder(root: View): RecyclerView.ViewHolder(root) {

    private val bookInfo: TextView = root.findViewById(R.id.book_item_book_info)

    fun bind(book: Book) {
        bookInfo.text = book.toInfoStr()
    }

}
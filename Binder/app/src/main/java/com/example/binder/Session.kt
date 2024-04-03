package com.example.binder

import android.annotation.SuppressLint
import android.content.Context
import com.example.binder.app.BinderApplication
import com.example.binder.model.Book
import com.example.binder.model.UserData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

lateinit var userToken: String
lateinit var currentUser: UserData

var userBooks: List<Book> = listOf(
    Book(-1, "Book", "Author", 2024, "Good Book"),
    Book(-1, "Book", "Author", 2024, "Good Book"),
    Book(-1, "Book", "Author", 2024, "Good Book"),
    Book(-1, "Book", "Author", 2024, "Good Book"),
    Book(-1, "Book", "Author", 2024, "Good Book"),
    Book(-1, "Book", "Author", 2024, "Good Book"),
    Book(-1, "Book", "Author", 2024, "Good Book"),
    Book(-1, "Book", "Author", 2024, "Good Book"),
    Book(-1, "Book", "Author", 2024, "Good Book"),
    Book(-1, "Book", "Author", 2024, "Good Book"),
    Book(-1, "Book", "Author", 2024, "Good Book"),
    Book(-1, "Book", "Author", 2024, "Good Book"),
    Book(-1, "Book", "Author", 2024, "Good Book"),
    Book(-1, "Book", "Author", 2024, "Good Book"),
)

fun bearer() = "Bearer $userToken"
@SuppressLint("CheckResult")
fun getAuthInfo(context: Context, updateUI: () -> Unit) {
    BinderApplication.instance.binderApi.getAuthInfo(bearer())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            updateUI()
        }, { ErrorUtils.showMessage(it, context) })
}
package com.example.binder

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.binder.app.BinderApplication
import com.example.binder.model.Giveaway
import com.example.binder.model.Match
import com.example.binder.model.UserData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

lateinit var userToken: String
lateinit var currentUser: UserData

var userGiveaways: List<Giveaway> = listOf()
var userMatches: List<Match> = listOf()

fun bearer() = "Bearer $userToken"
@SuppressLint("CheckResult")
fun getAuthInfo(context: Context, updateUI: () -> Unit) {
    BinderApplication.instance.binderApi.getAuthInfo(bearer())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            Log.i("getAuthInfo", it.toString())
            currentUser = it
            updateUI()
        }, { ErrorUtils.showMessage(it, context, "getAuthInfo") })
}
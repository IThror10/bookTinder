package com.example.binder.register

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.binder.ErrorUtils
import com.example.binder.MainActivity
import com.example.binder.R
import com.example.binder.api.RegisterUserReq
import com.example.binder.app.BinderApplication
import com.example.binder.currentUser
import com.example.binder.userToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NewUserActivity : AppCompatActivity() {
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        val submitButton = findViewById<Button>(R.id.submit)
        val nameET = findViewById<EditText>(R.id.name)
        val personalET = findViewById<EditText>(R.id.personal)
        val yearET = findViewById<EditText>(R.id.year)
        val loginET = findViewById<EditText>(R.id.login)
        val passwordET = findViewById<EditText>(R.id.password)

        submitButton.setOnClickListener {
            if (nameET.text.isBlank()) return@setOnClickListener
            if (yearET.text.isBlank()) return@setOnClickListener
            if (loginET.text.isBlank()) return@setOnClickListener
            if (passwordET.text.isBlank()) return@setOnClickListener
            val name = nameET.text.toString()
            val personal = personalET.text.toString()
            val year = yearET.text.toString().toInt()
            val login = loginET.text.toString()
            val password = passwordET.text.toString()
            val registerReq = RegisterUserReq(login, name, password, personal, year)
            BinderApplication.instance.binderApi.registerUser(registerReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.i("registered", it.toString())
                    val intent = Intent(this, MainActivity::class.java)
                    currentUser = it.userData
                    userToken = it.jsonAuth
                    startActivity(intent)
                    finish()
                }, { ErrorUtils.showMessage(it, this, "registerUser") })
        }
    }
}
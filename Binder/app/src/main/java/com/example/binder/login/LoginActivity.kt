package com.example.binder.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.binder.ErrorUtils
import com.example.binder.MainActivity
import com.example.binder.R
import com.example.binder.api.LoginUserReq
import com.example.binder.app.BinderApplication
import com.example.binder.currentUser
import com.example.binder.register.NewUserActivity
import com.example.binder.userToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val signInButton = findViewById<Button>(R.id.sign_in_button)
        val signUp = findViewById<TextView>(R.id.sign_up)
        val loginUsernameET = findViewById<TextView>(R.id.login_username)
        val loginPasswordET = findViewById<TextView>(R.id.login_password)

        signInButton.setOnClickListener {
            if (loginUsernameET.text.isBlank()) return@setOnClickListener
            if (loginPasswordET.text.isBlank()) return@setOnClickListener

            val login = loginUsernameET.text.toString()
            val password = loginPasswordET.text.toString()
            BinderApplication.instance.binderApi.loginUser(LoginUserReq(login, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.i("loginned", it.toString())
                    val intent = Intent(this, MainActivity::class.java)
                    userToken = it.jsonAuth
                    currentUser = it.userData
                    startActivity(intent)
                    finish()
                }, { ErrorUtils.showMessage(it, this, "loginUser") })
        }
        signUp.setOnClickListener {
            val intent = Intent(this, NewUserActivity::class.java)
            startActivity(intent)
        }
    }
}
package com.example.binder

import android.widget.EditText

fun EditText.str() = this.text.toString()
fun EditText.int() = this.str().toInt()
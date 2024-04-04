package com.example.binder

import android.graphics.BitmapFactory
import android.os.Build
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import java.util.Base64

fun EditText.str() = this.text.toString()
fun EditText.int() = this.str().toInt()

@RequiresApi(Build.VERSION_CODES.O)
fun ImageView.setBitmap(str: String?) {
    if (!str.isNullOrBlank()) {
        val bytes = Base64.getDecoder().decode(str)
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        this.setImageBitmap(bitmap)
    }
}
package com.example.binder

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException

object PhotoUtils {

    @RequiresApi(Build.VERSION_CODES.O)
    fun showPhoto(context: Context, photo: String?, defaultId: Int) {
        val layout: View = LayoutInflater.from(context).inflate(R.layout.layout_photo, null)
        val img = layout.findViewById<ImageView>(R.id.full_photo)
        if (photo != null) img.setBitmap(photo)
        else img.setImageResource(defaultId)

        val alertDialog = AlertDialog.Builder(context).apply {
            setView(layout)
        }.create()

        alertDialog.show()
    }

    fun checkBytes(bitmap: Bitmap, context: Context): ByteArray? {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
        val bytes = stream.toByteArray()
        val imageSizeInMb = bytes.size / (1024 * 1024)
        return if (imageSizeInMb >= 5) {
            Toast.makeText(context, "Image size should not exceed 5 MB", Toast.LENGTH_SHORT).show()
            null
        } else bytes
    }

    fun getImageBitmap(context: Context, uri: Uri): Bitmap? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            null
        }
    }
}
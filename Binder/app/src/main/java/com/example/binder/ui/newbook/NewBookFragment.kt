package com.example.binder.ui.newbook

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.binder.ErrorUtils
import com.example.binder.PhotoUtils
import com.example.binder.PhotoUtils.getImageBitmap
import com.example.binder.app.BinderApplication
import com.example.binder.bearer
import com.example.binder.currentUser
import com.example.binder.databinding.FragmentNewBookBinding
import com.example.binder.int
import com.example.binder.model.Book
import com.example.binder.model.Giveaway
import com.example.binder.str
import com.example.binder.ui.rv.BookAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.Base64


class NewBookFragment : Fragment() {

    private var _binding: FragmentNewBookBinding? = null
    private val binding get() = _binding!!

    private lateinit var bookTitleET: EditText
    private lateinit var bookAuthorET: EditText
    private lateinit var bookYearET: EditText
    private lateinit var bookDescET: EditText
    private lateinit var giveawayDescET: EditText
    private lateinit var suggestRV: RecyclerView
    private lateinit var submitButton: Button
    private lateinit var bookPhoto: ImageView

    private lateinit var bookAdapter: BookAdapter
    private var bookSet: Boolean = false

    private var bookPhotoByteArray: ByteArray? = null

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 1
        private const val REQUEST_CODE_TAKE_PICTURE = 2
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewBookBinding.inflate(inflater, container, false)
        val root: View = binding.root
        bookAdapter = BookAdapter(this::chooseBook)

        bookTitleET = binding.newBookTitle
        bookAuthorET = binding.newBookAuthorName
        bookYearET = binding.newBookYear
        bookDescET = binding.newBookDescription
        giveawayDescET = binding.newBookGiveawayDescription
        suggestRV = binding.suggestBooksRv
        submitButton = binding.newBookSubmit

        bookPhoto = binding.newBookPhoto

        submitButton.setOnClickListener {
            if (bookTitleET.text.isBlank()) return@setOnClickListener
            if (bookAuthorET.text.isBlank()) return@setOnClickListener
            if (bookYearET.text.isBlank()) return@setOnClickListener
            if (bookDescET.text.isBlank()) return@setOnClickListener
            if (giveawayDescET.text.isBlank()) return@setOnClickListener
            val book =
                Book(-1, bookTitleET.str(), bookAuthorET.str(), bookYearET.int(), bookDescET.str())
            val photoB64 = bookPhotoByteArray?.let { Base64.getEncoder().encodeToString(it) }
            val giveaway = Giveaway(-1, currentUser.userId, giveawayDescET.str(), book, photoB64)
            BinderApplication.instance.binderApi.createGiveaway(bearer(), giveaway)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.i("Created giveaway!", it.toString())
                    Toast.makeText(requireContext(), "Created!", Toast.LENGTH_SHORT).show()
                }, { ErrorUtils.showMessage(it, this.requireContext(), "createGiveaway") })
        }
        bookTitleET.addTextChangedListener {
            if (bookSet) bookSet = false else suggestBooks(it.toString())
        }
        bookPhoto.setOnClickListener {
            openImageSourceSelectionDialog()
        }
        return root
    }

    @SuppressLint("CheckResult")
    fun suggestBooks(name: String) {
        if (name.isBlank() || name.contains('\n')) updateUI(listOf())
        else BinderApplication.instance.binderApi.getSuggestedBooks(bearer(), name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                updateUI(it)
                Log.i("Suggested books!", it.toString())
            }, { ErrorUtils.showMessage(it, this.requireContext(), "getSuggestedBooks") })
    }

    private fun updateUI(books: List<Book>) {
        if ((books.size == 1 && books[0].title == bookTitleET.str() && books[0].author == bookAuthorET.str())
            || bookTitleET.text.isBlank()
        ) bookAdapter.setData(listOf())
        else bookAdapter.setData(books)
        suggestRV.layoutManager = LinearLayoutManager(context)
        suggestRV.adapter = bookAdapter
    }

    private fun chooseBook(book: Book) {
        bookSet = true
        bookTitleET.setText(book.title)
        bookAuthorET.setText(book.author)
        if (book.year != null) bookYearET.setText(book.year.toString())
        bookDescET.setText(book.description)
        updateUI(listOf())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openImageSourceSelectionDialog() {
        val items = arrayOf("Gallery", "Camera")
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Select Image Source")
            .setItems(items) { _, which ->
                when (which) {
                    0 -> openGallery()
                    1 -> openCamera()
                }
            }
            .show()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE -> {
                    data?.data?.let { uri ->
                        val imageBitmap = getImageBitmap(requireContext(), uri)
                        imageBitmap?.let {
                            val bytes = PhotoUtils.checkBytes(it, requireContext())
                            if (bytes != null) {
                                bookPhoto.setImageBitmap(it)
                                bookPhotoByteArray = bytes
                            }
                        }
                    }
                }

                REQUEST_CODE_TAKE_PICTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap?
                    imageBitmap?.let {
                        val bytes = PhotoUtils.checkBytes(it, requireContext())
                        if (bytes != null) {
                            bookPhoto.setImageBitmap(it)
                            bookPhotoByteArray = bytes
                        }
                    }
                }
            }
        }
    }
}
package com.example.binder.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.binder.ErrorUtils
import com.example.binder.api.UpdateUserRequest
import com.example.binder.app.BinderApplication
import com.example.binder.currentUser
import com.example.binder.databinding.FragmentProfileBinding
import com.example.binder.bearer
import com.example.binder.ui.rv.GiveawayAdapter
import com.example.binder.userGiveaways
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.widget.Toast
import java.io.ByteArrayOutputStream
import android.net.Uri
import android.graphics.BitmapFactory
import java.io.FileNotFoundException
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var bookRV: RecyclerView
    private lateinit var giveawayAdapter: GiveawayAdapter
    private lateinit var profileName: EditText
    private lateinit var profilePersonal: EditText

    private lateinit var editIcon: ImageView
    private lateinit var saveIcon: ImageView


    private lateinit var profilePhotoImageView: ImageView
    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 1
        private const val REQUEST_CODE_TAKE_PICTURE = 2
    }
    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        giveawayAdapter = GiveawayAdapter(this.requireContext())
        bookRV = binding.profileRvBooks
        profileName = binding.profileName
        profilePersonal = binding.profilePersonalInfo

        profilePhotoImageView = binding.profilePhoto // мое

        editIcon = binding.profileEditIcon
        saveIcon = binding.profileSaveIcon

        saveIcon.setVisible(false)

        // Editable false

        profileName.setEditable(false)
        profilePersonal.setEditable(false)

        editIcon.setOnClickListener {
            editIcon.setVisible(false)
            saveIcon.setVisible(true)
            profileName.setEditable(true)
            profilePersonal.setEditable(true)
        }
        saveIcon.setOnClickListener {
            if (profileName.text.isBlank()) return@setOnClickListener
            val name = profileName.text.toString()
            val personal = profilePersonal.text.toString()
            saveIcon.setVisible(false)
            editIcon.setVisible(true)
            profileName.setEditable(false)
            profilePersonal.setEditable(false)
            val req = UpdateUserRequest(personal, name)
            BinderApplication.instance.binderApi.updateUser(bearer(), req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    currentUser = it
                    updateUI()
                }, { ErrorUtils.showMessage(it, this.requireContext()) })
        }
        updateUI()
        getGiveaways()
        profilePhotoImageView.setOnClickListener {
            openImageSourceSelectionDialog()
        }

        return root
    }

    @SuppressLint("CheckResult")
    private fun getGiveaways() {
        BinderApplication.instance.binderApi.getGiveaways(bearer())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                userGiveaways = it
                Log.i("Get giveaways!", it.toString())
                updateUI()
            }, { ErrorUtils.showMessage(it, this.requireContext()) })
    }

    private fun updateUI() {
        profileName.setText(currentUser.name)
        profilePersonal.setText(currentUser.personal)
        giveawayAdapter.setData(userGiveaways)
        bookRV.layoutManager = LinearLayoutManager(context)
        bookRV.adapter = giveawayAdapter
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
                        val imageBitmap = getImageBitmap(uri)
                        imageBitmap?.let {
                            if (checkImageSize(it)) {
                                profilePhotoImageView.setImageBitmap(it)
                            } else {
                                Toast.makeText(requireContext(), "Image size should not exceed 5 MB", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                REQUEST_CODE_TAKE_PICTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap?
                    imageBitmap?.let {
                        if (checkImageSize(it)) {
                            profilePhotoImageView.setImageBitmap(it)
                        } else {
                            Toast.makeText(requireContext(), "Image size should not exceed 5 MB", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun getImageBitmap(uri: Uri): Bitmap? {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            null
        }
    }

    private fun checkImageSize(imageBitmap: Bitmap): Boolean {
        val outputStream = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        val imageSizeInMb = byteArray.size / (1024.0 * 1024.0)
        return imageSizeInMb <= 5
    }
}


fun EditText.setEditable(editable: Boolean) {
    this.isClickable = editable
    this.isFocusable = editable
    this.isCursorVisible = editable
    this.isFocusableInTouchMode = editable
}

fun ImageView.setVisible(visible: Boolean) {
    this.isClickable = visible
    this.isVisible = visible
}
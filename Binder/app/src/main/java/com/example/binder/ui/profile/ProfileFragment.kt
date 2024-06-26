package com.example.binder.ui.profile

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
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.binder.ErrorUtils
import com.example.binder.api.UpdateUserRequest
import com.example.binder.app.BinderApplication
import com.example.binder.bearer
import com.example.binder.currentUser
import com.example.binder.databinding.FragmentProfileBinding
import com.example.binder.setBitmap
import com.example.binder.ui.rv.GiveawayAdapter
import com.example.binder.userGiveaways
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import android.widget.Toast
import android.net.Uri
import android.graphics.BitmapFactory
import android.widget.ProgressBar
import android.widget.TextView
import com.example.binder.PhotoUtils
import com.example.binder.PhotoUtils.getImageBitmap
import com.example.binder.R
import com.example.binder.getAuthInfo
import java.io.FileNotFoundException

import java.io.ByteArrayOutputStream
import java.util.Base64

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
    private lateinit var noGiveaways: TextView
    private lateinit var giveawaysProgressBar: ProgressBar

    private var profilePhotoByteArray: ByteArray? = null
    private var isEditMode: Boolean = false

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
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        giveawayAdapter = GiveawayAdapter(this.requireContext(), this::getGiveaways)
        bookRV = binding.profileRvBooks
        profileName = binding.profileName
        profilePersonal = binding.profilePersonalInfo

        profilePhotoImageView = binding.profilePhoto
        noGiveaways = binding.noGiveaways

        editIcon = binding.profileEditIcon
        saveIcon = binding.profileSaveIcon

        giveawaysProgressBar = binding.giveawaysProgressBar

        saveIcon.setVisible(false)

        // Editable false

        profileName.setEditable(false)
        profilePersonal.setEditable(false)

        editIcon.setOnClickListener {
            editIcon.setVisible(false)
            saveIcon.setVisible(true)
            profileName.setEditable(true)
            profilePersonal.setEditable(true)
            isEditMode = true
        }
        saveIcon.setOnClickListener {
            if (profileName.text.isBlank()) return@setOnClickListener
            isEditMode = false
            val name = profileName.text.toString()
            val personal = profilePersonal.text.toString()
            saveIcon.setVisible(false)
            editIcon.setVisible(true)
            profileName.setEditable(false)
            profilePersonal.setEditable(false)
            val photoB64 = profilePhotoByteArray?.let { Base64.getEncoder().encodeToString(it) }
            val req = UpdateUserRequest(personal, name, currentUser.year, photoB64)
            BinderApplication.instance.binderApi.updateUser(bearer(), req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.i("update user", it.toString())
                    currentUser = it
                    updateUI()
                    Toast.makeText(requireContext(), "Saved!", Toast.LENGTH_SHORT).show()
                }, { ErrorUtils.showMessage(it, this.requireContext(), "updateUser") })
        }
        profilePhotoImageView.setOnClickListener {
            if (!isEditMode) {
                PhotoUtils.showPhoto(requireContext(), currentUser.photo, R.drawable.avatar)
            } else openImageSourceSelectionDialog()
        }
        updateUI()
        getGiveaways()
        getAuthInfo(requireContext(), this::updateUI)

        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CheckResult")
    private fun getGiveaways() {
        giveawaysProgressBar.setVisible(true)
        BinderApplication.instance.binderApi.getUserGiveaways(bearer())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                userGiveaways = it
                Log.i("Get giveaways!", it.toString())
                updateUI()
            }, { ErrorUtils.showMessage(it, this.requireContext(), "getGiveaways") })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateUI() {
        giveawaysProgressBar.setVisible(false)
        noGiveaways.setVisible(userGiveaways.isEmpty())
        profileName.setText(currentUser.name)
        profilePersonal.setText(currentUser.personal)
        profilePhotoImageView.setBitmap(currentUser.photo)
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
                        val imageBitmap = getImageBitmap(requireContext(), uri)
                        imageBitmap?.let {
                            val bytes = PhotoUtils.checkBytes(it, requireContext())
                            if (bytes != null) {
                                profilePhotoImageView.setImageBitmap(it)
                                profilePhotoByteArray = bytes
                            }
                        }
                    }
                }

                REQUEST_CODE_TAKE_PICTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap?
                    imageBitmap?.let {
                        val bytes = PhotoUtils.checkBytes(it, requireContext())
                        if (bytes != null) {
                            profilePhotoImageView.setImageBitmap(it)
                            profilePhotoByteArray = bytes
                        }
                    }
                }
            }
        }
    }

}


fun EditText.setEditable(editable: Boolean) {
    this.isClickable = editable
    this.isFocusable = editable
    this.isCursorVisible = editable
    this.isFocusableInTouchMode = editable
}

fun View.setVisible(visible: Boolean) {
    this.isClickable = visible
    this.isVisible = visible
}
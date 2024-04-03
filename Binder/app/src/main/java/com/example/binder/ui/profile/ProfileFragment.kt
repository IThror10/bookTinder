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

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        giveawayAdapter = GiveawayAdapter()
        bookRV = binding.profileRvBooks
        profileName = binding.profileName
        profilePersonal = binding.profilePersonalInfo

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
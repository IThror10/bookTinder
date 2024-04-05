package com.example.binder.ui.matches

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.binder.ErrorUtils
import com.example.binder.app.BinderApplication
import com.example.binder.bearer
import com.example.binder.databinding.FragmentMatchesBinding
import com.example.binder.ui.profile.setVisible
import com.example.binder.ui.rv.MatchAdapter
import com.example.binder.userMatches
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MatchesFragment : Fragment() {

    private var _binding: FragmentMatchesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var matchRV: RecyclerView
    private lateinit var matchAdapter: MatchAdapter
    private lateinit var noMatches: TextView
    private lateinit var matchesProgress: ProgressBar

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        matchAdapter = MatchAdapter(requireContext())
        matchRV = binding.matchesRvBooks
        noMatches = binding.noMatches
        matchesProgress = binding.matchesProgressBar

        matchesProgress.setVisible(true)
        BinderApplication.instance.binderApi.getMatches(bearer())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.i("getMatches", it.toString())
                userMatches = it
                updateUI()
            }, { ErrorUtils.showMessage(it, requireContext(), "getMatches") })

        return root
    }

    fun updateUI() {
        matchesProgress.setVisible(false)
        noMatches.setVisible(userMatches.isEmpty())
        matchAdapter.setData(userMatches)
        matchRV.layoutManager = LinearLayoutManager(context)
        matchRV.adapter = matchAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
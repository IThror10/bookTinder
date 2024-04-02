package com.example.binder.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.binder.databinding.FragmentMatchesBinding
import com.example.binder.model.Book
import com.example.binder.model.Match
import com.example.binder.ui.rv.MatchAdapter

class MatchesFragment : Fragment() {

    private var _binding: FragmentMatchesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var matchRV: RecyclerView
    private lateinit var matchAdapter: MatchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        matchAdapter = MatchAdapter(this)
        matchRV = binding.matchesRvBooks
        updateUI()
        return root
    }

    fun updateUI() {
        val match = Match(Book("My Author", "My Book", 2024), Book("Match Author", "Match Book", 2023))
        matchAdapter.setData(mutableListOf<Match>().apply { repeat(50) {this.add(match)} })
        matchRV.layoutManager = LinearLayoutManager(context)
        matchRV.adapter = matchAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
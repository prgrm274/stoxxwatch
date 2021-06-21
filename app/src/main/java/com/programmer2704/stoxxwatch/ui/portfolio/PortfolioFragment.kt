package com.programmer2704.stoxxwatch.ui.portfolio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.programmer2704.stoxxwatch.databinding.FragmentPortfolioBinding

class PortfolioFragment : Fragment() {

    private lateinit var portfolioViewModel: PortfolioViewModel
    private var _binding: FragmentPortfolioBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        portfolioViewModel =
            ViewModelProvider(this).get(PortfolioViewModel::class.java)

        _binding = FragmentPortfolioBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        portfolioViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
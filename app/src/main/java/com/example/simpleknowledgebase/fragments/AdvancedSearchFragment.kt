package com.example.simpleknowledgebase.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.simpleknowledgebase.databinding.FragmentAdvancedSearchBinding
import com.example.simpleknowledgebase.viewmodels.AdvancedSearchViewModel


class AdvancedSearchFragment : Fragment() {

    private lateinit var advancedSearchViewModel: AdvancedSearchViewModel
    private var _binding: FragmentAdvancedSearchBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        advancedSearchViewModel =
            ViewModelProvider(this).get(AdvancedSearchViewModel::class.java)

        _binding = FragmentAdvancedSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}


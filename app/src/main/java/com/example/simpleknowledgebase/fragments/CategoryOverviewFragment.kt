package com.example.simpleknowledgebase.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.simpleknowledgebase.databinding.FragmentCategoryOverviewBinding
import com.example.simpleknowledgebase.viewmodels.CategoryOverviewViewModel


class CategoryOverviewFragment: Fragment() {

    private lateinit var categoryOverviewViewModel: CategoryOverviewViewModel
    private var _binding: FragmentCategoryOverviewBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        categoryOverviewViewModel =
            ViewModelProvider(this).get(CategoryOverviewViewModel::class.java)

        _binding = FragmentCategoryOverviewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





    }





}
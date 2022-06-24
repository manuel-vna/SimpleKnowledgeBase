package com.example.simpleknowledgebase.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.simpleknowledgebase.R
import com.example.simpleknowledgebase.databinding.FragmentKeywordSearchBinding
import com.example.simpleknowledgebase.viewmodels.KeywordSearchViewModel

class KeywordSearchFragment : Fragment() {

    private lateinit var keywordSearchViewModel: KeywordSearchViewModel
    private var _binding: FragmentKeywordSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        keywordSearchViewModel =
            ViewModelProvider(this).get(KeywordSearchViewModel::class.java)

        _binding = FragmentKeywordSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.keywordTvNoOfHits
        keywordSearchViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.keywordBtnAddEntry.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                findNavController().navigate(R.id.action_nav_home_to_nav_add_entry)

            }
        })



    }




}
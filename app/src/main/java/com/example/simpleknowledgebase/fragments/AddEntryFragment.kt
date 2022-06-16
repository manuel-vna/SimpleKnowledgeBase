package com.example.simpleknowledgebase.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.simpleknowledgebase.databinding.FragmentAddEntryBinding
import com.example.simpleknowledgebase.viewmodels.AddEntryViewModel


class AddEntryFragment : Fragment() {

    private lateinit var addEntryViewModel: AddEntryViewModel
    private var _binding: FragmentAddEntryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addEntryViewModel =
            ViewModelProvider(this).get(AddEntryViewModel::class.java)

        _binding = FragmentAddEntryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.addEntryText
        addEntryViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
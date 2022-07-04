package com.example.simpleknowledgebase.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.simpleknowledgebase.Entry
import com.example.simpleknowledgebase.databinding.FragmentUpdateDeleteEntryBinding
import com.example.simpleknowledgebase.viewmodels.AdvancedSearchViewModel
import com.example.simpleknowledgebase.viewmodels.UpdateDeleteViewModel

class UpdateDeleteEntryFragment: Fragment() {


    private lateinit var advancedSearchViewModel: AdvancedSearchViewModel
    private var _binding: FragmentUpdateDeleteEntryBinding? = null
    private lateinit var updateDeleteViewModel: UpdateDeleteViewModel

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        updateDeleteViewModel =
            ViewModelProvider(this).get(UpdateDeleteViewModel::class.java)

        var entry: Entry = arguments?.get("entry") as Entry
        Log.i("Debug_A", "Entry: "+entry)

        _binding = FragmentUpdateDeleteEntryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.updateDeleteBtnUpdate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                //TBD
            }
        })

        binding.updateDeleteBtnDelete.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                //TBD
            }
        })

    }

}


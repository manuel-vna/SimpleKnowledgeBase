package com.example.simpleknowledgebase.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.simpleknowledgebase.Entry
import com.example.simpleknowledgebase.R
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

        _binding = FragmentUpdateDeleteEntryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //receive entry from fragment
        var entry: Entry = arguments?.get("entryFromKeywordSearchFragment") as Entry

        display_entry(entry)

        binding.updateDeleteBtnUpdate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                updateEntry(entry)
            }
        })

        binding.updateDeleteBtnDelete.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                deleteEntry(entry)
            }
        })


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun display_entry(entry: Entry){

        // set fields of selected entry in ui
        binding.updateDeleteTvTitle.setText(entry.title)
        binding.updateDeleteTvDescription.setText(entry.description)
        binding.updateDeleteTvCategory.setText(entry.category)
        binding.updateDeleteTvSource.setText(entry.source)
    }


    fun updateEntry(entry: Entry){

        var updateDeleteTvTitle = binding.updateDeleteTvTitle.text.toString().trim()
        var updateDeleteTvDescription = binding.updateDeleteTvDescription.text.toString().trim()
        var updateDeleteTvCategory = binding.updateDeleteTvCategory.text.toString().trim()
        var updateDeleteTvSource = binding.updateDeleteTvSource.text.toString().trim()

        entry.title = updateDeleteTvTitle
        entry.description = updateDeleteTvDescription
        entry.category = updateDeleteTvCategory
        entry.source = updateDeleteTvSource

        updateDeleteViewModel.updateEntry(entry)
        Toast.makeText(context, "Entry updated", Toast.LENGTH_LONG).show()
    }

    fun deleteEntry(entry: Entry){
        updateDeleteViewModel.deleteEntry(entry)
        findNavController().navigate(R.id.action_updateDeleteEntryFragment_to_nav_home)
        Toast.makeText(context, "Entry deleted", Toast.LENGTH_LONG).show()
    }


}


package com.example.simpleknowledgebase.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.simpleknowledgebase.databinding.FragmentDialogExportDatabaseBinding


class ExportDatabaseDialogFragment  : DialogFragment(){

    private var _binding: FragmentDialogExportDatabaseBinding? = null
    private val binding get() = _binding!!


    companion object {

        const val TAG = "ExportDatabaseDialogFragment"

        fun newInstance(): ExportDatabaseDialogFragment {
            val fragment = ExportDatabaseDialogFragment()
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //return inflater.inflate(R.layout.fragment_dialog_export_database, container, false)

        _binding = FragmentDialogExportDatabaseBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.exportBtnCancel.setOnClickListener(){
            dismiss()
        }


    }





}
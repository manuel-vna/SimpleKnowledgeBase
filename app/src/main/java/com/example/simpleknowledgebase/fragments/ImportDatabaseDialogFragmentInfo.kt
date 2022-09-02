package com.example.simpleknowledgebase.fragments

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.example.simpleknowledgebase.EntryDatabase
import com.example.simpleknowledgebase.activities.MainActivity
import com.example.simpleknowledgebase.databinding.FragmentDialogImportDatabaseBinding
import com.example.simpleknowledgebase.utils.ImportDatabase

class ImportDatabaseDialogFragment  : DialogFragment(){

    private var _binding: FragmentDialogImportDatabaseBinding? = null
    private val binding get() = _binding!!

    val mainActivity = MainActivity()

    companion object {
        const val TAG = "ImportDatabaseDialogFragment"
        fun newInstance(): ImportDatabaseDialogFragment {
            val fragment = ImportDatabaseDialogFragment()
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentDialogImportDatabaseBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var appDb = EntryDatabase.getInstance(application = Application())


        binding.importBtnCancel.setOnClickListener(){
            dismiss() // close popup window
        }

        binding.importBtnOkay.setOnClickListener() {
            Toast.makeText(context, "You did it!", Toast.LENGTH_SHORT).show()
            //mainActiviy.startImportProcess()


        }

    }

}
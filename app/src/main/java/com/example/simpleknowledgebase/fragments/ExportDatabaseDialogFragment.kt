package com.example.simpleknowledgebase.fragments

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.simpleknowledgebase.EntryDatabase
import com.example.simpleknowledgebase.databinding.FragmentDialogExportDatabaseBinding
import com.example.simpleknowledgebase.utils.AppPermissions
import com.example.simpleknowledgebase.utils.ExportDatabase


class ExportDatabaseDialogFragment  : DialogFragment(){

    private var _binding: FragmentDialogExportDatabaseBinding? = null
    private val binding get() = _binding!!
    //var appDb: EntryDatabase? = null


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

        var appDb = EntryDatabase.getInstance(application = Application())


        binding.exportBtnCancel.setOnClickListener(){
            dismiss() // close popup window
        }

        binding.exportBtnOkay.setOnClickListener(){

            var appPermissions: AppPermissions = AppPermissions(requireActivity(),requireContext())
            if (appPermissions.checkExportPermission() == false) {
                appPermissions.requestExportPermission()
            }

            var exportDatabase: ExportDatabase = ExportDatabase(appDb)
            var writingSuccess = exportDatabase.runExport()
            dismiss() // close popup window

            if (writingSuccess) {
                Toast.makeText(
                    context,
                    "Storing data at: 'storage/emulated/0/Download'",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else{
                Toast.makeText(
                    context,
                    "Export failed!",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }


}
package com.example.simpleknowledgebase.fragments

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.simpleknowledgebase.EntryDatabase
import com.example.simpleknowledgebase.databinding.FragmentDialogExportDatabaseBinding
import com.example.simpleknowledgebase.utils.AppPermissions
import com.example.simpleknowledgebase.utils.ExportDatabase
import kotlinx.coroutines.*


class ExportDatabaseDialogFragment  : DialogFragment(){

    private var _binding: FragmentDialogExportDatabaseBinding? = null
    private val binding get() = _binding!!
    var exportSuccess: String = "unknown"


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


            fun displayToast(exportSuccess: String) {
                dismiss()
                if (exportSuccess == "success") {
                    Toast.makeText(
                        context,
                        "Storing data at: 'storage/emulated/0/Download'",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if(exportSuccess == "empty"){
                    Toast.makeText(
                        context,
                        "Database is empty!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else {
                    Toast.makeText(
                        context,
                        "Export failed!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            fun doWorkAsync(): Deferred<String> =
                GlobalScope.async(Dispatchers.IO) {
                return@async exportDatabase.runExport() }

            //Dispatchers.Main is important since the ui-toast 'displayToast()' is called within this coroutine. lifecycleScope.launch() could be used alternatively {
            GlobalScope.launch(Dispatchers.Main) {
            exportSuccess = doWorkAsync().await()
                displayToast(exportSuccess)
            }

        }
    }

}

/*
TO DO

- test export in api levels 28 and 30,33 (scoped storage introduce in android 11 / api level 30)

- write automated tests

 */
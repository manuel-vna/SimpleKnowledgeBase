package com.example.simpleknowledgebase.fragments
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultRegistry
import androidx.fragment.app.DialogFragment
import com.example.simpleknowledgebase.EntryDatabase
import com.example.simpleknowledgebase.databinding.FragmentDialogExportDatabaseBinding
import com.example.simpleknowledgebase.databinding.FragmentDialogImportDatabaseBinding
import com.example.simpleknowledgebase.utils.AppPermissions
import com.example.simpleknowledgebase.utils.ExportDatabase
import com.example.simpleknowledgebase.utils.ImportDatabase
import kotlinx.coroutines.*


class ImportDatabaseDialogFragment  : DialogFragment() {

    private var _binding: FragmentDialogImportDatabaseBinding? = null
    private val binding get() = _binding!!
    var importSuccess: String = "unknown"

    //variable that refers to the class: ImportDatabase
    lateinit var observerImportDatabase : ImportDatabase




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


        var aRR: ActivityResultRegistry? = activity?.activityResultRegistry

        // create instance of class ImportDatabase in order to access its public methods later
        observerImportDatabase = ImportDatabase(requireContext(),aRR!!)
        // include class ImportDatabase into the lifecycle environment by adding it as observer
        lifecycle.addObserver(observerImportDatabase)


        binding.exportBtnCancel.setOnClickListener(){
            dismiss() // close popup window
        }

        binding.exportBtnOkay.setOnClickListener(){

            dismiss()
            observerImportDatabase.selectImportFile()

            }


    }

}

    


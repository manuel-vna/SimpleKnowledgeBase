package com.example.simpleknowledgebase.fragments
import android.content.Context
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.activity.result.ActivityResultRegistry
import androidx.fragment.app.DialogFragment
import com.example.simpleknowledgebase.databinding.FragmentDialogImportDatabaseBinding
import com.example.simpleknowledgebase.utils.ImportDatabase


class ImportDatabaseDialogFragment  : DialogFragment() {

    lateinit var binding: FragmentDialogImportDatabaseBinding
    var importSuccess: String = "unknown"
    //variable that refers to the class: ImportDatabase
    lateinit var observerImportDatabase : ImportDatabase



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDialogImportDatabaseBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var aRR: ActivityResultRegistry? = activity?.activityResultRegistry

        // create instance of class ImportDatabase in order to access its public methods later
        observerImportDatabase = ImportDatabase(requireContext(), aRR!!)
        // include class ImportDatabase into the lifecycle environment by adding it as observer
        lifecycle.addObserver(observerImportDatabase)


        binding.importBtnCancel.setOnClickListener() {
            dismiss() // close popup window
        }


        binding.importBtnOkay.setOnClickListener() {
            observerImportDatabase.selectImportFile(binding)
        }
    }


        //the context is passed from the caller in the class 'ImportDatabase'
        fun importFinishedMessage(
            context: Context,
            importSuccess: Boolean,
            lineCountSuccess: Int,
            lineCountError: Int,
            binding: FragmentDialogImportDatabaseBinding
        ) {


            var dialogMessage: String = ""

            if (importSuccess) {
                try {

                    dialogMessage =
                        "<h2><br><br>Report</h2><br>Successfully imported Lines: $lineCountSuccess<br>Lines with import errors: $lineCountError"

                    binding.importTvDescription.text = Html.fromHtml(dialogMessage)
                    Log.i("Debug_A", "$dialogMessage")

                    binding.importBtnOkay.visibility = GONE

                } catch (e: Exception) {
                    Log.i("Debug_A", "Exception: $e")
                    dialogMessage = "Import Failed\nError: $e"
                    binding.importTvDescription.text = dialogMessage
                }

            } else {
                dialogMessage = "Import failed!"
                binding.importTvDescription.text = dialogMessage
            }

        }

}

    


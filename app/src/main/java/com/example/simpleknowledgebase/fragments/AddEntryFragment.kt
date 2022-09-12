package com.example.simpleknowledgebase.fragments

import android.os.Bundle
import android.renderscript.ScriptGroup
import android.text.InputFilter
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.simpleknowledgebase.Entry
import com.example.simpleknowledgebase.R
import com.example.simpleknowledgebase.databinding.FragmentAddEntryBinding
import com.example.simpleknowledgebase.viewmodels.AddEntryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime


class AddEntryFragment() : Fragment() {

    // '_binding' provides a nullable binding-version for usage in onCreateView() and onDestroyView()
    private var _binding: FragmentAddEntryBinding? = null
    // using the operator '!!' on '_binding' provides a non-nullable second binding-version
    private val binding get() = _binding!!
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private lateinit var addEntryViewModel: AddEntryViewModel

    private var inputFilterLastCheckedCharacter = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        addEntryViewModel = ViewModelProvider(this).get(AddEntryViewModel::class.java)


        _binding = FragmentAddEntryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addentryBtnAdd.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                insertEntry()
            }
        })


        //implementation of the interface InputFilter
        val characterExcludingFilter =
            InputFilter { source, start, end, dest, dstart, dend ->
                for (i in start until end) {
                    Log.d("Debug_A", "AddEntryFragment-Filter: " + source[i].toString())
                    //check if the current input character is a semicolon
                    if (source[i].toString() == ";") {
                        //check if there is an escape character '/' in front of the semicolon.
                        //If yes, the input is fine, if no the input has to be blocked
                        if(inputFilterLastCheckedCharacter != "/") {
                            val toast =
                                Toast.makeText(
                                    context,
                                    "Please use '/' to escape a semicolon: /;",
                                    Toast.LENGTH_LONG
                                )
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                            return@InputFilter ""
                        }
                    }
                    inputFilterLastCheckedCharacter = source[i].toString()
                }
                null
            }
        //The method setFilters() of class Editable takes an array of InputFilters that will be called one after another whenever text of the Editable is changed
        //Editable.setFilters([filter1:InputFilter,filter2:InputFilter,filter3:InputFilter])
        binding.addentryActvAddTitle.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(50),characterExcludingFilter)) //alternative syntax
        binding.addentryActvAddCategory.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(20),characterExcludingFilter)
        binding.addentryActvAddDescription.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(500),characterExcludingFilter)
        binding.addentryActvAddSource.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(400),characterExcludingFilter)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    fun insertEntry(){

        val id: Int = 0
        val date: String = LocalDateTime.now().toString()
        val title: String = binding.addentryActvAddTitle.text.toString()
        val category: String = binding.addentryActvAddCategory.text.toString()
        val description: String = binding.addentryActvAddDescription.text.toString()
        val source: String = binding.addentryActvAddSource.text.toString()

        coroutineScope.launch() {

            var entry = Entry(id,date,title,category,description,source)
            addEntryViewModel.insertEntry(entry)
            findNavController().navigate(R.id.action_nav_add_entry_to_nav_home)
            Toast.makeText(context, "Entry added", Toast.LENGTH_LONG).show()
        }
    }


}
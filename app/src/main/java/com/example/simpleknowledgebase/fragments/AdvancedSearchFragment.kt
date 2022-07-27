package com.example.simpleknowledgebase.fragments


import android.os.Bundle
import android.text.format.DateFormat.format
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.simpleknowledgebase.Entry
import com.example.simpleknowledgebase.databinding.FragmentAdvancedSearchBinding
import com.example.simpleknowledgebase.viewmodels.AdvancedSearchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.String.format
import java.text.SimpleDateFormat
import java.util.*


class AdvancedSearchFragment : Fragment() {

    private lateinit var advancedSearchViewModel: AdvancedSearchViewModel
    private var _binding: FragmentAdvancedSearchBinding? = null

    //1b + 1c
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        advancedSearchViewModel =
            ViewModelProvider(this).get(AdvancedSearchViewModel::class.java)

        _binding = FragmentAdvancedSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //checkbox clicks
        binding.advancedSearchRbDateSearch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.advancedSearchTvDateStart.visibility = View.VISIBLE
                binding.advancedSearchTvDateEnd.visibility = View.VISIBLE
                binding.ContextSearchPickerDateFrom.visibility = View.VISIBLE
                binding.ContextSearchPickerDateTo.visibility = View.VISIBLE
            }
        }
        binding.advancedSearchRbByTextField.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Toast.makeText(context, "TBD", Toast.LENGTH_SHORT).show()
            }
        }

        // search button click

        //var checkedRadioButtonId: Int = binding.advancedSearchRgSearchPick.checkedRadioButtonId
        binding.advancedSearchBtnSearch.setOnClickListener { view ->

            if(binding.advancedSearchRbDateSearch.isChecked){
                Toast.makeText(context, "advancedSearchRbDateSearch", Toast.LENGTH_SHORT).show()

                //remove date input related fields
                binding.advancedSearchTvDateStart.visibility = View.GONE
                binding.advancedSearchTvDateEnd.visibility = View.GONE
                binding.ContextSearchPickerDateFrom.visibility = View.GONE
                binding.ContextSearchPickerDateTo.visibility = View.GONE


                var dateFromCalender: Calendar = Calendar.getInstance()
                var dateToCalender: Calendar = Calendar.getInstance()

                //save date picks in calender class object
                dateFromCalender.set(binding.ContextSearchPickerDateFrom.year,
                    binding.ContextSearchPickerDateFrom.month,
                    binding.ContextSearchPickerDateFrom.dayOfMonth
                )
                dateToCalender.set(binding.ContextSearchPickerDateTo.year,
                    binding.ContextSearchPickerDateTo.month,
                    binding.ContextSearchPickerDateTo.dayOfMonth
                )

                // format calender objects to preferred date string
                var dateFormat = SimpleDateFormat("yyyy-MM-dd")
                var dateFromCalenderFormatted = dateFormat.format(dateFromCalender.time)
                var dateToCalenderFormatted = dateFormat.format(dateToCalender.time)


                coroutineScope.launch() {
                    advancedSearchViewModel.findEntriesOfDateTimeSpan(
                        dateFromCalenderFormatted,
                        dateToCalenderFormatted
                    )
                }
            }
            else if(binding.advancedSearchRbByTextField.isChecked){
                Toast.makeText(context, "TBD", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(context, "Please choose a search variant", Toast.LENGTH_SHORT).show()
            }

        }


        //clear button
        binding.advancedSearchBtnClear.setOnClickListener { view ->

            //clear radio group
            binding.advancedSearchRgSearchPick.clearCheck()

            //remove date input related fields
            binding.advancedSearchTvDateStart.visibility = View.GONE
            binding.advancedSearchTvDateEnd.visibility = View.GONE
            binding.ContextSearchPickerDateFrom.visibility = View.GONE
            binding.ContextSearchPickerDateTo.visibility = View.GONE
        }

    }


    // receive entries from dao
    fun returnEntriesOfDateTimeSpan(searchResultsDate: List<Entry>?) {
        Log.i("Debug_A", "EntryFragment: "+searchResultsDate.toString())
    }


}


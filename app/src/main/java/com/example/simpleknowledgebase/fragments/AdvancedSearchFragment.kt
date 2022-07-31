package com.example.simpleknowledgebase.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleknowledgebase.Entry
import com.example.simpleknowledgebase.R
import com.example.simpleknowledgebase.adapters.EntryRecyclerViewAdapter
import com.example.simpleknowledgebase.databinding.FragmentAdvancedSearchBinding
import com.example.simpleknowledgebase.viewmodels.AdvancedSearchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class AdvancedSearchFragment : Fragment() {

    private lateinit var advancedSearchViewModel: AdvancedSearchViewModel
    private var _binding: FragmentAdvancedSearchBinding? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private lateinit var entryRecyclerViewAdapter: EntryRecyclerViewAdapter
    private lateinit var recyclerView: RecyclerView

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

        // get the recycler view item from its fragment.xml file
        recyclerView = binding.advancedSearchRv

        //checkbox clicks
        binding.advancedSearchRbDateSearch.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                binding.advancedSearchTvDateStart.visibility = View.VISIBLE
                binding.advancedSearchTvDateEnd.visibility = View.VISIBLE
                binding.ContextSearchPickerDateFrom.visibility = View.VISIBLE
                binding.ContextSearchPickerDateTo.visibility = View.VISIBLE
            }
            else {
                binding.advancedSearchTvDateStart.visibility = View.GONE
                binding.advancedSearchTvDateEnd.visibility = View.GONE
                binding.ContextSearchPickerDateFrom.visibility = View.GONE
                binding.ContextSearchPickerDateTo.visibility = View.GONE
            }
        }

        binding.advancedSearchRbByTextField.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                binding.advancedSearchSpnSearchbyTextField.visibility = View.VISIBLE
                binding.advancedSearchEtSearch.visibility = View.VISIBLE
            }
            else {
                binding.advancedSearchSpnSearchbyTextField.visibility = View.GONE
                binding.advancedSearchEtSearch.visibility = View.GONE
            }
        }

        //spinner field
        val spinner: Spinner = binding.advancedSearchSpnSearchbyTextField
        val adapterArray = ArrayAdapter(
            activity!!,
            android.R.layout.simple_spinner_item, resources
                .getStringArray(R.array.advancedSearch_spinner_SearchbyTextField)
        )
        adapterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapterArray



        fun searchQuery(){

            if (binding.advancedSearchRbDateSearch.isChecked) {

                //remove date input related fields
                binding.advancedSearchTvDateStart.visibility = View.GONE
                binding.advancedSearchTvDateEnd.visibility = View.GONE
                binding.ContextSearchPickerDateFrom.visibility = View.GONE
                binding.ContextSearchPickerDateTo.visibility = View.GONE

                var dateFromCalender: Calendar = Calendar.getInstance()
                var dateToCalender: Calendar = Calendar.getInstance()

                //save date picks in calender class object
                dateFromCalender.set(
                    binding.ContextSearchPickerDateFrom.year,
                    binding.ContextSearchPickerDateFrom.month,
                    binding.ContextSearchPickerDateFrom.dayOfMonth
                )
                dateToCalender.set(
                    binding.ContextSearchPickerDateTo.year,
                    binding.ContextSearchPickerDateTo.month,
                    binding.ContextSearchPickerDateTo.dayOfMonth
                )

                // format calender objects to preferred date string
                var dateFormat = SimpleDateFormat("yyyy-MM-dd")
                var dateFromCalenderFormatted = dateFormat.format(dateFromCalender.time)
                var dateToCalenderFormatted = dateFormat.format(dateToCalender.time)

                // start path to DAO
                coroutineScope.launch() {
                    advancedSearchViewModel.findEntriesOfDateTimeSpan(
                        dateFromCalenderFormatted,
                        dateToCalenderFormatted
                    )
                }
            } else if (binding.advancedSearchRbByTextField.isChecked) {

                var keyword: String = binding.advancedSearchEtSearch.text.toString()

                if(spinner.selectedItem == "Title"){
                    advancedSearchViewModel.findAdvancedSearchField("title", keyword)
                }
                else if(spinner.selectedItem == "Description") {
                    advancedSearchViewModel.findAdvancedSearchField("description", keyword)
                }
                else if(spinner.selectedItem == "Source-URL") {
                    advancedSearchViewModel.findAdvancedSearchField("source", keyword)
                }
            } else {
                Toast.makeText(context, "Please choose a search variant", Toast.LENGTH_SHORT).show()
            }
        }
        // search button click: search query
        binding.advancedSearchBtnSearch.setOnClickListener { view ->
            searchQuery()
        }
        // search field ClickListener (pressing enter): search query
        binding.advancedSearchEtSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            searchQuery()
            true
        })


        fun clear_data(){
            //clear radio group
            binding.advancedSearchRgSearchPick.clearCheck()

            //remove date input related fields
            binding.advancedSearchTvDateStart.visibility = View.GONE
            binding.advancedSearchTvDateEnd.visibility = View.GONE
            binding.ContextSearchPickerDateFrom.visibility = View.GONE
            binding.ContextSearchPickerDateTo.visibility = View.GONE

            //remove text field related fields
            binding.advancedSearchSpnSearchbyTextField.visibility = View.GONE
            binding.advancedSearchEtSearch.visibility = View.GONE

            //clear current recycler view data
            entryRecyclerViewAdapter = EntryRecyclerViewAdapter(requireContext(),listOf())
            recyclerView.adapter = entryRecyclerViewAdapter

            //clear EditText field
            binding.advancedSearchEtSearch.text.clear()

            //remove number of Hits
            binding.advancedSearchTvNumberOfHits.setVisibility(View.GONE)
        }


        //clear button of ui
        binding.advancedSearchBtnClear.setOnClickListener { view ->
            clear_data()
        }


        fun observerOnChanged(searchResults: List<Entry>){
            //create the adapter by handing the data entryList to it
            entryRecyclerViewAdapter = EntryRecyclerViewAdapter(requireContext(),searchResults)
            // get a LayoutManager. There's no layoutManager for ConstraintLayout available. That's why LinerLayoutManager is used
            val layoutManager = LinearLayoutManager(context)
            // Calling RecyclerView's method 'SetLayoutManager()
            recyclerView.layoutManager = layoutManager
            //Calling RecyclerView's method 'setAdapter()' and passing the adapter to it
            recyclerView.adapter = entryRecyclerViewAdapter
            // Callback that is used for Clicks on RecyclerView Items
            entryRecyclerViewAdapter.setOnEntryItemClickListener(object: EntryRecyclerViewAdapter.onItemClickListener {
                override fun onItemClick(position: Int,entry: Entry) {
                    // the function 'to' combines its left and right side into a Pair
                    var bundle: Bundle = bundleOf("entryFromKeywordSearchFragment" to entry)
                    findNavController().navigate(R.id.action_nav_home_to_updateDeleteEntryFragment,bundle)
                }
            })

            //output number of entry hits
            if (searchResults.size > 0) {
                binding.advancedSearchTvNumberOfHits.setVisibility(View.VISIBLE)
                binding.advancedSearchTvNumberOfHits.text = searchResults.size.toString()
            } else {
                binding.advancedSearchTvNumberOfHits.text = "0"
            }
        }
        // Live Data Observer for Input 'Date Time Span': Set entries to RecyclerView
        advancedSearchViewModel.getDateTimeSpanLiveData().observe(viewLifecycleOwner,object: Observer<List<Entry>> {

            override fun onChanged(searchResultsDate: List<Entry>) {
                observerOnChanged(searchResultsDate)
            }
        })
        // Live Data Observer for Input 'Date Time Span': Set entries to RecyclerView
        advancedSearchViewModel.getAdvancedSearchFieldLiveData().observe(viewLifecycleOwner,object: Observer<List<Entry>> {

            override fun onChanged(searchResultsAdvancedSearchField: List<Entry>) {
                observerOnChanged(searchResultsAdvancedSearchField)
            }
        })


    }


}


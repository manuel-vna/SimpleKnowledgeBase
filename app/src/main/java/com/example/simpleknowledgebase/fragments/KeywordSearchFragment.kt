package com.example.simpleknowledgebase.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.simpleknowledgebase.databinding.FragmentKeywordSearchBinding
import com.example.simpleknowledgebase.utils.HelperFunctions.Companion.hideKeyboard
import com.example.simpleknowledgebase.viewmodels.KeywordSearchViewModel


class KeywordSearchFragment : Fragment() {

    private lateinit var keywordSearchViewModel: KeywordSearchViewModel
    private var _binding: FragmentKeywordSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var entryRecyclerViewAdapter: EntryRecyclerViewAdapter
    private lateinit var searchResults: List<Entry>
    private lateinit var categoryFromCategoryOverviewFragment: String
    private lateinit var previousFragment: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        keywordSearchViewModel =
            ViewModelProvider(this).get(KeywordSearchViewModel::class.java)

        _binding = FragmentKeywordSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // retrieve the previous fragment from the BackStack
        val previousFragment = findNavController().previousBackStackEntry?.destination?.label

        // CategoryOverviewFragment implementation: Get category from navigation bundle:
        // if the previousFragment was CategoryOverviewFragment AND an argument is delivered via the fragment transition
        if (previousFragment ==  "CategoryOverviewFragment" && arguments?.get("categoryFromCategoryOverviewFragment") != null) {
            //receive category from fragment
            categoryFromCategoryOverviewFragment = arguments?.get("categoryFromCategoryOverviewFragment") as String
        }
        else {
            categoryFromCategoryOverviewFragment = ""
        }


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //displays entries by category that were chosen in the CategoryOverviewFragment
        if (categoryFromCategoryOverviewFragment.isNotEmpty()){
            keywordSearchViewModel.findCategory(categoryFromCategoryOverviewFragment)
        }

        // Button: Search
        binding.keywordBtnSearch.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                keywordSearchViewModel.findKeyword(binding.keywordEtSearch.text.toString())

                // calling extension function hideKeyboard() of class fragment which is defined in class: util.HelperFunctions
                hideKeyboard()
            }
        })

        // Live Data Observer: Keyword Search
        keywordSearchViewModel.getKeywordLiveData().observe(viewLifecycleOwner,object: Observer<List<Entry>> {

            override fun onChanged(entries: List<Entry>) {

                // get the recycler view item form its fragment.xml file
                val recyclerView: RecyclerView = binding.keywordRvDisplayResults
                //create the adapter by handing the data entryList to it
                entryRecyclerViewAdapter = EntryRecyclerViewAdapter(requireContext(),entries)
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
            }
        })

        // Live Data Observer: Category Search triggered from CategoryOverviewFragment
        keywordSearchViewModel.getCategoryLiveData().observe(viewLifecycleOwner,object: Observer<List<Entry>> {

            override fun onChanged(entries: List<Entry>) {

                // get the recycler view item form its fragment.xml file
                val recyclerView: RecyclerView = binding.keywordRvDisplayResults
                //create the adapter by handing the data entryList to it
                entryRecyclerViewAdapter = EntryRecyclerViewAdapter(requireContext(),entries)
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
            }
        })

    }

}









package com.example.simpleknowledgebase.fragments

import android.os.Bundle
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
import com.example.simpleknowledgebase.adapters.CategoryRecyclerViewAdapter
import com.example.simpleknowledgebase.adapters.EntryRecyclerViewAdapter
import com.example.simpleknowledgebase.databinding.FragmentCategoryOverviewBinding
import com.example.simpleknowledgebase.utils.CategoryRecyclerViewItemDecoration
import com.example.simpleknowledgebase.viewmodels.CategoryOverviewViewModel


class CategoryOverviewFragment: Fragment() {

    private lateinit var categoryOverviewViewModel: CategoryOverviewViewModel
    private var _binding: FragmentCategoryOverviewBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoryRecyclerViewAdapter: CategoryRecyclerViewAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        categoryOverviewViewModel =
            ViewModelProvider(this).get(CategoryOverviewViewModel::class.java)

        _binding = FragmentCategoryOverviewBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // call dao to retrieve the list of all category names save din the db
        categoryOverviewViewModel.findAllCategories()


        // Live Data Observer that observers the findAllCategories() process
        categoryOverviewViewModel.getCategoryLiveData().observe(viewLifecycleOwner,object:
            Observer<List<String>> {

            override fun onChanged(categories: List<String>) {

                // get the recycler view item form its fragment.xml file
                val recyclerView: RecyclerView = binding.categoryOverviewRvDisplayCategories
                //create the adapter by handing the data entryList to it
                categoryRecyclerViewAdapter = CategoryRecyclerViewAdapter(requireContext(),categories)
                // get a LayoutManager. There's no layoutManager for ConstraintLayout available. That's why LinerLayoutManager is used
                val layoutManager = LinearLayoutManager(context)
                // Calling RecyclerView's method 'SetLayoutManager()
                recyclerView.layoutManager = layoutManager

                //#
                recyclerView.addItemDecoration(CategoryRecyclerViewItemDecoration(64))
                //#

                //Calling RecyclerView's method 'setAdapter()' and passing the adapter to it
                recyclerView.adapter = categoryRecyclerViewAdapter

                // Callback that is used for Clicks on RecyclerView Items
                categoryRecyclerViewAdapter.setOnCategoryItemClickListener(object: CategoryRecyclerViewAdapter.onItemClickListener {
                    override fun onItemClick(position: Int,category: String) {
                        //var bundle: Bundle = bundleOf("entryFromKeywordSearchFragment" to category)
                        //findNavController().navigate(R.id.action_nav_home_to_updateDeleteEntryFragment,bundle)
                        Toast.makeText(context, "TBD", Toast.LENGTH_LONG).show()
                    }
                })



            }
        })

    }




}
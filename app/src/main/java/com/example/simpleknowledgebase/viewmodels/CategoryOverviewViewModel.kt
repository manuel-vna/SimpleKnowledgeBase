package com.example.simpleknowledgebase.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simpleknowledgebase.Entry
import com.example.simpleknowledgebase.EntryDatabase
import com.example.simpleknowledgebase.repositories.EntryRepository



class CategoryOverviewViewModel(application: Application): AndroidViewModel(application) {

    private val entryRepository: EntryRepository
    private val searchResultsLivedata: MutableLiveData<List<String>>

    init {
        val entryDao = EntryDatabase.getInstance(application).entryDao()
        entryRepository = EntryRepository(entryDao)
        searchResultsLivedata = entryRepository.searchResultsAllCategories
    }


    fun findAllCategories() {
        entryRepository.findAllCategories()
    }
    fun getAllCategoriesLiveData(): LiveData<List<String>> {
        return searchResultsLivedata
    }

}
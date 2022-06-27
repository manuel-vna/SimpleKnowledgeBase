package com.example.simpleknowledgebase.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.simpleknowledgebase.Entry
import com.example.simpleknowledgebase.EntryDatabase
import com.example.simpleknowledgebase.repositories.EntryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class KeywordSearchViewModel(application: Application): AndroidViewModel(application) {

    private val entryRepository: EntryRepository
    private val searchResultsLivedata: MutableLiveData<List<Entry>>

    init {
        val entryDao = EntryDatabase.getInstance(application).entryDao()
        entryRepository = EntryRepository(entryDao)
        searchResultsLivedata = entryRepository.searchResults
    }


    fun findKeyword(keyword: String) {
            entryRepository.findKeyword(keyword)

    }

    fun getKeywordLiveData(): LiveData<List<Entry>> {
        return searchResultsLivedata
    }


}
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
    private val keywordSearchResultsLivedata: MutableLiveData<List<Entry>>
    private val categorySearchResultsLivedata: MutableLiveData<List<Entry>>


    init {
        val entryDao = EntryDatabase.getInstance(application).entryDao()
        entryRepository = EntryRepository(entryDao)
        keywordSearchResultsLivedata = entryRepository.searchResults
        categorySearchResultsLivedata = entryRepository.searchResultsCategory
    }


    fun findKeyword(keyword: String) {
            entryRepository.findKeyword(keyword)
    }
    fun getKeywordLiveData(): LiveData<List<Entry>> {
        return keywordSearchResultsLivedata
    }

    fun findCategory(keyword: String) {
        entryRepository.findCategory(keyword)
    }
    fun getCategoryLiveData(): LiveData<List<Entry>> {
        return categorySearchResultsLivedata
    }


}
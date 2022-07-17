package com.example.simpleknowledgebase.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simpleknowledgebase.Entry
import com.example.simpleknowledgebase.EntryDatabase
import com.example.simpleknowledgebase.repositories.EntryRepository



class MainActivityViewModel(application: Application): AndroidViewModel(application) {


    private val entryRepository: EntryRepository
    private val rowCountSearchResultsLivedata: LiveData<Int>


    init {
        val entryDao = EntryDatabase.getInstance(application).entryDao()
        entryRepository = EntryRepository(entryDao)
        rowCountSearchResultsLivedata = entryRepository.searchResultsRowNumber

    }


    fun observeTotalRowNumberLiveData(): LiveData<Int> {
        return rowCountSearchResultsLivedata
    }

}

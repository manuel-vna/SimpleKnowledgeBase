package com.example.simpleknowledgebase.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.simpleknowledgebase.Entry
import com.example.simpleknowledgebase.EntryDatabase
import com.example.simpleknowledgebase.repositories.EntryRepository

//approach without using a Live<Data> wrapper

class AdvancedSearchViewModel(application: Application): AndroidViewModel(application) {

    private val entryRepository: EntryRepository
    private  var searchResultsDate: List<Entry>? = null


    init {
        val entryDao = EntryDatabase.getInstance(application).entryDao()
        entryRepository = EntryRepository(entryDao)
        searchResultsDate = entryRepository.searchResultsDate
    }

/*
    fun findEntriesOfDateTimespan(dateFrom: String, dateTo: String) {
        entryRepository.findEntriesOfDateTimespan(dateFrom, dateTo)
    }
    fun getEntriesOfDateTimespan(): List<Entry>? {
        return searchResultsDate
    }

 */

}


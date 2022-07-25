package com.example.simpleknowledgebase.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.simpleknowledgebase.Entry
import com.example.simpleknowledgebase.EntryDatabase
import com.example.simpleknowledgebase.repositories.EntryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//approach without using a Live<Data> wrapper

class AdvancedSearchViewModel(application: Application): AndroidViewModel(application) {

    private val entryRepository: EntryRepository

    private var searchResultsDate: List<Entry>? = null
    //private lateinit var searchResultsDate: List<Entry>

    private val coroutineScope = CoroutineScope(Dispatchers.IO)


    init {
        val entryDao = EntryDatabase.getInstance(application).entryDao()
        entryRepository = EntryRepository(entryDao)
    }


    fun findEntriesOfDateTimeSpan(dateFrom: String, dateTo: String) : List<Entry>? {
        coroutineScope.launch {
            //async-await approach:
            searchResultsDate = entryRepository.dateFindAsync(dateFrom, dateTo).await()

            // withContext() approach:
            //searchResultsDate = entryRepository.dateFindAsync(dateFrom, dateTo)
        }
        return searchResultsDate
    }



}


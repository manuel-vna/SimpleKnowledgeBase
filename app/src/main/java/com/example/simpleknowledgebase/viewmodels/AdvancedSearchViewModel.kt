package com.example.simpleknowledgebase.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simpleknowledgebase.Entry
import com.example.simpleknowledgebase.EntryDao
import com.example.simpleknowledgebase.EntryDatabase
import com.example.simpleknowledgebase.fragments.AdvancedSearchFragment
import com.example.simpleknowledgebase.repositories.EntryRepository
import kotlinx.coroutines.*

// approach without LiveData observation, instead:
// Fragment<-ViewModel: static method access ; ViewModel<->Repository: high-order function ; Repository<->Dao: coruoutine with suspend function

class AdvancedSearchViewModel(application: Application): AndroidViewModel(application) {

    private val entryRepository: EntryRepository
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val searchResultsDateLiveData = MutableLiveData<List<Entry>>()


    init {
        val entryDao = EntryDatabase.getInstance(application).entryDao()
        entryRepository = EntryRepository(entryDao)
    }


    fun findEntriesOfDateTimeSpan(dateFrom: String, dateTo: String) {
        coroutineScope.launch {
            entryRepository.findEntriesOfDateTimeSpan(
                dateFrom,
                dateTo,
                ::returnEntriesOfDateTimeSpan
            )
        }
    }

    fun returnEntriesOfDateTimeSpan(searchResultsDate: List<Entry>) {
        Log.i("Debug_A", "EntryViewModel: " + searchResultsDate.toString())
        searchResultsDateLiveData.setValue(searchResultsDate)
    }

    fun getDateTimeSpanLiveData(): LiveData<List<Entry>> {
        return searchResultsDateLiveData
    }


}


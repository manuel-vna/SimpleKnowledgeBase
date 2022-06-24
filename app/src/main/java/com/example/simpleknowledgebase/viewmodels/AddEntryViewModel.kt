package com.example.simpleknowledgebase.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpleknowledgebase.Entry
import com.example.simpleknowledgebase.EntryDatabase
import com.example.simpleknowledgebase.repositories.EntryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddEntryViewModel (application: Application): AndroidViewModel(application) {

    private val repository: EntryRepository

    init {
        val entryDao = EntryDatabase.getInstance(application).entryDao()
        repository = EntryRepository(entryDao)
    }

    fun insertEntry(entry: Entry) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertEntry(entry)
        }
    }




    /*
    private val _text = MutableLiveData<String>().apply {
        value = "This is AddEntryFragment"
    }
    val text: LiveData<String> = _text
    */

}
package com.example.simpleknowledgebase.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.simpleknowledgebase.Entry
import com.example.simpleknowledgebase.EntryDatabase
import com.example.simpleknowledgebase.repositories.EntryRepository

class UpdateDeleteViewModel(application: Application): AndroidViewModel(application) {

    private val entryRepository: EntryRepository

    init {
        val entryDao = EntryDatabase.getInstance(application).entryDao()
        entryRepository = EntryRepository(entryDao)
    }

    fun updateEntry(entry: Entry) {
        entryRepository.updateEntry(entry)
    }

}
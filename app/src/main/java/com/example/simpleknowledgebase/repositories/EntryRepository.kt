package com.example.simpleknowledgebase.repositories

import com.example.simpleknowledgebase.EntryDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EntryRepository(private val entryDao: EntryDao) {


    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    fun findKeyword(keyword: String){
        coroutineScope.launch(Dispatchers.IO) {
            entryDao.findKeyword(keyword)
        }

    }

}
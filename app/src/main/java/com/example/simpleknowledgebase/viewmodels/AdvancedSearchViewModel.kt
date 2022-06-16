package com.example.simpleknowledgebase.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AdvancedSearchViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is the AdvancedSearchFragment"
    }
    val text: LiveData<String> = _text
}
package com.exeapp.checheritox.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CodigosViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Aca van los Códigos"
    }
    val text: LiveData<String> = _text
}
package com.example.mf.experiments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataVM : ViewModel(){
    private val store = MutableLiveData<String>()
    val data : LiveData<String>
            get() = store
    init {
        store.value = "Hello"
    }
    fun changeValue(msg: String){
        store.postValue(msg)
    }
}
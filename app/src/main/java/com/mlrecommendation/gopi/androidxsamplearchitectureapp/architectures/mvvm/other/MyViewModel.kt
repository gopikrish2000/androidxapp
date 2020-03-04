package com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm.other

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    private val _liveData1 = MutableLiveData<String>()
    val liveData1: LiveData<String> = _liveData1
    val liveData2 = Transformations.map(_liveData1) { it.toUpperCase() }

    init {
        _liveData1.value = "Default"
    }

    fun setNewValue(newValue: String) {
        _liveData1.value = newValue        
    }

    /*fun testLiveDataEmitting() {
        val viewModel = MyViewModel()
        viewModel.setNewValue("foo")
        assertEquals(viewModel.liveData1.value, "foo") // Passes
        assertEquals(viewModel.liveData2.value, "FOO") // Fails!
    }*/
}
package com.mlrecommendation.gopi.androidxsamplearchitectureapp.databinding

import android.view.View
import android.widget.Toast
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class DataBindingVM : ViewModel() {

    val firstLiveData: MutableLiveData<String> = MutableLiveData()

    val staticData:String = "My Static Data"
    val editTextLiveData: MutableLiveData<String> = MutableLiveData()

    val editTextVisibilityLiveData: MutableLiveData<Boolean> = MutableLiveData()

    val tempIntLiveData = Transformations.switchMap(firstLiveData){va -> MutableLiveData<Int>().apply { value = if(va == null) -1 else va.length }}

    val mediatorLiveData = MediatorLiveData<String>()


    init {
        firstLiveData.value  = null
        editTextVisibilityLiveData.value = true
        mediatorLiveData.addSource(firstLiveData) {
            mediatorLiveData.value = it
        }
        mediatorLiveData.addSource(tempIntLiveData) {
            mediatorLiveData.value = it.toString()
        }
    }

    fun onButtonClicked(button: View) {
        Toast.makeText(button.context, "OnButtonClicked",Toast.LENGTH_SHORT).show()
    }


}
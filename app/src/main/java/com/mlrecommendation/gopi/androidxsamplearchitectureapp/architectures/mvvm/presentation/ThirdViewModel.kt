package com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Gopi Krishna on 2020-01-16.
 */
class ThirdViewModel : ViewModel() {

    private val _nameSavedLiveData = MutableLiveData<Boolean>()
    val nameSavedLiveData:LiveData<Boolean> = _nameSavedLiveData

    fun saveUserEnteredName(userName:String){
        // make network call & get response
        _nameSavedLiveData.postValue(true) // true when API updation is successful
    }
}

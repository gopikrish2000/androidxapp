package com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm.eventAppearOnce.Event

/**
 * Created by Gopi Krishna on 2020-01-16.
 */
class FourthViewModel : ViewModel() {

    private val _nameSavedLiveData = MutableLiveData<Event<Boolean>>()
    val nameSavedLiveData:LiveData<Event<Boolean>> = _nameSavedLiveData

    fun saveUserEnteredName(userName:String){
        // make network call & get response
        _nameSavedLiveData.postValue(Event(true)) // true when API updation is successful
    }
}

package com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by Gopi Krishna on 2020-01-16.
 */
class SecondViewModel : ViewModel() {

    private val _nameLiveData: MutableLiveData<String> by lazy {
        val mutableLiveData = MutableLiveData<String>()
        fetchNameFromServer()
        mutableLiveData
    }
    val nameLiveData:LiveData<String> = _nameLiveData

    init {
        fetchNameFromServer()
    }

    fun fetchNameFromServer(){ // This method is called only once
        // make network call onresponse of it set livedata
//        _nameLiveData.value = networkresponse
    }
}

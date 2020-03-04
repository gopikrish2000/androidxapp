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
class FirstViewModel : ViewModel() {

    private val _nameLiveData = MutableLiveData<String>()
    val nameLiveData:LiveData<String> = _nameLiveData
    val uppercaseName = Transformations.map(nameLiveData){ it.toUpperCase()}
    val studentName = Transformations.switchMap(nameLiveData){name -> // can do async operations with switchMap
        val mutableLiveData = MutableLiveData<Student>()
        Observable.just(1).delay(5,TimeUnit.SECONDS).subscribe { mutableLiveData.postValue(Student(name))  }
        return@switchMap mutableLiveData
    }

    init {
        _nameLiveData.value = "default"
    }

    fun onButtonClick(){
        _nameLiveData.value = ThreadUtils.randomNumber().toString()
    }
}

data class Student(val name:String)
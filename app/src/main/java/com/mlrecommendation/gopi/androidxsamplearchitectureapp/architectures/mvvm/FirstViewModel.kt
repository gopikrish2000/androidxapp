package com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm.eventAppearOnce.Event
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm.repository.MvvmRepository
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

/**
 * Created by Gopi Krishna on 2020-01-13.
 */
class FirstViewModel : ViewModel() {
    val firstLiveData = MutableLiveData<String>()
    val transformMapLiveData =
        Transformations.map(firstLiveData) { it.toLowerCase().substring(1) } //get new livedata of changed type.
    val transformSwitchMapLiveData = Transformations.switchMap(firstLiveData) {
        // Use this when returning asynchronous operation Or just return different number of items
        val mutableLiveData = MutableLiveData<Int>()
        mutableLiveData.value = it.length
        mutableLiveData
    }
    val mediatorLiveData =
        MediatorLiveData<String>() // when u have 2 different livedata & u want to listen to both use mediator.
    val oneMoreLiveData = MutableLiveData<String>()
    val compositeDisposable = CompositeDisposable()

    val activityChangeLiveData = MutableLiveData<String>()

    val eventUsecaseLiveData = MutableLiveData<Event<String>>()


    init {
        firstLiveData.value = "default"
        oneMoreLiveData.value = "defaultVal"
        println("FirstViewModel.init called")
        mediatorLiveData.addSource(firstLiveData) { mediatorLiveData.value = mergeSources() }
        mediatorLiveData.addSource(oneMoreLiveData) { mediatorLiveData.value = mergeSources() }
    }

    fun mergeSources(): String {
        return firstLiveData.value + " other- " + oneMoreLiveData.value
    }

    fun generateVal() {
        compositeDisposable.add(
            MvvmRepository.fetchData(
                ThreadUtils.randomNumberInRange(
                    1,
                    3
                )
            ).subscribe({ firstLiveData.postValue(it) }, {}, {})
        )
    }

    fun generateOtherVal() {
        oneMoreLiveData.value = ThreadUtils.randomNumber().toString()
    }

    fun generateOtherValWithActChange() {
        activityChangeLiveData.setValue(ThreadUtils.randomNumber().toString())
        Observable.create<String> { it.onNext("ww")
        }.delay(5, TimeUnit.SECONDS).map { it }.subscribeOn(Schedulers.io()).subscribe({
            activityChangeLiveData.postValue(ThreadUtils.randomNumber().toString())
        })
    }

    fun onEventTestButtonClick(){
        eventUsecaseLiveData.value = Event(ThreadUtils.randomNumber().toString())
    }

    override fun onCleared() {
        super.onCleared()
        println("FirstViewModel.onCleared")
        compositeDisposable.clear()
    }
}
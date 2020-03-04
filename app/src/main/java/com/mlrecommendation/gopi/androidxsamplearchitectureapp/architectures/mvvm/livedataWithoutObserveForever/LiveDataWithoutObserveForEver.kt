package com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm.livedataWithoutObserveForever

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm.other.Contacts
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by Gopi Krishna on 2020-01-15.
 */
class LiveDataWithoutObserveForEver : ViewModel() {

    private val contactIdChange = MutableLiveData<String>()
    val contactsInfo = Transformations.switchMap(contactIdChange) { getNewContactFromId(it) }

    private fun getNewContactFromId(id: String): LiveData<Contacts> {
        val liveData = MutableLiveData<Contacts>()
        Observable.fromCallable {
            val contacts = Contacts(mutableListOf(id))
            return@fromCallable contacts
        }.delay(5, TimeUnit.SECONDS).subscribe {
            liveData.postValue(it)
        }
        return liveData
    }

    fun onContactButtonClick(id: String) {
        contactIdChange.value = id // instead of calling repository use switchmap which takes care of this.
    }
}
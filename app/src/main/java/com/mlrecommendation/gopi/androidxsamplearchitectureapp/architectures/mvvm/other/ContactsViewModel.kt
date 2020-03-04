package com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm.other

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class ContactsViewModel(private val getContactsUseCase: GetContactsUseCase, parameters: Parameters) : ViewModel() {

    val contactsLiveData: LiveData<Contacts> by lazy {
        println("ContactsViewModel.contactsLiveData LAZY initialization")
        val liveData = MutableLiveData<Contacts>()
        getContactsUseCase.loadContacts(parameters) { liveData.postValue(it) } // do it asynchronous.
        return@lazy liveData
    }
}

class ContactsViewModelFactory(val getContactsUseCase: GetContactsUseCase, val parameters: Parameters) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContactsViewModel(getContactsUseCase, parameters) as T
    } }

data class Contacts(val names: List<String>)
data class Parameters(val namePrefix: String = "")

class GetContactsUseCase {
    fun loadContacts(parameters: Parameters, load: (Contacts) -> Unit){
        Observable.just(1).delay(5,TimeUnit.SECONDS).subscribe {
            println("Loading contacts")
            load(Contacts(mutableListOf("1","2","3")))
        }
    } }
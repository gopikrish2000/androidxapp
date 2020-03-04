package com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm.livedataWithoutObserveForever.LiveDataWithoutObserveForEver
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm.other.ContactsViewModel
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm.other.ContactsViewModelFactory
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm.other.GetContactsUseCase
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm.other.Parameters
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.MyApplication
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.showToast
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.lifecycle.LifeCycleSecondActivity
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils
import kotlinx.android.synthetic.main.activity_mvvm_first.view.*

class MvvmFirstActivity : AppCompatActivity() {
    var firstViewModel: FirstViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("MvvmFirstActivity.onCreate")
        val binding= DataBindingUtil.setContentView<com.mlrecommendation.gopi.androidxsamplearchitectureapp.databinding.ActivityMvvmFirstBinding>(
                this, R.layout.activity_mvvm_first )
        firstViewModel = ViewModelProviders.of(this).get(FirstViewModel::class.java)
        binding.lifecycleOwner = this
        binding.vm = firstViewModel!!

        firstViewModel!!.activityChangeLiveData.observe(this, Observer { startActivity(Intent(this,LifeCycleSecondActivity::class.java)); println("activityChangeLiveData got called") })

        val contactsViewModel = ViewModelProviders.of(this, ContactsViewModelFactory(GetContactsUseCase(), Parameters()))
            .get(ContactsViewModel::class.java)
        contactsViewModel.contactsLiveData.observe(this, Observer { MyApplication.showToast("contacts observed") })

        val liveDataWithoutObserveForEverVM = ViewModelProviders.of(this).get(LiveDataWithoutObserveForEver::class.java)
        liveDataWithoutObserveForEverVM.contactsInfo.observe(this, Observer { MyApplication.showToast(it.toString()) })

        firstViewModel!!.eventUsecaseLiveData.observe(this, Observer { it.getContentIfNotHandled()?.let { MyApplication.showToast("Event value is ${it}") }})

        binding.root.livedataObserveBtn.setOnClickListener { firstViewModel!!.onEventTestButtonClick()
//            liveDataWithoutObserveForEverVM.onContactButtonClick(ThreadUtils.randomNumberInRange(1,6).toString())
        }
    }

    override fun onStart() {
        super.onStart()
        println("MvvmFirstActivity.onStart")
    }

    override fun onResume() {
        super.onResume()
        println("MvvmFirstActivity.onResume")
    }

    override fun onPause() {
        super.onPause()
        println("MvvmFirstActivity.onPause")
    }

    override fun onStop() {
        super.onStop()
        println("MvvmFirstActivity.onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        val activity = this
        println("MvvmFirstActivity.onDestroy with isFinishing ${activity.isFinishing}" )
    }
}

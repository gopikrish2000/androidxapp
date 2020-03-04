package com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.MyApplication
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.showToast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_vm_presentation.*
import java.util.concurrent.TimeUnit

class VmPresentationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vm_presentation)

//        viewmodelDemo()
    }

    private fun viewmodelDemo() {
        val thirdViewModel = ViewModelProviders.of(this).get(FourthViewModel::class.java)
        thirdViewModel.nameSavedLiveData.observe(
            this,
            Observer { event ->
                event.getContentIfNotHandled()?.let { if (it) MyApplication.showToast("save successful") }
            })

        presentationBtn.setOnClickListener { thirdViewModel.saveUserEnteredName("gopi") }


        val secondViewModel = ViewModelProviders.of(this).get(SecondViewModel::class.java)
        secondViewModel.fetchNameFromServer()

        val firstViewModel = ViewModelProviders.of(this).get(FirstViewModel::class.java)
        firstViewModel.nameLiveData.observe(this, Observer { name -> presentationTv.text = name + "kk" })
        firstViewModel.studentName.observe(this, Observer { student ->
            presentationTv.text = student.name + "ss"
            println("studentName is ${student.name}")
        })
        println("uppercaseName is ${firstViewModel.uppercaseName.value}")
    }

    override fun onStop() {
        super.onStop()
        Observable.just(1).delay(5,TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe { presentationTv.text = "Gopi Text updated in BG" }
    }
}

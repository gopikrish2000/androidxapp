package com.mlrecommendation.gopi.androidxsamplearchitectureapp.databinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.HandlerUtil

class DataBindingActivity : AppCompatActivity() {
    lateinit var viewModel: DataBindingVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityDataBindingBinding>(this, R.layout.activity_data_binding)
        viewModel = ViewModelProviders.of(this).get(DataBindingVM::class.java)
        binding.lifecycleOwner = this
        binding.dataBindingVM = viewModel

        viewModel.mediatorLiveData.observe(this, Observer{
            Toast.makeText(applicationContext,"mediatorLiveData Value is $it", Toast.LENGTH_SHORT).show()
        })

       /* viewModel.editTextLiveData.observe(this, Observer{  // for observing in Activity

        })*/
        HandlerUtil.postDelayed(5000){
            viewModel.firstLiveData.postValue("I am setting this")
            viewModel.editTextLiveData.postValue("5 sec")
            viewModel.editTextVisibilityLiveData.postValue(false)
            Toast.makeText(applicationContext,"calledAfter 5 sec", Toast.LENGTH_SHORT).show() //toast calling in bg thread is not crashing.
        }

        HandlerUtil.postDelayed(10000) {
            viewModel.editTextLiveData.postValue("10 sec")
            viewModel.editTextVisibilityLiveData.postValue(true)
        }
    }
}

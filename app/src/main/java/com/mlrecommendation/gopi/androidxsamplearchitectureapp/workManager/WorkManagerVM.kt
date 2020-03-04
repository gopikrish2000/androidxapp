package com.mlrecommendation.gopi.androidxsamplearchitectureapp.workManager

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.showToast

class WorkManagerVM : ViewModel() {

    val workLiveData = MutableLiveData<String>().apply { value = "first" }

    fun onPerformWorkClick(view:View){
        MyWorkManagerJobs.firstWorkManagerRequest()
    }

}
package com.mlrecommendation.gopi.androidxsamplearchitectureapp.workManager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.databinding.ActivityWorkManagerBinding

class WorkManagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityWorkManagerBinding>(this, R.layout.activity_work_manager)
        binding.lifecycleOwner = this
        binding.vm = ViewModelProviders.of(this)[WorkManagerVM::class.java]

    }
}

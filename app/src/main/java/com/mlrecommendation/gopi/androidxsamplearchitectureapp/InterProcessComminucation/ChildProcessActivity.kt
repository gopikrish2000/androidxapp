package com.mlrecommendation.gopi.androidxsamplearchitectureapp.InterProcessComminucation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R

class ChildProcessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_process)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }
}

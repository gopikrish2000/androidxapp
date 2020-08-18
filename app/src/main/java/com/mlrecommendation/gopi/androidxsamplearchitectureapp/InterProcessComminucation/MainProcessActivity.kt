package com.mlrecommendation.gopi.androidxsamplearchitectureapp.InterProcessComminucation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R
import kotlinx.android.synthetic.main.activity_main_process.*

class MainProcessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_process)

        processMainBtn.setOnClickListener { startActivity(Intent(this, ChildProcessActivity::class.java)) }
    }
}

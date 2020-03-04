package com.mlrecommendation.gopi.androidxsamplearchitectureapp.lifecycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R

class LifeCycleSecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_cycle_second)
        println("LifeCycleSecondActivity.onCreate")
        findViewById<View>(R.id.firstBtn).setOnClickListener { startActivity(Intent(this, LifeCycleFirstActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)) }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        println("LifeCycleSecondActivity.onNewIntent")
    }

    override fun onRestart() {
        super.onRestart()
        println("LifeCycleSecondActivity.onRestart")
    }

    override fun onStart() {
        super.onStart()
        println("LifeCycleSecondActivity.onStart")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        println("LifeCycleSecondActivity.onRestoreInstanceState")
    }

    override fun onResume() {
        super.onResume()
        println("LifeCycleSecondActivity.onResume")
    }

    override fun onPause() {
        super.onPause()
        println("LifeCycleSecondActivity.onPause")
    }

    override fun onStop() {
        super.onStop()
        println("LifeCycleSecondActivity.onStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        println("LifeCycleSecondActivity.onSaveInstanceState")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("LifeCycleSecondActivity.onDestroy")
    }
}

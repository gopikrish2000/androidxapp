package com.mlrecommendation.gopi.androidxsamplearchitectureapp.lifecycle

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils

class LifeCycleFirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_cycle_test)
        println("LifeCycleFirstActivity.onCreate")
//        supportFragmentManager.beginTransaction().replace(R.id.fragFrame,LifeCycleFragment()).commitAllowingStateLoss()
        findViewById<View>(R.id.firstBtn).setOnClickListener { startActivityForResult(Intent(this, LifeCycleSecondActivity::class.java), 2) }
//        findViewById<View>(R.id.firstBtn).setOnClickListener { startActivity(Intent(this, LifeCycleFirstActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)) }

        //ThreadUtils.sleep(2000)
        println("LifeCycleFirstActivity.onCreate Completed....")
        findViewById<View>(R.id.fragReplaceBtn).setOnClickListener { val fragment = LifeCycleFragment().apply {
            arguments = Bundle().apply { putString("firstParam", ThreadUtils.randomNumber().toString()) }
        }
            supportFragmentManager.findFragmentById(R.id.fragFrame)?.arguments?.getString("firstParam").let { println("oldFrag firstParam is $it ***") }
            supportFragmentManager.beginTransaction().add(R.id.fragFrame, fragment).addToBackStack("dummyName").commit()
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        println("LifeCycleFirstActivity.onNewIntent")
    }

    override fun onRestart() {
        super.onRestart()
        println("LifeCycleFirstActivity.onRestart")
    }

    override fun onStart() {
        super.onStart()
        println("LifeCycleFirstActivity.onStart")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        println("LifeCycleFirstActivity.onRestoreInstanceState")
    }

    override fun onResume() {
        super.onResume()
        println("LifeCycleFirstActivity.onResume")
    }

    override fun onPause() {
        super.onPause()
        println("LifeCycleFirstActivity.onPause")
    }

    override fun onStop() {
        super.onStop()
        println("LifeCycleFirstActivity.onStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        println("LifeCycleFirstActivity.onSaveInstanceState")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("LifeCycleFirstActivity.onDestroy")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        println("LifeCycleFirstActivity.onActivityResult resultCode $resultCode  with reqcode $requestCode")
    }
}

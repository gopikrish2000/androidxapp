package com.mlrecommendation.gopi.androidxsamplearchitectureapp.SingleTopTasks

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.print
import kotlinx.android.synthetic.main.activity_first_task.*

class SecondTaskActivity : AppCompatActivity() {
    val activityName = "SecondTaskActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_task)
        print("$activityName onCreate ")
        firstTaskTv.text = "$activityName"
        firstNextBtn.setOnClickListener { val intent = Intent(this,ThirdTaskActivity::class.java);
            startActivity(intent) }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        print("$activityName onNewIntent ")
    }

    override fun onDestroy() {
        super.onDestroy()
        print("$activityName onDestroy")
    }
}

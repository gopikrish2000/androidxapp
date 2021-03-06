package com.mlrecommendation.gopi.androidxsamplearchitectureapp.androidQBubbles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R
import kotlinx.android.synthetic.main.activity_bubble_helper.*

class BubbleHelperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bubble_helper)

        bubbleBtn.setOnClickListener {
            BubbleHelper.createBubbleNotification(this)
        }
    }
}

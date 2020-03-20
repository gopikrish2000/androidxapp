package com.mlrecommendation.gopi.androidxsamplearchitectureapp.DB.sqlliteDB

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R

//import com.mlrecommendation.gopi.androidxsamplearchitectureapp.databinding.ActivitySqlLiteDbTestBinding

class SqlLiteDbTestActivity : AppCompatActivity() {
//    lateinit var binding: ActivitySqlLiteDbTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivitySqlLiteDbTestBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_sql_lite_db_test)

//        throw RuntimeException("My Gopi Exception")
    }
}

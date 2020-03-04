package com.mlrecommendation.gopi.androidxsamplearchitectureapp.asynctaskTest

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class AsyncTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async_test)
        val myAsyncTask = MyAsyncTask()
        myAsyncTask.execute()
        Observable.just(1).delay(2,TimeUnit.SECONDS).observeOn(Schedulers.newThread()).subscribe { myAsyncTask.executeOnExecutor(Executors.newSingleThreadExecutor(),"temp") }
    }
}

class MyAsyncTask : AsyncTask<String, String, Boolean>() { // when used same reference and execute Asynctask multiple times then "Task already running exception is thrown"
    override fun doInBackground(vararg p0: String?): Boolean {
        Thread.sleep(10000);
        println("executing in bg asynctask")
        return true
    }
}

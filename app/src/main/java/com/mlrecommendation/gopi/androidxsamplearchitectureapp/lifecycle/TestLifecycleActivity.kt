package com.mlrecommendation.gopi.androidxsamplearchitectureapp.lifecycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.SparseArray
import androidx.core.util.forEach
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.copyOf
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.sleep
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_test_lifecycle.*
import java.util.concurrent.TimeUnit

class TestLifecycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_lifecycle)

        testConcurrentModification()

        lifecycleTestBtn.setOnClickListener {
            finish()
            Handler(Looper.getMainLooper()).postDelayed(

                { val intent = Intent(this@TestLifecycleActivity, LifeCycleSecondActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                     startActivity(intent,null)}
                ,7000)
        }
    }

    fun testConcurrentModification(){
        /*val sparseArray = SparseArray<String>().apply { put(2,"wer"); put(3,"rrw"); put(4,"rtryr") }
        Observable.fromCallable {  }.delay(1,TimeUnit.SECONDS).subscribeOn(Schedulers.io()).subscribe{sparseArray.put(4,"t88jj"); println("PPut in BG thread")}
        for (i in 0 until sparseArray.size()){
            sparseArray.put(sparseArray.keyAt(i), sparseArray.valueAt(i)+"erer")
            sleep(1000)
        }*/

//        sparseArray.forEach { key, value ->  }

        val list = mutableListOf<Int>(1,2,3,5,6,7)
        Observable.fromCallable {  }.delay(1,TimeUnit.SECONDS).subscribeOn(Schedulers.io()).subscribe{list.add(188); println("PPut in BG thread")}
        val list1 = mutableListOf<Int>()
        list1.addAll(list)
        for (i in list1){
            println(" i is $i")
            sleep(1000)
        }
        println("execution done")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("TestLifecycleActivity.onDestroy")
    }
}

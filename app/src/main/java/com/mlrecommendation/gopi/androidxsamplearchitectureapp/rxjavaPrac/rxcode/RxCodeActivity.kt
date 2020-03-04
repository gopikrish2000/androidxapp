package com.mlrecommendation.gopi.androidxsamplearchitectureapp.rxjavaPrac.rxcode

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.MyApplication
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.showToast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rx_code.*

@SuppressLint("CheckResult")
class RxCodeActivity : AppCompatActivity() {

    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_code)

        val obj = VideoPlayer()

        playbtn.setOnClickListener { MyApplication.showToast("clicked");
            //obj.play()
            }
        pausebtn.setOnClickListener { obj.pause() }

        compositeDisposable.add(Observable.fromCallable {
//            Thread.sleep(15000)
            while (true){
                if (Thread.interrupted()) {
                    break
                }
            }
            println(" doing in bg")
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe { println(" onNext called") })


    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}

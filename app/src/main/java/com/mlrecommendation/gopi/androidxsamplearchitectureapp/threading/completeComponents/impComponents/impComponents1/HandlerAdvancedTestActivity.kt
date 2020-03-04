package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.impComponents1

import android.content.Intent
import android.os.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm.MvvmFirstActivity
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.print
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.randomNumberInRange
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.sleep
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.startThread
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_handler_advanced_test.*
import java.util.concurrent.TimeUnit

class HandlerAdvancedTestActivity : AppCompatActivity() {

    lateinit var firstBgHandler:Handler
    var uiHandler:Handler? = null
    val compositeDisposable = CompositeDisposable()
    lateinit var myHandlerTv:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler_advanced_test)
        doLogic()
    }

    private fun doLogic() {
        myHandlerTv = handlerTv
        //        firstHandlerTest()
        //        bgHandlerPostAfterQuit()
//        mainHandlerPostingAfterDestroy()
        handlerCancelARunnableNMsg()
    }

    fun firstHandlerTest(){
        startThread ( Runnable {
            Looper.prepare()
            firstBgHandler = Handler(Looper.myLooper()){
                when(it.what){
                    1 -> it.print().also { print("curThreadName "+Thread.currentThread().name.plus(" ")) }
                    2 -> it.print().also { print("curThreadName "+Thread.currentThread().name.plus(" ")) }
                    else -> "elseCondition11  $it".print().also { print("curThreadName "+Thread.currentThread().name) }
                }
                true
            }
            Looper.loop()
        })

        startThread(Runnable {
            sleep(5000)
            for (i in 1..25){
                if(i%2 == 0) firstBgHandler.post{ "Runnable printing"}
                else Message.obtain().apply { what = i; obj = "GopMsg" }.also { firstBgHandler.sendMessage(it) }
            }
        })
    }

    fun bgHandlerPostAfterQuit(){  // Just logs Log.w
        val handlerThread = HandlerThread("")
        handlerThread.start()
        var secondBgHandler = Handler(handlerThread.looper) {
            when (it.what) {
                1 -> it.print().also { print("curThreadName " + Thread.currentThread().name) }
                else -> "elseCondition + $it".print().also { print("curThreadName " + Thread.currentThread().name) }
            }
            true
        }

        Observable.interval(2,TimeUnit.SECONDS).subscribe { secondBgHandler.sendMessage(Message.obtain().apply { what = randomNumberInRange(1,100); }) }.also { compositeDisposable.add(it)}

        Observable.timer(10,TimeUnit.SECONDS).subscribe { handlerThread.quit() }.also { compositeDisposable::add }

        /*
        * W/MessageQueue: Handler (android.os.Handler) {7f4e9ad} sending message to a Handler on a dead thread  // U get Handled exception but not a crash on sending message to a Dead Handler
    java.lang.IllegalStateException: Handler (android.os.Handler) {7f4e9ad} sending message to a Handler on a dead thread
        at android.os.MessageQueue.enqueueMessage(MessageQueue.java:558)
        at android.os.Handler.enqueueMessage(Handler.java:754)
        at android.os.Handler.sendMessageAtTime(Handler.java:703)
        at android.os.Handler.sendMessageDelayed(Handler.java:673)
        at android.os.Handler.sendMessage(Handler.java:611)
        at com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.impComponents1.HandlerAdvancedTestActivity$bgHandlerPostAfterQuit$1.accept(HandlerAdvancedTestActivity.kt:62)
        at com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.impComponents1.HandlerAdvancedTestActivity$bgHandlerPostAfterQuit$1.accept(HandlerAdvancedTestActivity.kt:15)
        at io.reactivex.internal.observers.LambdaObserver.onNext(LambdaObserver.java:63)
        at io.reactivex.internal.operators.observable.ObservableInterval$IntervalObserver.run(ObservableInterval.java:82)
        at io.reactivex.internal.schedulers.ScheduledDirectPeriodicTask.run(ScheduledDirectPeriodicTask.java:38)
        at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:462)
        at java.util.concurrent.FutureTask.runAndReset(FutureTask.java:307)
        at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:302)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1167)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:641)
        at java.lang.Thread.run(Thread.java:919)
        * */
    }

    fun mainHandlerPostingAfterDestroy(){ // If u call finish then also no crash or problem ; Without finish upon going back to same activity the ui is showing with what ever u set => Even after onStop in Android u can update UI values but cannot do fragment commit() as it happens after onSavedInstanceState() So when Activity recreated by System this fragment will be missed. Hence this restriction for Fragments & Dialog Fragments.
        handlerBtn.setOnClickListener { startActivity(Intent(this, MvvmFirstActivity::class.java));
            finish();
        }
        uiHandler = Handler(Looper.getMainLooper()){
            "receiving mesgs ${it.what}".print()
            myHandlerTv.text = when(it.what){
                1 -> "1  $it"
                else -> "else $it"
            }
            true
        }

        Observable.interval(2,TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe { uiHandler?.sendMessage(Message.obtain().apply { what = randomNumberInRange(1,60); obj= "custom" }) }.also { compositeDisposable.add(it) } //compositeDisposable.add(it)
    }

    fun handlerCancelARunnableNMsg(){
        val handler = Handler(Looper.getMainLooper()){ it.print();true }
        val runnable = Runnable { sleep(3000); "inside runnable".print() }
        handler.postDelayed(runnable,5000)
        handler.sendMessageDelayed(Message.obtain().apply { what=1; obj ="1 Message Gopi" }, 3000)
        Observable.timer(7,TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe {
            handler.removeCallbacks(runnable)
            handler.removeMessages(1)
            "clearedMessages".print()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        "DestroyCalled".print()
        uiHandler?.removeCallbacksAndMessages(null)
        compositeDisposable.clear()
//        uiHandler = null
    }
}

package com.mlrecommendation.gopi.androidxsamplearchitectureapp.Test

import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.completeComponents.impComponents.utils.ThreadUtils.sleep
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SampleTest {

    fun sample(runnable:Runnable) {
        println("im sample")
        Completable.create {print("executing"); runnable.run();  }.delay(2000, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.computation())
            .subscribe{
                println("subscribed ...")
            }


    }

    fun nullableTest(key:String?) {
        key ?: return
        val abc: Any? = 5234
//        val abc: Int? = parent
//        val len = (abc as String).length
        val len1 = ((abc as? String) as? String)?.length

        println("both lengths are   && $len1")
    }

}

fun main() {
    print("starting main")
    val obj = SampleTest()
//    obj.sample(Runnable { ThreadUtils.print("main ") })
    obj.nullableTest("ee")
//    sleep(4000)


}
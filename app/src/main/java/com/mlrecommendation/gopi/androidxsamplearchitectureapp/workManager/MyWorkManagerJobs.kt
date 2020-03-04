package com.mlrecommendation.gopi.androidxsamplearchitectureapp.workManager

import android.content.Context
import androidx.work.*
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.getMyApplicationContext
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons.getMyWorkManager
import java.util.concurrent.TimeUnit

object MyWorkManagerJobs {

    fun firstWorkManagerRequest() {
        val workRequest = OneTimeWorkRequestBuilder<MyWorkRequest>().apply {
            setConstraints(Constraints.Builder().apply {
                setRequiredNetworkType(NetworkType.CONNECTED)
                setRequiresBatteryNotLow(true)
            }.build())

            setInputData(workDataOf("a" to 1, "b" to 2, "c" to 3))
            setInitialDelay(1000, TimeUnit.MILLISECONDS)
            addTag("MyWorkRequest")
        }.build()
        val requestId = workRequest.id

        val secondWorkRequest = OneTimeWorkRequestBuilder<MySecondWorkRequest>().build()
        /* WorkManager.getInstance(getMyApplicationContext()).enqueue(workRequest)

         val workInfo = WorkManager.getInstance(getMyApplicationContext()).getWorkInfoById(requestId).get()
         println("BY ID: $workInfo")
         val workInfoByTag = WorkManager.getInstance(getMyApplicationContext()).getWorkInfosByTag("MyWorkRequest").get()
         println("BY Tag: $workInfoByTag")*/
        /*.addListener({
            showToast("got status of workRequest. ")
        }, {})*/

//        getMyWorkManager().beginWith(workRequest).then(secondWorkRequest).enqueue()
         getMyWorkManager().beginUniqueWork("FirstWorkReq",ExistingWorkPolicy.KEEP, mutableListOf(workRequest, workRequest)).enqueue()  // does only one in queue n ignore it its already in queue;  ExistingWorkPolicy.REPLACE will replace if in the queue.
//        getMyWorkManager().cancelWorkById()

    }
}

class MyWorkRequest(val context: Context, val workerParameters: WorkerParameters) : Worker(context, workerParameters) {
    override fun doWork(): Result {
        println("doing its work" + workerParameters.inputData.keyValueMap)
        Thread.sleep(2000)
        println("work done")
        return Result.success()
    }

    override fun onStopped() {  // when stopped clear resourrces
        super.onStopped()
    }
}

class MySecondWorkRequest(val context: Context, val workerParameters: WorkerParameters) : Worker(context, workerParameters) {
    override fun doWork(): Result {
        println("second doing its work" + workerParameters.inputData.keyValueMap)
        Thread.sleep(2000)
        println("second work done")
        return Result.success()
    }
}
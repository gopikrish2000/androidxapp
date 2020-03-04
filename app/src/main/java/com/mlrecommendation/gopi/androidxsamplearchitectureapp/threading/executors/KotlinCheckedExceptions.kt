package com.mlrecommendation.gopi.androidxsamplearchitectureapp.threading.executors

import org.json.JSONObject

object KotlinCheckedExceptions {

    fun checkedExceptionTest() {
        println("KotlinCheckedExceptions.checkedExceptionTest method call")
        val obj = try {
             val obj = JSONObject("sfdwq")
            println("json is "+obj.optString("aa", ""))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        println("KotlinCheckedExceptions.checkedExceptionTest method ENDED")

    }
}
package com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons

import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.work.WorkManager
import java.io.Console
import java.lang.Exception

fun  Any.getMyApplicationContext(): MyApplication {
    return MyApplication.getInstance()
}

fun  Any.showToast(msg:String){
    Toast.makeText(getMyApplicationContext(), msg, Toast.LENGTH_SHORT).show()
}

fun Any.getMyWorkManager(): WorkManager {
    return WorkManager.getInstance(getMyApplicationContext())
}

fun Any.dpToPx(dp:Int):Int = (getMyApplicationContext().resources.displayMetrics.density * dp).toInt()

fun Any.deviceWidth():Int = (getMyApplicationContext().resources.displayMetrics.widthPixels)

fun Any.deviceHeight():Int = (getMyApplicationContext().resources.displayMetrics.heightPixels)

fun Any.getColor(color:Int): Int = ContextCompat.getColor(getMyApplicationContext(),color)

fun <T> MutableList<T>.copyOf(): MutableList<T> {
    return mutableListOf<T>().apply { addAll(this) }
}

fun Any.print(){
    var message = "value is $this"
    message = message.plus(" method -- " + Exception().stackTrace[1].run {  "$methodName():$lineNumber" })
    println(message)
}

fun Any?.printAll(vararg any:Any?){
    var str = this.toString()
    any.forEach { str = str.plus(" , ".plus(it)) }
    str = str.plus(" method -- " + Exception().stackTrace[1].run {  "$methodName():$lineNumber" })

    println("values are $str")
}

fun Any.emptyLine(){
    println("")
}

fun Throwable.getStackTraceAsString() = CommonUtils.getStackTraceAsString(this)
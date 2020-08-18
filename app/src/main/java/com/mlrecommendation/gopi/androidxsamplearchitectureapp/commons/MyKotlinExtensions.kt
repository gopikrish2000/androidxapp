package com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.work.WorkManager


fun  Any.getMyApplicationContext(): MyApplication {
    return MyApplication.getInstance()
}

fun  Any.showToast(msg:String){
    if (Looper.myLooper() == Looper.getMainLooper()) {
        Toast.makeText(getMyApplicationContext(), msg, Toast.LENGTH_SHORT).show()
    } else {
        Handler(Looper.getMainLooper()).post { Toast.makeText(getMyApplicationContext(), msg, Toast.LENGTH_SHORT).show()
        }
    }
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

fun Any?.print(){
    val value = if(this == null) "null" else this.toString()
    var message = "$value"
    message = message.plus("$ANSI_RED ::method:: -- " + Exception().stackTrace[1].run {  "$methodName():$lineNumber" } + ANSI_RESET)
    println(message)
}

const val ANSI_RESET = "\u001B[0m"
const val ANSI_BLACK = "\u001B[30m"
const val ANSI_RED = "\u001B[31m"
const val ANSI_GREEN = "\u001B[32m"
const val ANSI_YELLOW = "\u001B[33m"
const val ANSI_BLUE = "\u001B[34m"
const val ANSI_PURPLE = "\u001B[35m"
const val ANSI_CYAN = "\u001B[36m"
const val ANSI_WHITE = "\u001B[37m"

fun Any?.printAll(vararg any:Any?){
    var str = this.toString()
    any.forEach { str = str.plus(" , ".plus(it)) }
    str = str.plus("$ANSI_RED ::method:: -- " + Exception().stackTrace[1].run {  "$methodName():$lineNumber" } + ANSI_RESET)

    println(str)
}

fun Any.emptyLine(){
    println("")
}

fun Throwable.getStackTraceAsString() = CommonUtils.getStackTraceAsString(this)
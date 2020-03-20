package com.mlrecommendation.gopi.androidxsamplearchitectureapp.commons

import android.app.Activity
import android.content.res.Configuration
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import java.io.PrintWriter
import java.io.StringWriter


class CommonUtils {
    companion object {
        @JvmStatic
        fun getSubstring(string: String?, from: Int): String {
//            return string.substring(from)
            return string ?: ""
        }

        fun hideKeyboard(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = activity.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun hideKeyboard(view: View) {
            val imm = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun showToast(string: String){
            Toast.makeText(MyApplication.getInstance(),string,Toast.LENGTH_SHORT).show()
        }

        fun isPortraitMode(): Boolean {
            return MyApplication.getInstance().getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT
        }

        fun isLandscapeMode(): Boolean {
            return MyApplication.getInstance().getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
        }

        fun getStackTraceAsString(throwable: Throwable): String? {
            val stringWriter = StringWriter()
            throwable.printStackTrace(PrintWriter(stringWriter))
            return stringWriter.toString()
        }
    }
}
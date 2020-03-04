package com.mlrecommendation.gopi.androidxsamplearchitectureapp.databinding.bindingAdapters

import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter

object FirstBindingAdapter {

    @BindingAdapter("viewGone")  // for annotation processor to work use apply plugin: 'kotlin-kapt'
    @JvmStatic fun viewGone(view: View, bool: Boolean){
        view.visibility = if(bool) View.GONE else View.VISIBLE
    }

    @BindingAdapter(value = ["checkTextViewSizeNColorSet"] , requireAll = true)
    @JvmStatic fun checkTextViewSizeNColorSet(textView: TextView, text: String){

       val string = if(text.isNotBlank() ){
             "text not blank "
        } else {
           "either text blank "
       }
    }

    @BindingAdapter(value = ["checkTextViewSizeNColorSet", "viewGone"] , requireAll = false)
    @JvmStatic fun optionalAttributeChecker(textView: TextView, text: String?, bool: Boolean?){  // when u want to track multiple attributes u can try this way.
        val string =
            if(text != null && bool != null ){
                "optionalAttributeChecker Both text & bool defined "
            } else if(text == null && bool != null ){
            "optionalAttributeChecker Only bool defined "
        } else {
            "optionalAttributeChecker Only text defined "
        }
        Toast.makeText(textView.context,string, Toast.LENGTH_LONG).show()
    }
}
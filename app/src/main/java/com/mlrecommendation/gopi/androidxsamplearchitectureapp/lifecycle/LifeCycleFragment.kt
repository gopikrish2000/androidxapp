package com.mlrecommendation.gopi.androidxsamplearchitectureapp.lifecycle

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mlrecommendation.gopi.androidxsamplearchitectureapp.R
import kotlinx.android.synthetic.main.activity_first_task.*

/**
 * Created by Gopi Krishna on 2020-01-10.
 */
class LifeCycleFragment: Fragment() {

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        println("LifeCycleFragment.onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("LifeCycleFragment.onCreate with param ${arguments?.get("firstParam")}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        println("LifeCycleFragment.onCreateView with param ${arguments?.get("firstParam")}\"")
        return inflater.inflate(R.layout.lifecycle_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        println("LifeCycleFragment.onActivityCreated with param ${arguments?.get("firstParam")}\"")
    }

    override fun onStart() {
        super.onStart()
        println("LifeCycleFragment.onStart with param ${arguments?.get("firstParam")}\"")
    }

    override fun onResume() {
        super.onResume()
        println("LifeCycleFragment.onResume with param ${arguments?.get("firstParam")}\"")
    }

    override fun onPause() {
        super.onPause()
        println("LifeCycleFragment.onPause with param ${arguments?.get("firstParam")}\"")
    }

    override fun onStop() {
        super.onStop()
        println("LifeCycleFragment.onStop with param ${arguments?.get("firstParam")}\"")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        println("LifeCycleFragment.onSaveInstanceState")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("LifeCycleFragment.onDestroyView with param ${arguments?.get("firstParam")}\"")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("LifeCycleFragment.onDestroy with param ${arguments?.get("firstParam")}\"")
    }

    override fun onDetach() {
        super.onDetach()
        println("LifeCycleFragment.onDetach with param ${arguments?.get("firstParam")}\"")
    }


}
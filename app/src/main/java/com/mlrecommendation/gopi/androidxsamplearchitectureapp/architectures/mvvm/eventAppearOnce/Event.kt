package com.mlrecommendation.gopi.androidxsamplearchitectureapp.architectures.mvvm.eventAppearOnce

/**
 * Created by Gopi Krishna on 2020-01-15.
 */
class Event<T>(val content: T?) {
    private var isHandled = false

    fun getContentIfNotHandled(): T? {
        if(isHandled) return null
        isHandled = true
        return content
    } }
package com.devyash.basicsindepthpractice

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.devyash.basicsindepthpractice.Constants.LIFECYCLEOBSERVER

class Car:LifecycleObserver {


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun carStart(){
        Log.d(LIFECYCLEOBSERVER,"Car is starting")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun carRunning(){
        Log.d(LIFECYCLEOBSERVER,"Car is Running")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun carBreaking(){
        Log.d(LIFECYCLEOBSERVER,"Car is Breaking")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun carStopped(){
        Log.d(LIFECYCLEOBSERVER,"Car Stopped")
    }
}
package com.devyash.basicsindepthpractice.viewmodels

import androidx.lifecycle.ViewModel


class UserViewModel(private val initialCount:Int) : ViewModel() {

    var count: Int = initialCount

    fun increaseCount() {
        count++
    }
}
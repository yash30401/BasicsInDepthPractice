package com.devyash.basicsindepthpractice.viewmodels

import androidx.lifecycle.ViewModel


class UserViewModel : ViewModel() {

    var count: Int = 0

    fun increaseCount() {
        count++
    }
}
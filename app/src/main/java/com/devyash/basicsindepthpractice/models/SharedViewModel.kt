package com.devyash.basicsindepthpractice.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel:ViewModel() {
    val sharedData = MutableLiveData<String>("Initial Text")
}
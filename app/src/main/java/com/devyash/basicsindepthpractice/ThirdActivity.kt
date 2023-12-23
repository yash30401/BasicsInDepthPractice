package com.devyash.basicsindepthpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.devyash.basicsindepthpractice.Constants.SHAREDSTATEFLOW
import com.devyash.basicsindepthpractice.databinding.ActivityThirdBinding
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ThirdActivity : AppCompatActivity() {

    private var _binding:ActivityThirdBinding?=null
    private val binding  get()= _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startSharedFlow()
        startStateFlow()

    }

    private fun startSharedFlow() {
        val sharedFlow = MutableSharedFlow<Int>()

        lifecycleScope.launch {
            sharedFlow.collect{
                Log.d(SHAREDSTATEFLOW,"SharedFlow Collector 1:- "+it.toString())
            }
        }

        lifecycleScope.launch {
            sharedFlow.collect{
                Log.d(SHAREDSTATEFLOW,"SharedFlow Collector 2:- "+it.toString())
            }
        }

        lifecycleScope.launch {
            repeat(3){
                sharedFlow.emit(it)
            }
        }
    }

    private fun startStateFlow() {
        val mutableStateFlow = MutableStateFlow(0)
        val stateFlow: StateFlow<Int> = mutableStateFlow


        lifecycleScope.launch {
            stateFlow.collect { value ->
                Log.d(SHAREDSTATEFLOW,"StateFlow Collector 1: $value")
            }
        }


        lifecycleScope.launch {
            stateFlow.collect { value ->
                Log.d(SHAREDSTATEFLOW,"StateFlow Collector 2: $value")
            }
        }


        lifecycleScope.launch {
            repeat(3) { i ->
                mutableStateFlow.value = i
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
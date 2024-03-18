package com.devyash.basicsindepthpractice

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.devyash.basicsindepthpractice.databinding.ActivityCoroutinePracBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoroutinePracActivity : AppCompatActivity() {


    private var _binding: ActivityCoroutinePracBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCoroutinePracBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLaunch.setOnClickListener {
            lifecycleScope.launch {
                funDoSomethingAndThorwException()
            }
        }

        binding.btnAsync.setOnClickListener {
            lifecycleScope.async {
                funDoSomethingAndThorwException()
            }
        }


        binding.btnOneByOne.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                val res1 = doLongRunningTaskOne()
                val res2 = doLongRunningTaskTwo()
                val answer = res1+res2
                Log.d("LONGRUNNING","One By One Answer:- ${answer}")
            }
        }

        binding.btnParallely.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                val resDeferred1 = async { doLongRunningTaskOne() }
                val resDeferred2 = async { doLongRunningTaskTwo() }
                val answer = resDeferred1.await()+resDeferred2.await()
                Log.d("LONGRUNNING","Paralley Answer:- ${answer}")
            }
        }

    }

    fun funDoSomethingAndThorwException(){
        throw Exception("Some Exception")
    }


    suspend fun doLongRunningTaskOne():Int{
        return withContext(Dispatchers.Default){

            Log.d("LONGRUNNING","Task One")
            delay(2000)
            return@withContext 20
        }
    }

    suspend fun doLongRunningTaskTwo():Int{
        return withContext(Dispatchers.Default){

            Log.d("LONGRUNNING","Task Two")
            delay(2000)
            return@withContext 10
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
package com.devyash.basicsindepthpractice

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.devyash.basicsindepthpractice.databinding.ActivityFlowsPracBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class FlowsPrac : AppCompatActivity() {

    private var _binding:ActivityFlowsPracBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFlowsPracBinding.inflate(layoutInflater)
        setContentView(binding.root)

            val collectPersons = emitPersons()
           lifecycleScope.launch {
               collectPersons.collect{person->
                   for(i in 0..person.size-1){
                       Log.d("FLOWSPRAC",person.get(i).toString())
                   }
               }
           }

    }

    fun emitPersons(): Flow<List<Person>> {
        return flow {
            val persons = listOf(
                Person("John Doe", 30, "New York", "Software Engineer", 100000),
                Person("Jane Smith", 25, "Los Angeles", "Doctor", 120000),
                Person("Michael Johnson", 35, "Chicago", "Teacher", 80000),
                Person("Emily Brown", 28, "Houston", "Lawyer", 150000),
                Person("William Davis", 40, "Phoenix", "Nurse", 70000),
                Person("Olivia Wilson", 33, "Philadelphia", "Accountant", 110000),
                Person("James Taylor", 45, "San Antonio", "Chef", 90000),
                Person("Sophia Martinez", 38, "San Diego", "Artist", 85000),
                Person("Benjamin Anderson", 32, "Dallas", "Writer", 95000),
                Person("Isabella Thomas", 36, "San Jose", "Entrepreneur", 200000)
            )
            emit(persons)
        }
    }

    data class Person(val name:String, val age:Int, val address:String, val job:String, val salary:Int)

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
package com.devyash.basicsindepthpractice

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.devyash.basicsindepthpractice.databinding.ActivityFlowsPracBinding

class FlowsPrac : AppCompatActivity() {

    private var _binding:ActivityFlowsPracBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFlowsPracBinding.inflate(layoutInflater)
        setContentView(binding.root)

        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
package com.devyash.basicsindepthpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devyash.basicsindepthpractice.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private var _binding: ActivitySecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding  = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("USERNAME")

        binding.tvUser.text = "Hello, ${username.toString()}"

        val intent = Intent(this@SecondActivity,MainActivity::class.java)
        intent.putExtra("CHECKUSER","Yes, User Exist")
        setResult(111,intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
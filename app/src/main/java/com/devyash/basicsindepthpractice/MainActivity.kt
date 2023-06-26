package com.devyash.basicsindepthpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devyash.basicsindepthpractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val contract = registerForActivityResult(Contract()){
        binding.tvCheckUserExist.text = it
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.btnCheckUser.setOnClickListener {
//            val username = binding.etName.text.toString()
//            val intent = Intent(this@MainActivity, SecondActivity::class.java)
//            intent.putExtra("USERNAME", username)
//
//            startActivityForResult(intent, 111)
//        }


        /*
* New ActivityResultLauncher Method*/
        binding.btnCheckUser.setOnClickListener {
            contract.launch(binding.etName.text.toString())
        }


    }

    // Trying onActivityResult Here Old Method
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == 111) {
//            binding.tvCheckUserExist.text = data!!.getStringExtra("CHECKUSER").toString()
//        }
//
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
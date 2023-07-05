package com.devyash.basicsindepthpractice

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.devyash.basicsindepthpractice.BroadcastReciever.AirPlaneModeReceiver
import com.devyash.basicsindepthpractice.Constants.TAG
import com.devyash.basicsindepthpractice.databinding.ActivityMainBinding
import com.devyash.basicsindepthpractice.viewmodels.UserViewModel
import com.devyash.basicsindepthpractice.viewmodels.UserViewModelFactory

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val contract = registerForActivityResult(Contract()) {
        binding.tvCheckUserExist.text = it
    }

    private lateinit var viewModel: UserViewModel
    private val airPlaneModeReceiver = AirPlaneModeReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerReceiver(
            airPlaneModeReceiver,
            IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        )
        viewModel = ViewModelProvider(this, UserViewModelFactory(10)).get(UserViewModel::class.java)
//        binding.btnCheckUser.setOnClickListener {
//            val username = binding.etName.text.toString()
//            val intent = Intent(this@MainActivity, SecondActivity::class.java)
//            intent.putExtra("USERNAME", username)
//
//            startActivityForResult(intent, 111)
//        }

        Log.d(TAG, "onCreate: Calling Main Activity")
        /*
        * New ActivityResultLauncher Method
        * */
        setText()
        binding.btnCheckUser.setOnClickListener {
            contract.launch(binding.etName.text.toString())
            increaseCounter()
            setResourcesStringText()
        }

        binding.btnRedirect.setOnClickListener {
//            Intent(Intent.ACTION_MAIN).also{
//                it.`package` = "com.google.android.youtube"
//                try {
//                    startActivity(it)
//                }catch (e:ActivityNotFoundException){
//                    e.printStackTrace()
//                }
//            }
//        }

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("test@test.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Test Subject")
                putExtra(Intent.EXTRA_TEXT, "Test Text")
            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }

    }


    private fun increaseCounter() {
        viewModel.increaseCount()
        Log.d(TAG, "increaseCounter: ${viewModel.count}")
        setText()
    }

    private fun setText() {
        binding.tvCount.text = "Counter:- ${viewModel.count.toString()}"
    }

    private fun setResourcesStringText() {
        binding.tvLoremIpsum.text = resources.getString(R.string.lorem_ipsum)
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

    /* onStart():-
*  A Activity is called to be started when it is visible to the user.
*  But user can't interact with activity at this point.
* */
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Calling Main Activity")
    }

    /* onResume():-
    *  It is now visible to the user and user can interact with it.
    *  It will go into the onPause() state if there is any activity, dialog or anything come into the foreground.
    * */
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Calling Main Activity")
    }

    /* onPause():-
    *  It will go into the onPause() state if there is any activity, dialog or anything come into the foreground.
    * */
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Calling Main Activity")
    }

    /* onStop():-
    *  At this point whole Activity is not visible to the user.
    * */
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: Calling Main Activity")
    }

    /* onRestart():-
    *  When an user comes back from the another activity or from the minimized state then in this case Activity will be Restarted.
    * */
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: Calling Main Activity")
    }

    /* onDestoryed():-
    *  All resources should be released here or should have already been released in onStop().
    *  On global configurartion changes onDestroy will be called. ex(Screen Rotation)
    * */
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        unregisterReceiver(airPlaneModeReceiver)
        Log.d(TAG, "onDestroy: Calling Main Activity")
    }
}
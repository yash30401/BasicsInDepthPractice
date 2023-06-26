package com.devyash.basicsindepthpractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.devyash.basicsindepthpractice.Constants.TAG
import com.devyash.basicsindepthpractice.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private var _binding: ActivitySecondBinding? = null
    private val binding get() = _binding!!

    /* onCreate():-
    *  This the place  where we initialize  our varibles of that Activity.
    *  Here we set our view (UI) of the Activity.
    * At this point User does not see any thing on the screen.
    * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*Old method getting Result and setting Result*/
//        val username = intent.getStringExtra("USERNAME")
//
//        binding.tvUser.text = "Hello, ${username.toString()}"
//
//        val intent = Intent(this@SecondActivity,MainActivity::class.java)
//        intent.putExtra("CHECKUSER","Yes, User Exist")
//        setResult(111,intent)

        /*Now you don't have to call setResult for setting the Result*/
        val username = intent.getStringExtra("USERNAME")
        binding.tvUser.text = "Hello, ${username.toString()}"
    }

    /* onStart():-
    *  A Activity is called to be started when it is visible to the user.
    *  But user can't interact with activity at this point.
    * */
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Calling")
    }

    /* onResume():-
    *  It is now visible to the user and user can interact with it.
    *  It will go into the onPause() state if there is any activity, dialog or anything come into the foreground.
    * */
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Calling")
    }

    /* onPause():-
    *  It will go into the onPause() state if there is any activity, dialog or anything come into the foreground.
    * */
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Calling")
    }

    /* onStop():-
    *  At this point whole Activity is not visible to the user.
    * */
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: Calling")
    }

    /* onRestart():-
    *  When an user comes back from the another activity or from the minimized state then in this case Activity will be Restarted.
    * */
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: Calling")
    }

    /* onDestoryed():-
    *  All resources should be released here or should have already been released in onStop().
    *  On global configurartion changes onDestroy will be called. ex(Screen Rotation)
    * */
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        Log.d(TAG, "onDestroy: Calling")
    }

}
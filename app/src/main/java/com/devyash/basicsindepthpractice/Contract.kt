package com.devyash.basicsindepthpractice

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class Contract: ActivityResultContract<String,String>() {
    override fun createIntent(context: Context, input: String): Intent {
        val intent = Intent(context,SecondActivity::class.java)
        intent.putExtra("USERNAME",input)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String {
       return "User, Exist"
    }

}
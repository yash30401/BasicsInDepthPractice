package com.devyash.basicsindepthpractice

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.devyash.basicsindepthpractice.DataStoreApiStore.PreferencesKeys.datastore
import com.devyash.basicsindepthpractice.databinding.ActivityDataStoreApiStoreBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException


class DataStoreApiStore : AppCompatActivity() {

    private var _binding:ActivityDataStoreApiStoreBinding?=null
    private val binding get() = _binding!!



    companion object PreferencesKeys{
        private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "Messages")

        val subject = stringPreferencesKey("SUBJECT")
        val message = stringPreferencesKey("MESSAGE")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding =  ActivityDataStoreApiStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSaveMessage.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                saveDataUsingDataStoreApi()
            }
        }

        binding.btnGetMessage.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
              val data= getDataFromDataStoreApi()
                data.collect{it->
                    withContext(Dispatchers.Main){
                        binding.tvMessage.text = "Subject:- ${it.first}\nMessage:- ${it.second}"
                    }

                }
            }
        }
    }


    private suspend fun saveDataUsingDataStoreApi() {
        val subject= binding.etSubject.text.toString()
        val message = binding.etMessage.text.toString()
        applicationContext.datastore.edit { messages->
            messages[PreferencesKeys.subject] = subject
            messages[PreferencesKeys.message] = message
        }
        withContext(Dispatchers.Main){
            Toast.makeText(this@DataStoreApiStore, "Data Saved", Toast.LENGTH_SHORT).show()
            binding.etSubject.text.clear()
            binding.etMessage.text.clear()
        }
    }

    private suspend fun getDataFromDataStoreApi():Flow<Pair<String,String>>{
       val readMessage = applicationContext.datastore.data.catch { exception ->
           if (exception is IOException) {
               emit(emptyPreferences())
           } else {
               throw exception
           }

       }.map {messages->
           val subject = messages[PreferencesKeys.subject]?:""
           val message = messages[PreferencesKeys.message]?:""
           Log.d("DATASTOREAPITEST","SUBJECT:- $subject")
           Log.d("DATASTOREAPITEST","MESSAGE:- $message")
           Pair(subject,message)
       }
        Log.d("DATASTOREAPITEST","ENTERING")
        return readMessage
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


package com.devyash.basicsindepthpractice

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.Image
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModelProvider
import com.devyash.basicsindepthpractice.BroadcastReciever.AirPlaneModeReceiver
import com.devyash.basicsindepthpractice.Constants.SUPERVISORSCOPETEST
import com.devyash.basicsindepthpractice.Constants.SUSPENDCANCELLABLE
import com.devyash.basicsindepthpractice.Constants.TAG
import com.devyash.basicsindepthpractice.databinding.ActivityMainBinding
import com.devyash.basicsindepthpractice.models.NetworkData
import com.devyash.basicsindepthpractice.models.UserMapper
import com.devyash.basicsindepthpractice.services.RunningService
import com.devyash.basicsindepthpractice.viewmodels.ImageViewModel
import com.devyash.basicsindepthpractice.viewmodels.UserViewModel
import com.devyash.basicsindepthpractice.viewmodels.UserViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Calendar
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val contract = registerForActivityResult(Contract()) {
        binding.tvCheckUserExist.text = it
    }


//    private lateinit var imagesViewModel:ImageViewModel


    private lateinit var imageDownloader: ImageDownloader

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

        lifecycle.addObserver(Car())


        imageDownloader = ImageDownloader()

//        imagesViewModel = ViewModelProvider(this).get(ImageViewModel::class.java)

//        val projection = arrayOf(
//            MediaStore.Images.Media._ID,
//            MediaStore.Images.Media.DISPLAY_NAME
//        )
//
//        val millisYesterday = Calendar.getInstance().apply {
//            add(Calendar.DAY_OF_YEAR, -1)
//        }.timeInMillis
//
//        val selection = "${MediaStore.Images.Media.DATE_TAKEN} >= ?"
//        val selectionArgs = arrayOf(millisYesterday.toString())
//
//        val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"

//        contentResolver.query(
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//            projection,
//            selection,
//            selectionArgs,
//            sortOrder
//        )?.use { cursor ->
//            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
//            val nameColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
//
//            val images = mutableListOf<com.devyash.basicsindepthpractice.Image>()
//
//            while (cursor.moveToNext()) {
//                val id = cursor.getLong(idColumn)
//                val name = cursor.getString(nameColumn)
//                val uri =
//                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
//
//                images.add(Image(id,name,uri))
//            }
//            imagesViewModel.updateImages(images)
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }

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

        binding.btnStartSerivce.setOnClickListener {
            startMyRunningSerive()
        }

        binding.btnStopService.setOnClickListener {
            stopMyRunningService()
        }

//        GlobalScope.launch(Dispatchers.IO) {
//            val result = performNetworkCall()
//            Log.d(SUSPENDCANCELLABLE,result.toString())
//        }

//        GlobalScope.launch(Dispatchers.IO) {
//            val result = performNetworkCallWithSuspendCancellableCoroutine()
//            Log.d(
//                  SUSPENDCANCELLABLE,
//                "First Result:- ${result.first}\nSecond Result:- ${result.second}"
//            )
//        }

//        GlobalScope.launch(Dispatchers.IO) {
//            val result = performNetworkCallWithAsyncAwait()
//            Log.d(
//                SUSPENDCANCELLABLE,
//                "First Result:- ${result.first}\nSecond Result:- ${result.second}"
//            )
//        }

//        GlobalScope.launch(Dispatchers.IO) {
//            val downloadImagesWithScope = imageDownloader.downloadImagesWithCoroutineScope()
//            Log.d(SUPERVISORSCOPETEST,"Downloaded images with coroutineScope: $downloadImagesWithScope")
//        }


        GlobalScope.launch(Dispatchers.IO) {
            val downloadImagesWithSupervisor = imageDownloader.downloadImagesWithSupervisorScope()
            Log.d(SUPERVISORSCOPETEST,"Downloaded images with supervisorScope: $downloadImagesWithSupervisor")
        }

        saveSomeDataUsingDataStoreApi()

        binding.btnVisitDataStoreActivity.setOnClickListener {
            Intent(this@MainActivity,DataStoreApiStore::class.java).also {
                startActivity(it)
            }
        }

        val networkData = NetworkData(12134,"Yashveer Singh",21,true,"Aligarh","India")

        val user = UserMapper().fromNetworkToUserMapper(networkData)

        binding.tvMappingText.text = "Name:- ${user.name}\nAge:-${user.age}\nHave License:-${if(user.haveLicense) "Yes I have" else "No I don't have"}"

    }

    private fun saveSomeDataUsingDataStoreApi() {

    }

    // ----------------------------------------------------------------------------------------------------------------------------

    private fun increaseCounter() {
        viewModel.increaseCount()
        Log.d(TAG, "increaseCounter: ${viewModel.count}")
        setText()
    }

    private fun setText() {
        binding.tvCount.text = "Counter:- ${viewModel.count.toString()}"
    }

    // ----------------------------------------------------------------------------------------------------------------------------

    private fun startMyRunningSerive() {
        Intent(applicationContext, RunningService::class.java).also {
            it.action = RunningService.Actions.START.toString()
            startService(it)
        }
    }

    private fun stopMyRunningService() {
        Intent(applicationContext, RunningService::class.java).also {
            it.action = RunningService.Actions.STOP.toString()
            startService(it)
        }
    }

    // ----------------------------------------------------------------------------------------------------------------------------

    // Trying onActivityResult Here Old Method
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == 111) {
//            binding.tvCheckUserExist.text = data!!.getStringExtra("CHECKUSER").toString()
//        }
//
//    }

    // ----------------------------------------------------------------------------------------------------------------------------

/*
*  Fetching Data From Single Network Call
*  suspendabeCancellableCoroutine suspends at suspendabeCancellableCoroutine and wait for the result and and won't move forward
*  until and unless result is available.
* */

//    suspend fun fetchDataFromNetwork():String{
//        Log.d(SUSPENDCANCELLABLE,"Fetching Started")
//        delay(10000)
//        return "Hello :- Data is here"
//    }
//
//    suspend fun performNetworkCall():String{
//        return suspendCancellableCoroutine { continuation ->
//            GlobalScope.launch {
//                Log.d(SUSPENDCANCELLABLE,"Before Fecthed Data")
//                val data = fetchDataFromNetwork()
//                continuation.resume(data)
//                Log.d(SUSPENDCANCELLABLE,"After Fetched Data")
//            }
//
//        }
//    }

    // ----------------------------------------------------------------------------------------------------------------------------

    /*
    * Here we are watching the behaviour of Aysnc/Await and suspendableCancellableCoroutine
    * Fetching data through two networkcalls
    * CASE 1:- In this case suspendCancellableCoroutine suspend at first network call and wait for the result after the result is
    * available then only it move to the next network call.
    * CASE 2:- In this case Async/Await make both the network calls simultaneously and will return the result as soon as they get it.
    *
    * Both can be used based on your needs whether u want to become wait for one network call or u want to make both calls simultaneously.
    * */

    suspend fun fecthDataFromNetwork1(): String {
        delay(5000)
        return "Data 1 Is Here"
    }

    suspend fun fetchDataFromNetwork2(): String {
        delay(10000)
        return "Data 2 Is Here"
    }

    suspend fun performNetworkCallWithSuspendCancellableCoroutine(): Pair<String, String> {
        return suspendCancellableCoroutine { continuation ->
            GlobalScope.launch {
                try {
                    Log.d(SUSPENDCANCELLABLE, "Inside Try Block")
                    Log.d(SUSPENDCANCELLABLE, "Data1 Is Fetching")
                    val data1 = fecthDataFromNetwork1()
                    Log.d(SUSPENDCANCELLABLE, "Data2 Is Fetching")
                    val data2 = fetchDataFromNetwork2()

                    // Here we can see data1 is fetched and then it moved to fetch data2.

                    Log.d(SUSPENDCANCELLABLE, "Fetching Completed")

                    continuation.resume(Pair(data1, data2))
                } catch (e: Throwable) {
                    continuation.resumeWithException(e)
                }
            }
        }
    }

    suspend fun performNetworkCallWithAsyncAwait(): Pair<String, String> {
        return coroutineScope {
            Log.d(SUSPENDCANCELLABLE, "Inside Coroutine Block")
            Log.d(SUSPENDCANCELLABLE, "Data1 Is Fetching")
            val deferredData1 = async { fecthDataFromNetwork1() }
            Log.d(SUSPENDCANCELLABLE, "Data2 Is Fetching")
            val deferredData2 = async { fetchDataFromNetwork2() }

            // Here we can both the network calls starts fetching the data parallely.

            Log.d(SUSPENDCANCELLABLE, "Before Await")

            val data1 = deferredData1.await()
            val data2 = deferredData2.await()

            Log.d(SUSPENDCANCELLABLE, "After Await")
            Log.d(SUSPENDCANCELLABLE, "Fetching Completed")

            Pair(data1, data2)
        }
    }

    // ----------------------------------------------------------------------------------------------------------------------------

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


    // Saving using onSaveInstanceState at config changes
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        Log.d(TAG,"OnSaveInstanceState")
        outState.putString("ONSAVETESTING","Saving value 1")
    }

    // Getting values back in onRestoreInstanceState
    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        Log.d(TAG,"onRestoreInstanceState")
        val value = savedInstanceState?.get("ONSAVETESTING")
        Log.d(TAG,"onRestoreInstanceState Value:- $value")
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


data class Image(
    val id: Long,
    val name: String,
    val uri: Uri
)
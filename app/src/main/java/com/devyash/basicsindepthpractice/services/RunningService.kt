package com.devyash.basicsindepthpractice.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.devyash.basicsindepthpractice.R


class RunningService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        /*
        * IBinder binds multiple service
        * */
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, "Running_Channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Run is active")
            .setContentInfo("Elapsed time: 00:50")
            .build()

        startForeground(1,notification)
    }

    enum class Actions {
        START,
        STOP
    }
}
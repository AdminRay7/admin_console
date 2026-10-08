package com.admin.console

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class LocationService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val ch = "track_ch"
        val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            nm.createNotificationChannel(NotificationChannel(ch, "Tracking", NotificationManager.IMPORTANCE_LOW))
        startForeground(99, NotificationCompat.Builder(this, ch)
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .setContentTitle("Tracking active")
            .build())
        return START_STICKY
    }
}

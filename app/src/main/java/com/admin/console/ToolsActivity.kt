package com.admin.console

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat

class ToolsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(40, 60, 40, 40) }

        val bm = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        val lvl = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        layout.addView(TextView(this).apply { text = "🔋 Battery: $lvl%"; textSize = 15f })

        layout.addView(Button(this).apply {
            text = "📳 Vibrate"
            setOnClickListener {
                val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    v.vibrate(VibrationEffect.createWaveform(longArrayOf(200, 100, 200), -1))
                else @Suppress("DEPRECATION") v.vibrate(500)
            }
        })

        layout.addView(Button(this).apply {
            text = "🔊 Play Beep"
            setOnClickListener {
                val tg = ToneGenerator(AudioManager.STREAM_MUSIC, 80)
                tg.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 2000)
            }
        })

        layout.addView(Button(this).apply {
            text = "🔔 Send Notification"
            setOnClickListener {
                val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val ch = "admin_ch"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    nm.createNotificationChannel(NotificationChannel(ch, "Admin", NotificationManager.IMPORTANCE_HIGH))
                nm.notify(1, NotificationCompat.Builder(this@ToolsActivity, ch)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("Admin Console")
                    .setContentText("Hello from your device")
                    .build())
            }
        })

        layout.addView(Button(this).apply {
            text = "📋 Show Clipboard"
            setOnClickListener {
                val cm = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                val clip = cm.primaryClip
                val txt = if (clip != null && clip.itemCount > 0) clip.getItemAt(0).text?.toString() ?: "(empty)" else "(empty)"
                Toast.makeText(this@ToolsActivity, "Clipboard: $txt", Toast.LENGTH_LONG).show()
            }
        })

        val et = EditText(this).apply { hint = "Type here — keystrokes logged below" }
        val logTv = TextView(this).apply { textSize = 13f; text = "Keylog:\n" }
        et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { logTv.text = "Keylog:\n${s.toString()}" }
            override fun beforeTextChanged(s: CharSequence?, a: Int, b: Int, c: Int) {}
            override fun onTextChanged(s: CharSequence?, a: Int, b: Int, c: Int) {}
        })
        layout.addView(et)
        layout.addView(logTv)

        setContentView(ScrollView(this).apply { addView(layout) })
    }
}

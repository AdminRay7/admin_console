package com.admin.console

import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DeviceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val info = """
            Manufacturer: ${Build.MANUFACTURER}
            Model: ${Build.MODEL}
            Device: ${Build.DEVICE}
            Android: ${Build.VERSION.RELEASE} (SDK ${Build.VERSION.SDK_INT})
            Brand: ${Build.BRAND}
            Product: ${Build.PRODUCT}
        """.trimIndent()
        setContentView(LinearLayout(this).apply {
            addView(TextView(this@DeviceActivity).apply { text = info; textSize = 15f; setPadding(40, 60, 40, 40) })
        })
    }
}

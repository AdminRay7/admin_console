package com.admin.console

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class SmsActivity : AppCompatActivity() {
    private lateinit var tv: TextView
    private val REQ = 400
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(30, 40, 30, 40) }
        layout.addView(Button(this).apply { text = "📩 Load Inbox"; setOnClickListener { load("inbox") } })
        layout.addView(Button(this).apply { text = "📤 Load Sent"; setOnClickListener { load("sent") } })
        tv = TextView(this).apply { textSize = 13f }
        layout.addView(ScrollView(this).apply { addView(tv) }, LinearLayout.LayoutParams(-1, 0, 1f))
        setContentView(layout)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_SMS), REQ)
    }
    private fun load(box: String) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) return
        val uri = Uri.parse("content://sms/$box")
        val cur: Cursor? = contentResolver.query(uri, null, null, null, "date DESC")
        val sb = StringBuilder()
        cur?.use {
            val a = it.getColumnIndex("address")
            val b = it.getColumnIndex("body")
            val d = it.getColumnIndex("date")
            var n = 0
            while (it.moveToNext() && n < 50) {
                sb.append(it.getString(a)).append(" — ")
                  .append(java.util.Date(it.getLong(d))).append("\n")
                  .append(it.getString(b)).append("\n\n")
                n++
            }
        }
        tv.text = if (sb.isEmpty()) "No messages or permission denied" else sb.toString()
    }
}

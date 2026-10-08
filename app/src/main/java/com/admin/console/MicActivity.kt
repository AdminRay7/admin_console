package com.admin.console

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File

class MicActivity : AppCompatActivity() {
    private var recorder: MediaRecorder? = null
    private var file: File? = null
    private val REQ = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(40, 60, 40, 40) }
        layout.addView(Button(this).apply { text = "🎤 Start"; setOnClickListener { startRec() } })
        layout.addView(Button(this).apply { text = "⏹ Stop"; setOnClickListener { stopRec() } })
        setContentView(layout)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), REQ)
    }

    private fun startRec() {
        val dir = File(filesDir, "audio").apply { mkdirs() }
        file = File(dir, "audio_${System.currentTimeMillis()}.m4a")
        recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) MediaRecorder(this) else MediaRecorder()
        recorder?.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(file!!.absolutePath)
            prepare()
            start()
        }
        Toast.makeText(this, "Recording…", Toast.LENGTH_SHORT).show()
    }

    private fun stopRec() {
        try { recorder?.stop(); recorder?.release() } catch (e: Exception) {}
        recorder = null
        Toast.makeText(this, "Saved ${file?.name}", Toast.LENGTH_SHORT).show()
    }
}

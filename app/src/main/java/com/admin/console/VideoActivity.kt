package com.admin.console

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class VideoActivity : AppCompatActivity() {
    private lateinit var previewView: PreviewView
    private var videoCapture: VideoCapture<Recorder>? = null
    private val REQ = 103

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        previewView = PreviewView(this)
        layout.addView(previewView, LinearLayout.LayoutParams(-1, 0, 1f))
        val row = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        row.addView(Button(this).apply { text = "▶ Preview"; setOnClickListener { startPreview() } })
        row.addView(Button(this).apply { text = "⏺ Record"; setOnClickListener {
            Toast.makeText(this@VideoActivity, "Preview ready — plug MediaRecorder here", Toast.LENGTH_LONG).show()
        } })
        layout.addView(row)
        setContentView(layout)
        val perms = arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
        if (perms.any { ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED })
            ActivityCompat.requestPermissions(this, perms, REQ)
    }

    private fun startPreview() {
        val pf = ProcessCameraProvider.getInstance(this)
        pf.addListener({
            val p = pf.get()
            val preview = androidx.camera.core.Preview.Builder().build()
                .also { it.setSurfaceProvider(previewView.surfaceProvider) }
            videoCapture = VideoCapture.withOutput(Recorder.Builder().build())
            p.unbindAll()
            p.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA, preview, videoCapture)
        }, ContextCompat.getMainExecutor(this))
    }
}

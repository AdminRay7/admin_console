package com.admin.console

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NetworkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val caps = cm.activeNetwork?.let { cm.getNetworkCapabilities(it) }
        val info = buildString {
            append("Connected: ${caps != null}\n")
            append("WiFi: ${caps?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false}\n")
            append("Cellular: ${caps?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ?: false}\n")
            append("VPN: ${caps?.hasTransport(NetworkCapabilities.TRANSPORT_VPN) ?: false}\n")
            append("Downstream: ${caps?.linkDownstreamBandwidthKbps ?: 0} kbps\n")
            append("Upstream: ${caps?.linkUpstreamBandwidthKbps ?: 0} kbps")
        }
        setContentView(LinearLayout(this).apply {
            addView(TextView(this@NetworkActivity).apply { text = info; textSize = 15f; setPadding(40, 60, 40, 40) })
        })
    }
}

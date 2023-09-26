package com.example.testapplication.core

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData

class NetworkAvailabilityChecker(private val context: Context) : LiveData<Boolean>() {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: android.net.Network) {
            postValue(true) // Network is available
        }

        override fun onLost(network: android.net.Network) {
            postValue(false) // Network is lost
        }
    }

    override fun onActive() {
        super.onActive()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(receiver, filter)
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    override fun onInactive() {
        super.onInactive()
        context.unregisterReceiver(receiver)
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val isNetworkAvailable =
                connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
            postValue(isNetworkAvailable)
        }
    }
}
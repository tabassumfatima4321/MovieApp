package com.example.testapplication.core.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log

fun String.printLog(text: Any?) {
        Log.e(this, if (text !is String) text.toString() else text)
}


inline fun <reified T : Any> newIntent(context: Context): Intent = Intent(context, T::class.java)


inline fun <reified T : Any> Context.launchActivity(
        options: Bundle? = null, noinline init: Intent.() -> Unit = {}
) {
        val intent = newIntent<T>(this)
        intent.init()
        startActivity(intent, options)
}

fun Activity.getIntentString(key: String, default: String = ""): String {
        return intent?.getStringExtra(key) ?: default
}
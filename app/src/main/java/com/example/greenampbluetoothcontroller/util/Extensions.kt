package com.example.greenampbluetoothcontroller.util

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.greenampbluetoothcontroller.R

fun View?.visible() {
    this?.visibility = View.VISIBLE
}

fun View?.gone() {
    this?.visibility = View.GONE
}

fun View?.inVisible() {
    this?.visibility = View.INVISIBLE
}

fun View.focusOnView() {
    Handler(Looper.getMainLooper()).postDelayed({
        val rect = Rect(0, 0, width, height)

        requestRectangleOnScreen(rect, false)
    }, 500)
}

fun View.setOnClickListeners(onClickListeners: ((View) -> Unit)) {
    var lastClickTime: Long = 0

    setOnClickListener {
        if (SystemClock.elapsedRealtime() - lastClickTime > 2000) {
            lastClickTime = SystemClock.elapsedRealtime()

            onClickListeners.invoke(it)
        }
    }
}

fun String?.validateString() = if (isNullOrEmpty()) "" else trim()

fun Fragment.makeToast(message: String?) {
    Toast.makeText(requireContext(), message ?: "Something went wrong!", Toast.LENGTH_LONG).show()
}

fun AutoCompleteTextView.loadAdapter(list: List<*>?) {
    setAdapter(
        ArrayAdapter(
            context,
            R.layout.item_spinner_layout,
            R.id.tvSpinner,
            list
                ?: listOf(),
        ),
    )
}

fun Context.hasPermission(permission: String): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    } else {
        ActivityCompat.checkSelfPermission(
            this,
            permission,
        ) == PackageManager.PERMISSION_GRANTED
    }
}
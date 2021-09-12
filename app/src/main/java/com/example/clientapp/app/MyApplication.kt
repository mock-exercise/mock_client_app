package com.example.clientapp.app

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application(){
    companion object {
                /**
         * @param context An Activity or Application Context.
         * @param stringRes A string resource that to be displayed inside a Toast.
         */
        fun showToast(context: Context, @StringRes stringRes: Int) {
            Toast.makeText(context, stringRes, Toast.LENGTH_LONG).show()
        }
    }
}
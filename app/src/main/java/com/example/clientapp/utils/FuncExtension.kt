package com.example.clientapp.utils

import android.util.Log
import java.lang.IllegalArgumentException
import java.lang.NullPointerException
import java.text.SimpleDateFormat
import java.util.*

object FuncExtension{

    private val TAG: String = this.javaClass.simpleName

    fun String.convertDateStringToTimestamp(): Long{

        try {
            val date = SimpleDateFormat("dd-MM-yyyy", Locale.US).parse(this)!!
            return date.time
        }catch (ex: IllegalArgumentException){
            Log.e(TAG, "convertDateStringToTimestamp: ")
        }catch (ex: NullPointerException){
            Log.e(TAG, "convertDateStringToTimestamp: ")
        }

        return 0
    }
}
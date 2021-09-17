package com.example.clientapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream
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
    fun String.convertBase64ToBitmap(): Bitmap {
        val byteString = Base64.decode(this, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(byteString,0, byteString.size)
    }

    fun Bitmap.encodeImage(): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}
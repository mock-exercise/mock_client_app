package com.example.clientapp.utils

import com.example.clientapp.utils.MyStringUtils.ab
import java.text.SimpleDateFormat
import java.util.*

object MyStringUtils {
    @JvmStatic
    fun convertTimestampToDate(timeStamp: Long): String {
        return try {
            val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
            val netDate = Date((timeStamp) * 1000)
            sdf.format(netDate)
        } catch (ex: Exception) {
            ex.toString()
        }
    }

    @JvmStatic
    fun convertTimestampToHour(timeStamp: Long): String {
        return try {
            val sdf = SimpleDateFormat("hh:mm", Locale.US)
            val netDate = Date((timeStamp) * 1000)
            sdf.format(netDate)
        } catch (ex: Exception) {
            ex.toString()
        }
    }
    @JvmStatic
    fun String.ab(): String{
        return "a"
    }
//    @JvmStatic
//    fun
}

fun main(){
    val a:String = "a"
    a.ab()
}
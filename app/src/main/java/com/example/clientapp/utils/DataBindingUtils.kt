package com.example.clientapp.utils

import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class DataBindingUtils {
    companion object {
        @JvmStatic
        fun convertTimestampToDate(timeStamp: Long): String {
            return try {
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
                val netDate = Date(timeStamp)
                sdf.format(netDate)
            } catch (ex: Exception) {
                ex.toString()
            }
        }

        @JvmStatic
        fun convertTimestampToHour(timeStamp: Long): String {
            return try {
                val sdf = SimpleDateFormat("hh:mm", Locale.US)
                val netDate = Date(timeStamp)
                sdf.format(netDate)
            } catch (ex: Exception) {
                ex.toString()
            }
        }

        @JvmStatic
        fun convertPriceToUI(number: Int): String{
            val strNumber = number.toString()
            val sb = StringBuilder(strNumber)

            if(sb.length > 3){
                var index = sb.length - 3
                do{
                    sb.insert(index,", " )
                    index -= 3
                }while (index > 0)
            }
            return sb.toString()
        }
    }
}
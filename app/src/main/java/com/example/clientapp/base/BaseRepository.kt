
package com.example.clientapp.base

import android.util.Log
import com.example.clientapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseRepository {

    suspend fun <T> safeApiCall(apiCall: suspend () -> T?): Resource<T> = withContext(Dispatchers.IO) {
        try {
            Resource.Success(apiCall.invoke())
        } catch (e: Throwable) {
            Log.e("TAG", "Mất kết nối tới server" )
            Resource.Error(e.message)
        }
    }
}
package com.heinhtetoo.tmdb.data.remote

import android.util.Log
import com.heinhtetoo.tmdb.utils.Resource
import retrofit2.Response

abstract class BaseDataSource {

    companion object {
        private val TAG = BaseDataSource::class.java.simpleName
    }

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            Log.d(TAG, "Network Call Running...")
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Log.e("remoteDataSource", message)
        return Resource.error("Network call has failed due to: $message")
    }

}
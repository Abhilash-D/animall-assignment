package com.animall.assignment2.data.remote

import com.animall.assignment2.util.Resource
import retrofit2.Response
import android.util.Log
import retrofit2.Call
import retrofit2.Callback

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            e.printStackTrace()
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Log.d("Network Error",message)
        return Resource.error("Network call has failed for a following reason: $message")
    }

}
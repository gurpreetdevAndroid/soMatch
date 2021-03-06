package com.devstory.somatch.repository.networkrequests

import com.devstory.somatch.R
import com.devstory.somatch.repository.models.Error
import com.devstory.somatch.repository.models.PojoNetworkResponse
import okhttp3.ResponseBody
import java.io.IOException

/**
 * Created by Mukesh on 20/7/18.
 */
object RetrofitRequest {

    fun checkForResponseCode(code: Int): PojoNetworkResponse {
        return when (code) {
            200 -> PojoNetworkResponse(true, false)
            403 -> PojoNetworkResponse(false, true)
            else -> PojoNetworkResponse(false, false)
        }
    }

    fun getErrorMessage(responseBody: ResponseBody): String {
        val errorMessage = ""
        try {
            val errorConverter = RestClient.retrofitInstance!!
                    .responseBodyConverter<Error>(Error::class.java, arrayOfNulls<Annotation>(0))
            return errorConverter.convert(responseBody).error_description
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return errorMessage
    }

    fun getRetrofitError(t: Throwable): Int {
        return if (t is IOException) {
            R.string.no_internet
        } else {
            R.string.retrofit_failure
        }
    }

}
package com.devstory.somatch.repository.networkrequests

import retrofit2.Response


interface NetworkRequestCallbacks {

    fun onSuccess(response: Response<*>)

    fun onError(t: Throwable)

}
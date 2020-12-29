package com.devstory.somatch.repository.interactors

import com.devstory.somatch.repository.networkrequests.NetworkRequestCallbacks
import com.devstory.somatch.repository.networkrequests.RestClient
import com.devstory.somatch.utils.ApplicationGlobal
import com.devstory.somatch.utils.GeneralFunctions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

/**
 * Created by Mukesh on 20/7/18.
 */
class LoginInteractor {

    companion object {
        const val DEVICE_TYPE = "Android"
    }

    fun loginUser(email: String, password: String, fcmToken: String,
                  networkRequestCallbacks: NetworkRequestCallbacks): Disposable {
        return RestClient.get().login(email, password,
                GeneralFunctions.generateRandomString(15),
                DEVICE_TYPE, fcmToken, ApplicationGlobal.deviceLocale)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Response<*>>() {
                    override fun onNext(response: Response<*>) {
                        networkRequestCallbacks.onSuccess(response)
                    }

                    override fun onError(t: Throwable) {
                        networkRequestCallbacks.onError(t)
                    }

                    override fun onComplete() {

                    }
                })
    }

    fun recoverPassword(email: String,
                        networkRequestCallbacks: NetworkRequestCallbacks): Disposable {
        return RestClient.get().forgotPassword(email,
                ApplicationGlobal.deviceLocale)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Response<*>>() {
                    override fun onNext(response: Response<*>) {
                        networkRequestCallbacks.onSuccess(response)
                    }

                    override fun onError(t: Throwable) {
                        networkRequestCallbacks.onError(t)
                    }

                    override fun onComplete() {

                    }
                })
    }

}
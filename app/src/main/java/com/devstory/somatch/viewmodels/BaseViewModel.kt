package com.devstory.somatch.viewmodels

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devstory.somatch.repository.models.RetrofitErrorMessage
import com.devstory.somatch.repository.preferences.UserPrefsManager
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Mukesh on 13/02/2018.
 */
abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    protected val mUserPrefsManager: UserPrefsManager by lazy { UserPrefsManager(getApplication()) }
    protected val isShowLoader = MutableLiveData<Boolean>()
    protected val isShowNoDataText = MutableLiveData<Boolean>()
    protected val isShowSwipeRefreshLayout = MutableLiveData<Boolean>()
    protected val isSessionExpired = MutableLiveData<Boolean>()
    protected val retrofitErrorDataMessage = MutableLiveData<RetrofitErrorMessage>()
    protected val retrofitErrorMessage = MutableLiveData<RetrofitErrorMessage>()
    protected val successMessage = MutableLiveData<String>()
    protected val errorHandler = MutableLiveData<ErrorHandler>()
    protected val mCompositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }


    fun isShowLoader(): LiveData<Boolean> = isShowLoader

    fun isShowNoDataText(): LiveData<Boolean> = isShowNoDataText

    fun isSessionExpired(): LiveData<Boolean> = isSessionExpired

    fun isShowSwipeRefreshLayout(): LiveData<Boolean> = isShowSwipeRefreshLayout

    fun getRetrofitErrorDataMessage(): LiveData<RetrofitErrorMessage> = retrofitErrorDataMessage

    fun getRetrofitErrorMessage(): LiveData<RetrofitErrorMessage> = retrofitErrorMessage

    fun getErrorHandler(): LiveData<ErrorHandler> = errorHandler

    fun getSuccessMessage(): LiveData<String> = successMessage

    enum class ErrorHandler(@StringRes private val resourceId: Int) : ErrorEvent {
//        EMPTY_FULLNAME(R.string.empty_full_name),
//        EMPTY_USERNAME(R.string.empty_username),
//        EMPTY_EMAIL(R.string.empty_email),
//        INVALID_EMAIL(R.string.invalid_email),
//        EMPTY_PHONE_NUMBER(R.string.empty_phone_number),
//        EMPTY_PASSWORD(R.string.empty_password),
//        INVALID_PASSWORD(R.string.invalid_password),
//        ACCEPT_TERMS_AND_POLICY(R.string.accept_terms_and_policy),
//        EMPTY_OLD_PASSWORD(R.string.empty_old_password),
//        EMPTY_NEW_PASSWORD(R.string.empty_new_password),
//        INVALID_NEW_PASSWORD(R.string.invalid_new_password),
//        PASSWORD_NOT_MATCHED(R.string.password_not_matched),
//        INVALID_MOOD_ID(R.string.invalid_mood_id),
//        EMPTY_POST_TTTLE(R.string.empty_post_title),
//        EMPTY_MEDIA(R.string.empty_media),
//        INVALID_MEDIA_FILE(R.string.invalid_media_file),
//        INVALID_MOOD_SELECTION(R.string.invalid_mood_selection),
//        EMPTY_COMMENT(R.string.empty_comment),
//        EMPTY_GROUP_NAME(R.string.empty_group_name)
        ;

        override fun getErrorResource() = resourceId
    }

    interface ErrorEvent {
        @StringRes
        fun getErrorResource(): Int
    }
}
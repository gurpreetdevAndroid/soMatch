package com.devstory.somatch.repository.networkrequests

import com.devstory.somatch.repository.models.PojoGeneral
import com.devstory.somatch.repository.models.PojoUserLogin
import com.devstory.somatch.repository.models.User
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface API {

    @FormUrlEncoded
    @POST("login")
    fun login(@Field("phone_number") userName: String,
              @Field("password") password: String,
              @Field("device_id") deviceId: String,
              @Field("device_type") deviceType: String,
              @Field("device_token") deviceToken: String,
              @Field("locale") locale: String): Observable<Response<PojoUserLogin>>

    @FormUrlEncoded
    @POST("forgot-password")
    fun forgotPassword(@Field("email") email: String,
                       @Field("locale") locale: String): Observable<Response<PojoGeneral>>

    @Multipart
    @POST("edit-profile")
    fun updateProfile(@Part("session_id") sessionId: RequestBody,
                      @Part picture: MultipartBody.Part?,
                      @Part("full_name") fullName: RequestBody,
                      @Part("username") userName: RequestBody,
                      @Part("email") email: RequestBody,
                      @Part("country_code") countryCode: RequestBody,
                      @Part("phone_number") phoneNumber: RequestBody,
                      @Part("country") country: RequestBody,
                      @Part("city") city: RequestBody,
                      @Part("age") age: RequestBody,
                      @Part("locale") deviceLocale: RequestBody): Observable<Response<User>>

}
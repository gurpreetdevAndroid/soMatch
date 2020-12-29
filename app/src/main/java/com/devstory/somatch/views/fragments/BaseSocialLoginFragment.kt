package com.devstory.somatch.views.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import com.devstory.somatch.R
import com.devstory.somatch.repository.models.PojoRegister
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


/**
 * Created by Mukesh on 18/10/18.
 */
abstract class BaseSocialLoginFragment : BaseFragment() {

    companion object {

        private const val REQUEST_CODE_GOOGLE_PLUS_LOGIN = 34
    }

    private val mCallbackManager: CallbackManager? by lazy {
        CallbackManager.Factory.create()
    }

    private val mGoogleSignInClient: GoogleSignInClient? by lazy {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions
                .DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        // New Google SignIn Client
        GoogleSignIn.getClient(activityContext, gso)
    }

    override fun init() {
        getSHA1ForFacebook()
        getFBLoginManager()
    }

    private fun getSHA1ForFacebook() {
        try {
            @SuppressLint("PackageManagerGetSignatures") val info = activity!!
                    .packageManager.getPackageInfo(
                    activity!!.packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (ignored: PackageManager.NameNotFoundException) {
        } catch (ignored: NoSuchAlgorithmException) {
        }

    }

    private fun getFBLoginManager() {
        // For Facebook SignIn
        LoginManager.getInstance().registerCallback(mCallbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        showProgressLoader()
                        val request = GraphRequest.newMeRequest(
                                loginResult.accessToken
                        ) { `object`, response ->
                            if (null != response) {
                                try {
                                    LoginManager.getInstance().logOut()

                                    // Create PojoRegister object out of response
                                    val fullName = `object`.getString("name")
                                    val pojoRegister = PojoRegister(fullName,
                                            if (`object`.has("email")) {
                                                `object`.getString("email")
                                            } else {
                                                "${fullName.toLowerCase()
                                                        .replace(" ", "")}@facebook.com"
                                            },
                                            `object`.getString("id"),
                                            "",
                                            "https://graph.facebook.com/"
                                                    + `object`.getString("id")
                                                    + "/picture")

                                    onSuccessfulSocialLogin(pojoRegister)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    hideProgressLoader()
                                    showMessage(resId = R.string.retrofit_failure)
                                }

                            }
                        }
                        val parameters = Bundle()
                        parameters.putString("fields", "email,name")
                        request.parameters = parameters
                        request.executeAsync()
                    }

                    override fun onCancel() {}

                    override fun onError(exception: FacebookException) {
                        var errorMessage = exception.message
                        if ((errorMessage ?: "").contains("ERR_INTERNET_DISCONNECTED")) {
                            errorMessage = getString(R.string.no_internet)
                        }
                        showMessage(message = errorMessage)
                    }
                })
    }

    fun doFacebookLogin() {
        LoginManager.getInstance().logInWithReadPermissions(this@BaseSocialLoginFragment,
                Arrays.asList("email", "public_profile"))
    }

    fun doGooglePlusLogin() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE_PLUS_LOGIN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_GOOGLE_PLUS_LOGIN) {
                try {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                    val googleSignInAccount = task.getResult(ApiException::class.java)

                    // Create PojoRegister object out of response
                    val pojoRegister = PojoRegister(googleSignInAccount?.displayName ?: "",
                            googleSignInAccount?.email ?: "",
                            "",
                            googleSignInAccount?.id ?: "0",
                            googleSignInAccount?.photoUrl?.toString() ?: "")

                    showProgressLoader()
                    onSuccessfulSocialLogin(pojoRegister)

                } catch (e: ApiException) {
                    // The ApiException status code indicates the detailed failure reason.
                    // Please refer to the GoogleSignInStatusCodes class reference for more information.
                    showMessage(resId = R.string.retrofit_failure)
                }
            } else {
                mCallbackManager?.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    internal abstract fun onSuccessfulSocialLogin(pojoRegister: PojoRegister)

}
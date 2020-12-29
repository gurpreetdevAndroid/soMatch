package com.devstory.somatch.repository.models

/**
 * Created by Mukesh on 18/10/2018.
 */
data class PojoRegister(val fullName: String = "",
                        val email: String = "",
                        val facebookId: String = "",
                        val googlePlusId: String = "",
                        val profileImage: String = "")

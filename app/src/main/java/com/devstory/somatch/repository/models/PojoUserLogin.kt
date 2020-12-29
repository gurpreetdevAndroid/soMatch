package com.devstory.somatch.repository.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Mukesh on 19/7/18.
 */
data class PojoUserLogin(
        val profile: User,
        val session_id: String = ""
)

@Parcelize
data class User(
        val age: Int,
        val status: Int,
        val city: String = "",
        val country: String = "",
        val email: String = "",
        val full_name: String = "",
        val followers_count: Int = 0,
        val following_count: Int = 0,
        val id: Int,
        val image: String = "",
        val mood_id: Int,
        val mood_name: String = "",
        val mood_image: String = "",
        val mood_color: String = "",
        val country_code: String = "",
        val phone_number: String = "",
        val username: String = "",
        val can_change_password: Int = 0,
        var is_selected: Boolean = false,
        val is_admin: Int = 0
) : Comparable<User>, Parcelable {
    override fun compareTo(other: User) = when {
        id != other.id -> -1
        else -> 0
    }

    constructor() : this(0, 0, "", "", "", "", 0,
            0, 0, "", 0, "", "",
            "", "", "", "", 0,
            false, 0)
}
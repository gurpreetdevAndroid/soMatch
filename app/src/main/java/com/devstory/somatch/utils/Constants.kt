package com.devstory.somatch.utils

import android.os.Environment

/**
 * Created by Mukesh on 20/7/18.
 */
object Constants {

    private const val APP_NAME = "Emotic"

    // Media Constants
    private val LOCAL_STORAGE_BASE_PATH_FOR_MEDIA = Environment
            .getExternalStorageDirectory().toString() + "/" + APP_NAME
    val LOCAL_STORAGE_BASE_PATH_FOR_USER_PHOTOS = "$LOCAL_STORAGE_BASE_PATH_FOR_MEDIA/Users/Photos/"
    val LOCAL_STORAGE_BASE_PATH_FOR_POSTS_MEDIA = "$LOCAL_STORAGE_BASE_PATH_FOR_MEDIA/Posts/"
    val LOCAL_STORAGE_BASE_PATH_FOR_CHAT_MEDIA = "$LOCAL_STORAGE_BASE_PATH_FOR_MEDIA/Chats/"
    const val IMAGES_FOLDER = "Images/"
    const val VIDEOS_FOLDER = "Videos/"
    const val VIDEOS_THUMBS_FOLDER = "Thumbs/"

}
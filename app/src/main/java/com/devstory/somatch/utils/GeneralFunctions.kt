package com.devstory.somatch.utils

import android.graphics.Color
import android.os.Build
import android.os.Environment
import android.util.Log
import com.facebook.drawee.view.SimpleDraweeView
import java.io.File
import java.io.IOException


/**
 * Created by Mukesh on 20/7/18.
 */
object GeneralFunctions {

    private const val ALPHA_NUMERIC_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
    private const val JPEG_FILE_PREFIX = "IMG_"
    private const val JPEG_FILE_SUFFIX = ".jpg"
    private const val VIDEO_FILE_PREFIX = "VID_"
    const val VIDEO_FILE_SUFFIX = ".mp4"
    private const val VIDEO_THUMB_FILE_PREFIX = "Thumb_"
    private const val VIDEO_THUMB_FILE_SUFFIX = ".jpg"
    private const val MIN_PASSWORD_LENGTH = 6
    private const val MAX_PASSWORD_LENGTH = 15

    val isAboveLollipopDevice: Boolean
        get() = Build.VERSION_CODES.LOLLIPOP <= Build.VERSION.SDK_INT

    @Throws(IOException::class)
    fun setUpImageFile(directory: String): File? {
        var imageFile: File? = null
        if (Environment.MEDIA_MOUNTED == Environment
                        .getExternalStorageState()) {
            val storageDir = File(directory)
            if (!storageDir.mkdirs()) {
                if (!storageDir.exists()) {
                    Log.d("CameraSample", "failed to create directory")
                    return null
                }
            }

            imageFile = File.createTempFile(JPEG_FILE_PREFIX
                    + System.currentTimeMillis() + "_",
                    JPEG_FILE_SUFFIX, storageDir)
        }
        return imageFile
    }

    @Throws(IOException::class)
    fun setUpVideoFile(directory: String): File? {
        var videoFile: File? = null
        if (Environment.MEDIA_MOUNTED == Environment
                        .getExternalStorageState()) {
            val storageDir = File(directory)
            if (!storageDir.mkdirs()) {
                if (!storageDir.exists()) {
                    return null
                }
            }

            videoFile = File.createTempFile(VIDEO_FILE_PREFIX
                    + System.currentTimeMillis() + "_",
                    VIDEO_FILE_SUFFIX, storageDir)
        }
        return videoFile
    }

    @Throws(IOException::class)
    fun setUpVideoThumbFile(directory: String): File? {
        var videoThumbFile: File? = null
        if (Environment.MEDIA_MOUNTED == Environment
                        .getExternalStorageState()) {
            val storageDir = File(directory)
            if (!storageDir.mkdirs()) {
                if (!storageDir.exists()) {
                    return null
                }
            }

            videoThumbFile = File.createTempFile(VIDEO_THUMB_FILE_PREFIX
                    + System.currentTimeMillis() + "_",
                    VIDEO_THUMB_FILE_SUFFIX, storageDir)
        }
        return videoThumbFile
    }

    fun generateRandomString(randomStringLength: Int): String {
        val buffer = StringBuffer()
        val charactersLength = ALPHA_NUMERIC_CHARS.length
        for (i in 0 until randomStringLength) {
            val index = Math.random() * charactersLength
            buffer.append(ALPHA_NUMERIC_CHARS.get(index.toInt()))
        }
        return buffer.toString()
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return null != target && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.length in MIN_PASSWORD_LENGTH..MAX_PASSWORD_LENGTH
    }


    fun getResizedUserImage(imageUrl: String, imageWidth: Int = 0, imageHeight: Int = 0): String {
        return when {
            imageUrl.contains("graph.facebook.com") -> "$imageUrl?width=$imageWidth&height=$imageHeight"
            imageUrl.contains("googleusercontent") -> imageUrl + "sz=" + imageWidth
            else -> imageUrl + (if (0 != imageWidth) "/$imageWidth" else "") +
                    if (0 != imageHeight) "/$imageHeight" else ""
        }
    }

    fun getResizedImage(imageUrl: String, imageWidth: Int, imageHeight: Int): String {
        return imageUrl + (if (0 != imageWidth) "/$imageWidth" else "") +
                if (0 != imageHeight) "/$imageHeight" else ""
    }

    fun getLocalImageFile(file: File): String {
        return "file://$file"
    }

    fun setUserImage(sdvUserImage: SimpleDraweeView, moodColor: String, image: String, width: Int,
                     height: Int) {
        if (moodColor.isNotEmpty()) {
            val roundingParams = sdvUserImage.hierarchy.roundingParams
            roundingParams?.borderColor = Color.parseColor(moodColor)
            sdvUserImage.hierarchy.roundingParams = roundingParams
        }
        sdvUserImage.setImageURI(getResizedUserImage(image, width, height))
    }

}
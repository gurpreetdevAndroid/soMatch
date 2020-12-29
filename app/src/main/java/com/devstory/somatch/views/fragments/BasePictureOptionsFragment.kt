package com.devstory.somatch.views.fragments

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.view.ViewGroup
import androidx.core.content.FileProvider
import com.emotic.app.utils.GetSampledImage
import com.devstory.somatch.BuildConfig
import com.devstory.somatch.R
import com.devstory.somatch.utils.GeneralFunctions
import com.devstory.somatch.utils.MarshMallowPermissions
import com.devstory.somatch.views.activities.inflate
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.show_picture_options_bottom_sheet.view.*
import java.io.File

/**
 * Created by Mukesh on 17/5/18.
 */
abstract class BasePictureOptionsFragment : BaseFragment(), GetSampledImage.SampledImageAsyncResp {

    companion object {
        private const val REQUEST_CODE_GALLERY_PHOTOS = 192
        private const val REQUEST_CODE_TAKE_PHOTO = 281
    }

    private var picturePath: String? = null
    private var imagesDirectory: String? = null
    private var isCameraOptionSelected: Boolean = false
    private val mMarshMallowPermissions by lazy { MarshMallowPermissions(this) }

    override fun init() {
        setData()
    }

    fun showPictureOptionsBottomSheet(imagesDirectory: String) {
        val bottomSheetDialog = BottomSheetDialog(activity!!)
        val view = (view as ViewGroup).inflate(R.layout.show_picture_options_bottom_sheet)

        view.tvCamera.setOnClickListener {
            checkForPermissions(true, imagesDirectory)
            bottomSheetDialog.dismiss()
        }
        view.tvGallery.setOnClickListener {
            checkForPermissions(false, imagesDirectory)
            bottomSheetDialog.dismiss()
        }
        view.tvCancel.setOnClickListener { bottomSheetDialog.dismiss() }

        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }

    private fun checkForPermissions(isCameraOptionSelected: Boolean, imagesDirectory: String) {
        this.isCameraOptionSelected = isCameraOptionSelected
        this.imagesDirectory = imagesDirectory

        if (mMarshMallowPermissions.isPermissionGrantedForWriteExtStorage) {
            if (isCameraOptionSelected) {
                startCameraIntent()
            } else {
                openGallery()
            }
        } else {
            mMarshMallowPermissions.requestPermissionForWriteExtStorage()
        }
    }

    private fun openGallery() {
        startActivityForResult(Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI), REQUEST_CODE_GALLERY_PHOTOS)
    }

    private fun startCameraIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            val file = GeneralFunctions.setUpImageFile(imagesDirectory!!)
            picturePath = file!!.absolutePath

            val outputUri = FileProvider
                    .getUriForFile(activity!!, BuildConfig.APPLICATION_ID + ".provider", file)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
            takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

        } catch (e: Exception) {
            e.printStackTrace()
            picturePath = null
        }
        startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            MarshMallowPermissions.WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE ->
                if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
                    if (isCameraOptionSelected) {
                        startCameraIntent()
                    } else {
                        openGallery()
                    }
                } else {
                    showMessage(R.string.enable_storage_permission, null,
                            false)
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            var isGalleryImage = false
            if (requestCode == REQUEST_CODE_GALLERY_PHOTOS) {
                data?.data?.let { uri ->
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor = activity!!.contentResolver.query(uri,
                            filePathColumn, null, null, null)
                    cursor!!.moveToFirst()
                    val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                    picturePath = cursor.getString(columnIndex)
                    cursor.close()
                    isGalleryImage = true
                }
            }

            GetSampledImage(this).execute(picturePath, imagesDirectory,
                    isGalleryImage.toString(),
                    resources.getDimension(R.dimen.user_image_downsample_size).toInt().toString())
        }
    }

    override fun onSampledImageAsyncPostExecute(file: File) {
        onGettingImageFile(file)
    }


    abstract fun setData()

    abstract fun onGettingImageFile(file: File)

}
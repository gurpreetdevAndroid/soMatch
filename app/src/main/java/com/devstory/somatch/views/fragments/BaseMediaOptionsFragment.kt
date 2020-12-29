//package com.example.template.views.fragments
//
//import android.app.Activity
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.graphics.Bitmap
//import android.media.MediaMetadataRetriever
//import android.media.ThumbnailUtils
//import android.net.Uri
//import android.os.AsyncTask
//import android.provider.MediaStore
//import android.view.View
//import android.view.ViewGroup
//import androidx.core.content.FileProvider
//import com.example.template.utils.Constants
//import com.example.template.utils.GeneralFunctions
//import com.emotic.app.utils.GetSampledImage
//import com.example.template.BuildConfig
//import com.example.template.R
//import com.example.template.utils.MarshMallowPermissions
//import com.example.template.views.activities.inflate
//import com.google.android.material.bottomsheet.BottomSheetDialog
//import java.io.File
//import java.io.FileOutputStream
//import java.io.InputStream
//
//
///**
// * Created by Mukesh on 17/5/18.
// */
//abstract class BaseMediaOptionsFragment : BaseFragment(), GetSampledImage.SampledImageAsyncResp {
//
//    companion object {
//        const val DSPHOTOEDITOR_API_KEY = "63f63702f86f4759fefe230ed694fcf430e91f98"
//
//        const val REQUEST_CODE_GALLERY_PHOTOS = 234
//        const val REQUEST_CODE_TAKE_PHOTO = 23
//        const val REQUEST_CODE_GALLERY_VIDEOS = 484
//        const val REQUEST_CODE_RECORD_VIDEO = 83
//        const val REQUEST_CODE_PHOTO_EDITOR = 343
//
//        private const val COMPRESSED_VIDEO_FILE_SUFFIX = "_compressed.mp4"
//
//        const val MEDIA_TYPE_NONE = 0
//        const val MEDIA_TYPE_TAKE_PHOTO = 1
//        const val MEDIA_TYPE_BROWSE_PHOTOS = 2
//        const val MEDIA_TYPE_RECORD_VIDEO = 3
//        const val MEDIA_TYPE_BROWSE_VIDEOS = 4
//
//    }
//
//    private var picturePath: String = ""
//    private var videoPath: String = ""
//    private var baseStorageDirectory: String = ""
//    private var mediaStorageDirectory: String = ""
//    private val mMarshMallowPermissions by lazy { MarshMallowPermissions(this) }
//
//    private lateinit var bottomSheetDialog: BottomSheetDialog
//    private var currentSelectedOption = 0
//    private var shouldDeleteOriginalVideo = false
//
//    override fun init() {
//        setData()
//    }
//
//    fun showMediaOptionsBottomSheet(baseStorageDirectory: String) {
//        this.baseStorageDirectory = baseStorageDirectory
//        bottomSheetDialog = BottomSheetDialog(activity!!)
//        val view = (view as ViewGroup).inflate(R.layout.show_media_options_bottom_sheet)
//
//        view.tvTakePhoto.setOnClickListener(optionClickListner)
//        view.tvBrowsePhotos.setOnClickListener(optionClickListner)
//        view.tvRecordVideo.setOnClickListener(optionClickListner)
//        view.tvBrowseVideos.setOnClickListener(optionClickListner)
//        view.tvCancel.setOnClickListener(optionClickListner)
//
//        bottomSheetDialog.setContentView(view)
//        bottomSheetDialog.show()
//    }
//
//    private val optionClickListner = View.OnClickListener {
//        when (it?.id) {
//            R.id.tvTakePhoto -> {
//                currentSelectedOption = MEDIA_TYPE_TAKE_PHOTO
//                mediaStorageDirectory = baseStorageDirectory + Constants.IMAGES_FOLDER
//            }
//            R.id.tvBrowsePhotos -> {
//                currentSelectedOption = MEDIA_TYPE_BROWSE_PHOTOS
//                mediaStorageDirectory = baseStorageDirectory + Constants.IMAGES_FOLDER
//            }
//            R.id.tvRecordVideo -> {
//                currentSelectedOption = MEDIA_TYPE_RECORD_VIDEO
//                mediaStorageDirectory = baseStorageDirectory + Constants.VIDEOS_FOLDER
//            }
//            R.id.tvBrowseVideos -> {
//                currentSelectedOption = MEDIA_TYPE_BROWSE_VIDEOS
//                mediaStorageDirectory = baseStorageDirectory + Constants.VIDEOS_FOLDER
//            }
//            R.id.tvCancel -> {
//                currentSelectedOption = MEDIA_TYPE_NONE
//            }
//        }
//
//        // Dismiss bottom sheet dialog
//        bottomSheetDialog.dismiss()
//
//        if (MEDIA_TYPE_NONE != currentSelectedOption) {
//            // Check for permissions
//            if (mMarshMallowPermissions.isPermissionGrantedForWriteExtStorage) {
//                openCorrespondingMediaOptions()
//            } else {
//                mMarshMallowPermissions.requestPermissionForWriteExtStorage()
//            }
//        }
//    }
//
//
//    private fun openCorrespondingMediaOptions() {
//        when (currentSelectedOption) {
//            MEDIA_TYPE_TAKE_PHOTO -> startCameraIntent()
//            MEDIA_TYPE_BROWSE_PHOTOS -> openGallery()
//            MEDIA_TYPE_RECORD_VIDEO -> {
//                if (mMarshMallowPermissions.isPermissionGrantedForRecordAudio) {
//                    openCameraForRecordignVideo()
//                } else {
//                    mMarshMallowPermissions.requestPermissionForRecordAudio()
//                }
//            }
//            MEDIA_TYPE_BROWSE_VIDEOS -> {
////                val intent = Intent()
////                intent.action = Intent.ACTION_GET_CONTENT
////                intent.type = "video/*"
////                startActivityForResult(intent, REQUEST_CODE_GALLERY_VIDEOS)
//
//                startActivityForResult(Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI),
//                        REQUEST_CODE_GALLERY_VIDEOS)
//            }
//        }
//    }
//
//    private fun openCameraForRecordignVideo() {
//        try {
//            val file = GeneralFunctions.setUpVideoFile(mediaStorageDirectory)
//            if (null != file) {
//                videoPath = file.absolutePath
//
//                val outputUri = FileProvider.getUriForFile(activity!!,
//                        BuildConfig.APPLICATION_ID + ".provider", file)
//
//                val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
//                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 60)
//                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1)
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
//                startActivityForResult(intent, REQUEST_CODE_RECORD_VIDEO)
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun openGallery() {
//        startActivityForResult(Intent(Intent.ACTION_PICK,
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI), REQUEST_CODE_GALLERY_PHOTOS)
//    }
//
//    private fun startCameraIntent() {
//        try {
//            val file = GeneralFunctions.setUpImageFile(mediaStorageDirectory)
//
//            if (null != file) {
//                picturePath = file.absolutePath
//                val outputUri = FileProvider
//                        .getUriForFile(activity!!, BuildConfig.APPLICATION_ID + ".provider", file)
//
//                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
//                takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
//                startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO)
//            }
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
//                                            grantResults: IntArray) {
//        when (requestCode) {
//            MarshMallowPermissions.WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE ->
//                if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
//                    openCorrespondingMediaOptions()
//                } else {
//                    showMessage(R.string.enable_storage_permission, null,
//                            false)
//                }
//            MarshMallowPermissions.RECORD_AUDIO_PERMISSION_REQUEST_CODE -> {
//                if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
//                    openCameraForRecordignVideo()
//                } else {
//                    showMessage(R.string.enable_record_audio_permission, null,
//                            false)
//                }
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (Activity.RESULT_OK == resultCode) {
//            when (requestCode) {
//                REQUEST_CODE_GALLERY_PHOTOS -> {
//                    data?.data?.let {
//                        try {
//                            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
//                            val cursor = activity!!.contentResolver.query(it,
//                                    filePathColumn, null, null, null)
//                            cursor!!.moveToFirst()
//                            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
//                            picturePath = cursor.getString(columnIndex)
//                            cursor.close()
//
//                            // Downsample image
//                            GetSampledImage(this).execute(picturePath, mediaStorageDirectory,
//                                    "true", resources.getDimension(R.dimen.post_image_downsample_size)
//                                    .toInt().toString())
//
//                        } catch (e: Exception) {
//                            e.printStackTrace()
//                            showMessage(resId = R.string.unable_to_access_file)
//                        }
//                    }
//                }
//                REQUEST_CODE_TAKE_PHOTO -> {
//                    GetSampledImage(this).execute(picturePath, mediaStorageDirectory,
//                            "false", resources.getDimension(R.dimen.post_image_downsample_size)
//                            .toInt().toString())
//                }
//                REQUEST_CODE_GALLERY_VIDEOS -> {
////                    val uri = data?.data
////                    if (null == uri) {
////                        return
////                    } else {
////                        // check for video extension
////                        val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(activityContext
////                                .contentResolver.getType(uri))
////                        if (!"mp4".equals(extension, true)) {
////                            showMessage(resId = R.string.invalid_video)
////                            return
////                        }
////                    }
//
//                    /*
//                     * Get the file's content URI from the incoming Intent,
//                     * then query the server app to get the file's display name
//                     * and size.
//                     */
////                    activityContext.contentResolver.openInputStream(uri)
////                            ?.let { inputStream ->
////
////                                // Generate output video file
////                                val videoFile = GeneralFunctions.setUpVideoFile(mediaStorageDirectory)
////
////                                if (null != videoFile) {
////                                    videoPath = videoFile.absolutePath
////
////                                    // Write InputStream to output file path
////                                    inputStream.toFile(videoPath)
////
////                                    // Compress Video
////                                    shouldDeleteOriginalVideo = true
////                                    VideoCompressor().execute(videoPath,
////                                            getCompressedVideoPath(videoPath))
////
////                                }
////                            }
//
//
//                    data?.data?.let {
//                        try {
//                            // Get video path
//                            val filePathColumn = arrayOf(MediaStore.Video.Media.DATA)
//                            val cursor = activity!!.contentResolver.query(it,
//                                    filePathColumn, null, null, null)
//                            cursor.moveToFirst()
//                            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
//                            videoPath = cursor.getString(columnIndex)
//                            cursor.close()
//
//                            //  Get Video Duration
//                            val mediaMetadataRetriever = MediaMetadataRetriever()
//                            mediaMetadataRetriever.setDataSource(videoPath)
//                            val time = mediaMetadataRetriever
//                                    .extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
//                            if (!time.isNullOrEmpty()) {
//                                val timeInSeconds = time.toLong() / 1000
//                                if (60 < timeInSeconds) {
//                                    showMessage(resId = R.string.invalid_video_selection_from_gallery)
//                                } else {
//                                    // Compress video
//                                    shouldDeleteOriginalVideo = false
//                                    VideoCompressor().execute(videoPath,
//                                            getCompressedVideoPath(videoPath))
//                                }
//                            }
//                        } catch (e: Exception) {
//                            e.printStackTrace()
//                            showMessage(resId = R.string.unable_to_access_file)
//                        }
//                    }
//                }
//                REQUEST_CODE_RECORD_VIDEO -> {
//                    shouldDeleteOriginalVideo = true
//                    VideoCompressor().execute(videoPath, getCompressedVideoPath(videoPath))
//                }
//                REQUEST_CODE_PHOTO_EDITOR -> {
//                    data?.data?.let { uri ->
//                        onGettingImageFile(File(uri.path))
//                    }
//                }
//            }
//        }
//    }
//
//    private fun InputStream.toFile(path: String) {
//        File(path).outputStream().use { this.copyTo(it) }
//    }
//
//    private fun getCompressedVideoPath(videoPath: String?): String? {
//        return videoPath?.replace(GeneralFunctions.VIDEO_FILE_SUFFIX,
//                COMPRESSED_VIDEO_FILE_SUFFIX) ?: ""
//    }
//
//    internal inner class VideoCompressor : AsyncTask<String, Void, Boolean>() {
//
//        override fun onPreExecute() {
//            super.onPreExecute()
//            showProgressLoader()
//        }
//
//        override fun doInBackground(vararg params: String): Boolean? {
//            return MediaController.getInstance().convertVideo(params[0], params[1])
//        }
//
//        override fun onPostExecute(compressed: Boolean?) {
//            super.onPostExecute(compressed)
//            hideProgressLoader()
//            if (compressed!!) {
//
//                // Get video thumbnail
//                try {
//                    val bmp = ThumbnailUtils.createVideoThumbnail(
//                            videoPath, MediaStore.Video.Thumbnails.MINI_KIND)
//
//                    val videoThumbFile = GeneralFunctions
//                            .setUpVideoThumbFile(baseStorageDirectory + Constants.VIDEOS_THUMBS_FOLDER)
//
//                    FileOutputStream(videoThumbFile).use { out ->
//                        bmp.compress(Bitmap.CompressFormat.JPEG, 85, out)
//                    }
//
//                    if (null != videoThumbFile) {
//                        // Delete original file and get compressed video file
//                        if (shouldDeleteOriginalVideo) {
//                            File(videoPath).delete()
//                        }
//                        val videoFile = File(getCompressedVideoPath(videoPath))
//                        onGettingVideoFile(videoFile, videoThumbFile)
//                    }
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            }
//        }
//    }
//
//    override fun onSampledImageAsyncPostExecute(file: File) {
//        // Open photo editor
//        val dsPhotoEditorIntent = Intent(activityContext, DsPhotoEditorActivity::class.java)
//        dsPhotoEditorIntent.data = Uri.fromFile(file)
//        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_API_KEY,
//                DSPHOTOEDITOR_API_KEY)
////        dsPhotoEditorIntent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY,
////                imagesDirectory)
//        startActivityForResult(dsPhotoEditorIntent, REQUEST_CODE_PHOTO_EDITOR)
//    }
//
//
//    abstract fun setData()
//
//    abstract fun onGettingImageFile(file: File)
//
//    abstract fun onGettingVideoFile(videoFile: File, videoThumbFile: File)
//
//}
package com.devstory.somatch.utils

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.devstory.somatch.R

/**
 * Created by Mukesh on 20/7/18.
 */
class MarshMallowPermissions(private val mFragment: Fragment) {

    companion object {
        const val WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 19
    }

    private var mActivity: Activity = mFragment.activity!!

    val isPermissionGrantedForWriteExtStorage: Boolean
        get() = ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    fun requestPermissionForWriteExtStorage() {
        if (mFragment.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showAlertDialog(mActivity.getString(R.string.storage_permission_needed),
                    DialogInterface.OnClickListener { _, _ ->
                        mFragment.requestPermissions(
                                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE)
                    }, null)
        } else {
            mFragment.requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE)
        }
    }

    private fun showAlertDialog(message: String,
                                okListener: DialogInterface.OnClickListener,
                                cancelListener: DialogInterface.OnClickListener?) {
        AlertDialog.Builder(mActivity)
                .setMessage(message)
                .setPositiveButton(mActivity.getString(R.string.ok), okListener)
                .setNegativeButton(mActivity.getString(R.string.cancel), cancelListener)
                .create()
                .show()
    }

}
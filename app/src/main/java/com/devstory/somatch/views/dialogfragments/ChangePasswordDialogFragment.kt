package com.devstory.somatch.views.dialogfragments

import com.devstory.somatch.R
import kotlinx.android.synthetic.main.toolbar_dialog_fragments.*

/**
 * Created by Mukesh on 29/6/18.
 */
class ChangePasswordDialogFragment : BaseDialogFragment() {

    override val layoutId: Int
        get() = R.layout.dialogfragment_change_password

    override fun init() {
        // Set Toolbar Title
        tvToolbarTitle.text = getString(R.string.change_password)

        // Set OnClick Listener
//        btChangePassword.setOnClickListener {
//            if (targetFragment is SettingsFragment) {
//                (targetFragment as SettingsFragment).onChangePassword(etOldPassword.text
//                        .toString().trim(), etNewPassword.text.toString().trim())
//            }
//        }
    }

    override val isFullScreenDialog: Boolean
        get() = false
}
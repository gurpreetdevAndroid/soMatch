package com.devstory.somatch.views.dialogfragments

import com.devstory.somatch.R
import kotlinx.android.synthetic.main.toolbar_dialog_fragments.*

/**
 * Created by Mukesh on 29/6/18.
 */
class ForgotPasswordDialogFragment : BaseDialogFragment() {

    override val layoutId: Int
        get() = R.layout.dialog_fragment_forgot_passsword

    override fun init() {
        // Set Title
        tvToolbarTitle.text = getString(R.string.title_forgot_password)

        // Set OnClick Listener
//        btSubmit.setOnClickListener {
//            if (targetFragment is LoginFragment) {
//                (targetFragment as LoginFragment).recoverPassword(etEmail.text.toString().trim())
//            }
//        }
    }

    override val isFullScreenDialog: Boolean
        get() = false
}
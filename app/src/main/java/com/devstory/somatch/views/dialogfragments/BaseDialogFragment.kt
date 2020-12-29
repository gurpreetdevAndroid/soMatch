package com.devstory.somatch.views.dialogfragments

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.devstory.somatch.R
import kotlinx.android.synthetic.main.toolbar_dialog_fragments.*

/**
 * Created by Mukesh on 29/6/18.
 */
abstract class BaseDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        return inflater.inflate(layoutId, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme)

        // Making fragment dialog transparent and of fullscreen width
        val dialog = dialog
        if (null != dialog) {
            if (isFullScreenDialog) {
                dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT)
            } else {
                dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
            }
            dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
            dialog.window!!.setWindowAnimations(R.style.DialogFragmentAnimations)
        }

        // Set Toolbar
        if (null != toolbar) {
            toolbar.setNavigationIcon(R.drawable.ic_close_white)
            toolbar.setNavigationOnClickListener { dismiss() }
        }

        init()
    }

    abstract val isFullScreenDialog: Boolean

    abstract val layoutId: Int

    abstract fun init()
}
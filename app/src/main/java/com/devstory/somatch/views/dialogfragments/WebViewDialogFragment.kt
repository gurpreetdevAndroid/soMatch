package com.devstory.somatch.views.dialogfragments

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.devstory.somatch.R
import kotlinx.android.synthetic.main.dialogfragment_webview.*
import kotlinx.android.synthetic.main.toolbar_dialog_fragments.*

/**
 * Created by Mukesh on 29/6/18.
 */
class WebViewDialogFragment : BaseDialogFragment() {

    companion object {

        private const val BUNDLE_EXTRAS_TITLE = "title"
        private const val BUNDLE_EXTRAS_URL = "url"

        fun newInstance(title: String, url: String): WebViewDialogFragment {
            val webViewFragment = WebViewDialogFragment()
            val bundle = Bundle()
            bundle.putString(BUNDLE_EXTRAS_TITLE, title)
            bundle.putString(BUNDLE_EXTRAS_URL, url)
            webViewFragment.arguments = bundle
            return webViewFragment
        }
    }

    override val layoutId: Int
        get() = R.layout.dialogfragment_webview

    override fun init() {
        // get arguments
        if (null != arguments) {
            tvToolbarTitle.text = arguments!!.getString(BUNDLE_EXTRAS_TITLE, "")
            webView.loadUrl(arguments!!.getString(BUNDLE_EXTRAS_URL, ""))
        }

        // Enable javascript
        webView.settings.javaScriptEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true

        // Set WebView client listener
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                progressBar?.visibility = View.GONE
            }
        }
    }

    override val isFullScreenDialog: Boolean
        get() = true

    override fun onPause() {
        webView.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        webView.onResume()
    }

    override fun onDestroyView() {
        webView.destroy()
        super.onDestroyView()
    }
}
package com.devstory.somatch.views.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.devstory.somatch.R
import com.devstory.somatch.utils.MyCustomLoader
import com.devstory.somatch.viewmodels.BaseViewModel
import com.devstory.somatch.views.activities.BaseAppCompactActivity
import com.devstory.somatch.views.activities.MainActivity
import com.devstory.somatch.views.activities.inflate
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by Mukesh on 19/3/18.
 */
abstract class BaseFragment : Fragment() {

    private val mMyCustomLoader: MyCustomLoader by lazy { MyCustomLoader(context) }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            container?.inflate(layoutRes = layoutId)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Set Toolbar
        if (null != toolbar) {
            toolbar.setNavigationIcon(R.drawable.ic_back)
            toolbar.setNavigationOnClickListener {
                hideSoftKeyboard()
                (activityContext as BaseAppCompactActivity).onBackPressed()
            }
        }

        init()
        observeBaseProperties()
    }

    private fun observeBaseProperties() {
        // Observe message
        viewModel?.getSuccessMessage()?.observe(this, Observer {
            showMessage(null, it)
        })

        // Observe any general exception
        viewModel?.getErrorHandler()?.observe(this, Observer {
            if (null != it) {
                showMessage(resId = it.getErrorResource(), isShowSnackbarMessage = false)
            }
        })

        // Observe user session expiration
        viewModel?.isSessionExpired()?.observe(this, Observer {
            if (it!!) {
                expireUserSession()
            }
        })

        // Observe visibility of loader
        viewModel?.isShowLoader()?.observe(this, Observer {
            if (it!!) {
                showProgressLoader()
            } else {
                hideProgressLoader()
            }
        })

        // Observe retrofit error messages
        viewModel?.getRetrofitErrorMessage()?.observe(this, Observer {
            showMessage(resId = it?.errorResId, message = it?.errorMessage, isShowSnackbarMessage = false)
        })

        // Observe screen specific data
        observeProperties()
    }

    val activityContext: Context
        get() = activity!!

    fun showMessage(resId: Int? = null, message: String? = null, isShowSnackbarMessage: Boolean = false) {
        if (isShowSnackbarMessage) {
            mMyCustomLoader.showSnackBar(view, message ?: getString(resId!!))
        } else {
            mMyCustomLoader.showToast(message ?: getString(resId!!))
        }
    }

    fun dismissDialogFragment() {
        (fragmentManager!!.findFragmentByTag(getString(R.string.dialog)) as DialogFragment).dismiss()
    }

    protected fun navigateToMainActivity() {
        startActivity(Intent(activityContext, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
        activity?.finish()
    }

    protected fun showProgressLoader() {
        mMyCustomLoader.showProgressDialog()
    }

    protected fun hideProgressLoader() {
        mMyCustomLoader.dismissProgressDialog()
    }

    private fun expireUserSession() {
        showMessage(R.string.session_expired, null,
                false)
        startActivity(Intent(activity, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    fun hideSoftKeyboard() {
        val inputMethodManager = activityContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    fun showSoftKeyboard() {
        (activityContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    abstract val layoutId: Int

    abstract fun init()

    abstract val viewModel: BaseViewModel?

    abstract fun observeProperties()

}
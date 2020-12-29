package com.devstory.somatch.views.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.AnimatorRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.devstory.somatch.R
import com.devstory.somatch.utils.GeneralFunctions


abstract class BaseAppCompactActivity : AppCompatActivity() {

    companion object {
        const val INTENT_EXTRAS_IS_FROM_NOTIFICATION = "isFromNotification"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        if (GeneralFunctions.isAboveLollipopDevice) {
            val window = window
            if (isMakeStatusBarTransparent) {
                window.statusBarColor = ContextCompat.getColor(this, R.color.colorTransparent)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
        }
        init()
    }

    override fun onBackPressed() {
        if (null != supportFragmentManager && 1 < supportFragmentManager.backStackEntryCount) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            finish()
//            super.onBackPressed()
        }
    }

    fun closeActivity() {
        /**
         * Check if the activity was opened by notification then move to HomeActivity class else
         * normally move to the last fragment or activity in the stack
         */
        if (intent.getBooleanExtra(INTENT_EXTRAS_IS_FROM_NOTIFICATION, false)) {
            startActivity(Intent(this, HomeActivity::class.java)
                    .putExtra(INTENT_EXTRAS_IS_FROM_NOTIFICATION, true)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        } else {
            super.finish()
        }

    }

    abstract val layoutId: Int

    abstract val isMakeStatusBarTransparent: Boolean

    abstract fun init()

}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun AppCompatActivity.doFragmentTransaction(fragManager: FragmentManager = supportFragmentManager,
                                            @IdRes containerViewId: Int,
                                            fragment: Fragment,
                                            tag: String = "",
                                            @AnimatorRes enterAnimation: Int = 0,
                                            @AnimatorRes exitAnimation: Int = 0,
                                            @AnimatorRes popEnterAnimation: Int = 0,
                                            @AnimatorRes popExitAnimation: Int = 0,
                                            isAddFragment: Boolean = true,
                                            isAddToBackStack: Boolean = true,
                                            allowStateLoss: Boolean = false) {

    val fragmentTransaction = fragManager.beginTransaction()
            .setCustomAnimations(enterAnimation, exitAnimation, popEnterAnimation, popExitAnimation)

    if (isAddFragment) {
        fragmentTransaction.add(containerViewId, fragment, tag)
    } else {
        fragmentTransaction.replace(containerViewId, fragment, tag)
    }

    if (isAddToBackStack) {
        fragmentTransaction.addToBackStack(null)
    }

    if (allowStateLoss) {
        fragmentTransaction.commitAllowingStateLoss()
    } else {
        fragmentTransaction.commit()
    }
}

fun AppCompatActivity.openShareDialog(shareHeading: String = getString(R.string.share_via),
                                      shareSubject: String = getString(R.string.app_name),
                                      messageToShare: String) {
    val share = android.content.Intent(android.content.Intent.ACTION_SEND)
    share.type = "text/plain"
    share.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSubject)
    share.putExtra(android.content.Intent.EXTRA_TEXT, messageToShare)
    startActivity(android.content.Intent.createChooser(share, shareHeading))
}

//fun Menu.changeItemsFont(context: Context) {
//    for (i in 0 until this.size()) {
//        val menuItem = this.getItem(i)
//
//        val spannableString = SpannableString(menuItem.title)
//        val endSpan = spannableString.length
//
//        // Set Typeface span
//        spannableString.setSpan(CustomTypefaceSpan(ResourcesCompat.getFont(context,
//                R.font.font_mont_regular)), 0, endSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//        // Set color span
//        spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(context,
//                R.color.colorMenuItem)), 0, endSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//        menuItem.title = spannableString
//    }
//}

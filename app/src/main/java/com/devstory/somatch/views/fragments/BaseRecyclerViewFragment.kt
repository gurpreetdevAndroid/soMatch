package com.devstory.somatch.views.fragments

import android.os.Handler
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devstory.somatch.R
import com.devstory.somatch.views.utils.DefaultDividerItemDecoration
import kotlinx.android.synthetic.main.fragment_base_recycler_view.*


/**
 * Created by Mukesh on 20/3/18.
 */
abstract class BaseRecyclerViewFragment : BaseFragment() {

    protected val mDividerItemDecoration by lazy {
        DefaultDividerItemDecoration(LinearLayoutManager.VERTICAL,
                ContextCompat.getDrawable(activityContext,
                        R.drawable.drawable_recyclerview_divider)!!)
    }

    override fun init() {
        // Set SwipeRefreshLayout
        if (null != swipeRefreshLayout) {
            swipeRefreshLayout!!.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent,
                    R.color.colorAccent, R.color.colorAccent)
            swipeRefreshLayout!!.setOnRefreshListener { onPullDownToRefresh() }
        }

        // Set RecylerView
        recyclerView.layoutManager = if (null == layoutManager) LinearLayoutManager(activity) else (layoutManager)

        if (isShowRecyclerViewDivider) {
            recyclerView.addItemDecoration(mDividerItemDecoration)
        }

        recyclerView.adapter = recyclerViewAdapter
        setData()

        // Observe SwipeRefreshLayout
        viewModel?.isShowSwipeRefreshLayout()?.observe(this, Observer {
            if (it!!) {
                showSwipeRefreshLoader()
            } else {
                hideSwipeRefreshLoader()
            }
        })

        // Observe retrofit errors
        viewModel?.getRetrofitErrorDataMessage()?.observe(this, Observer {
            showNoDataText(it?.errorResId, it?.errorMessage)
        })
    }

    fun showNoDataText(resId: Int? = null, message: String? = null) {
        if (null == resId && null == message) {
            hideNoDataText()
        } else {
            if (getDefaultAdapterCount() < recyclerViewAdapter?.itemCount!!) {
                showMessage(resId, message)
            } else {
                tvNoData?.visibility = View.VISIBLE
                tvNoData?.text = message ?: getString(resId!!)
            }
        }
    }

    private fun getDefaultAdapterCount(): Int {
        return 0
    }

    private fun hideNoDataText() {
        tvNoData?.visibility = View.GONE
    }

    private fun showSwipeRefreshLoader() {
        swipeRefreshLayout?.post {
            if (null != swipeRefreshLayout) {
                swipeRefreshLayout!!.isRefreshing = true
            }
        }
    }

    private fun hideSwipeRefreshLoader() {
        Handler().postDelayed({
            if (null != swipeRefreshLayout && swipeRefreshLayout!!.isRefreshing) {
                swipeRefreshLayout!!.isRefreshing = false
            }
        }, 50)
    }

    abstract fun setData()

    abstract val recyclerViewAdapter: RecyclerView.Adapter<*>?

    abstract val layoutManager: RecyclerView.LayoutManager?

    abstract val isShowRecyclerViewDivider: Boolean

    abstract fun onPullDownToRefresh()

}
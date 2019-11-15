package com.cts.avin.ui.main

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import android.util.Log
import android.view.View
import android.widget.TextView

import com.cts.avin.R
import com.cts.avin.adapter.AboutListAdapter
import com.cts.avin.base.BaseFragment
import com.cts.avin.data.main.ListData
import com.cts.avin.data.main.Rows
import com.cts.avin.adapter.AboutListItemSelectedListener
import com.cts.avin.util.ViewModelFactory
import com.cts.avin.viewmodel.AboutListViewModel

import javax.inject.Inject

import butterknife.BindView

class AboutListFragment : BaseFragment(), AboutListItemSelectedListener {

    private val TAG = AboutListFragment::class.java!!.getName()

    @BindView(R.id.recyclerView)
    internal var mListView: RecyclerView? = null
    @BindView(R.id.tv_error)
    internal var mErrorTextView: TextView? = null
    @BindView(R.id.loading_view)
    internal var mLoadingView: View? = null
    @BindView(R.id.swipe_refresh)
    internal var mSwipeRefresh: SwipeRefreshLayout? = null
    @Inject
    internal var mViewModelFactory: ViewModelFactory? = null
    private var mViewModel: AboutListViewModel? = null
    private var mMainListData: ListData? = null
    private var mAboutListAdapter: AboutListAdapter? = null

    override fun layoutRes(): Int {
        return R.layout.aboutlist_fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AboutListViewModel::class.java!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mSwipeRefresh!!.setOnRefreshListener { makeMainListCall() }

        mSwipeRefresh!!.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mListView!!.addItemDecoration(DividerItemDecoration(baseActivity!!, DividerItemDecoration.VERTICAL))
        mAboutListAdapter = AboutListAdapter(this)
        mListView!!.adapter = mAboutListAdapter
    }

    override fun onResume() {
        super.onResume()
        if (mMainListData == null) {
            makeMainListCall()
        } else {
            mLoadingView!!.visibility = View.GONE
        }

    }

    /*
     * Method to call the ViewModel which will hit the API & will give the response.
     * */
    internal fun makeMainListCall() {

        mViewModel!!.progressDialog.observe(this, Observer{ isLoading ->
            if (isLoading != null) {
                mLoadingView!!.visibility = if (isLoading) View.VISIBLE else View.GONE
                if (isLoading!!) {
                    mErrorTextView!!.visibility = View.GONE
                    mListView!!.visibility = View.GONE
                }
            }
        })
        mViewModel!!.mainListData.observe(this, Observer { listData ->
            mSwipeRefresh!!.isRefreshing = false
            activity!!.title = listData!!.title
            mMainListData = listData
            mAboutListAdapter!!.data = mMainListData!!.rows!!
            mErrorTextView!!.visibility = View.GONE
            mListView!!.visibility = View.VISIBLE
        })

        mViewModel!!.errorMsg.observe(this, Observer {
            mSwipeRefresh!!.isRefreshing = false
            mErrorTextView!!.visibility = View.VISIBLE
            mListView!!.visibility = View.GONE
        })
        mViewModel!!.makeMainListCall()
    }

    override fun onItemSelected(data: Rows) {
        Log.d(TAG, "selected item is : " + data.title!!)
    }

    companion object {

        fun newInstance(): AboutListFragment {
            return AboutListFragment()
        }
    }
}

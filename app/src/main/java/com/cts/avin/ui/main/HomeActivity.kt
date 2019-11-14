package com.cts.avin.ui.main

import android.os.Bundle

import com.cts.avin.R
import com.cts.avin.base.BaseActivity

class HomeActivity : BaseActivity() {
    override fun layoutRes(): Int {
        return R.layout.home_activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            addFragment(AboutListFragment.newInstance(), R.id.container)
        }
    }
}

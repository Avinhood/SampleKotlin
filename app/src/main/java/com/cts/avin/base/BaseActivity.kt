package com.cts.avin.base

import android.os.Bundle

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

import butterknife.ButterKnife
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {
    @LayoutRes
    protected abstract fun layoutRes(): Int

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes())
        ButterKnife.bind(this)
    }

    /**
     * Add a fragment to the given container.
     *
     * @param fragment
     * @param containerId
     */
    fun addFragment(fragment: Fragment, @IdRes containerId: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(containerId, fragment)
        transaction.commit()
    }
}

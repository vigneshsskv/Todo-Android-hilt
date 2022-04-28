package com.vignesh.todo.common

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint


sealed interface LNFragmentCallback {
    fun replaceFragment(containerViewId: Int, fragment: Fragment, addToBackStack: Boolean)
    fun popCurrentFragment()
    fun setToolbar(homeButton: Boolean = false, title: String, drawable: Drawable?)
}

@AndroidEntryPoint
abstract class LNActivity : AppCompatActivity(), LNFragmentCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun popCurrentFragment() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    override fun replaceFragment(
        containerViewId: Int,
        fragment: Fragment,
        addToBackStack: Boolean
    ) {
        supportFragmentManager.beginTransaction().also {
            it.replace(containerViewId, fragment)
            if (addToBackStack) {
                it.addToBackStack(null)
            }
        }.commit()
    }

    override fun onBackPressed() {
        popCurrentFragment()
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == android.R.id.home) {
            popCurrentFragment()
            return false
        }
        return super.onOptionsItemSelected(menuItem)
    }
}
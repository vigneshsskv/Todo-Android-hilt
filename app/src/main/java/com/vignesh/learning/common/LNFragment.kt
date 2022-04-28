package com.vignesh.learning.common

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class LNFragment : Fragment() {
    private var fragmentCallback: LNFragmentCallback? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentCallback = context as? LNFragmentCallback
    }

    protected fun replaceFragment(
        containerViewId: Int,
        fragment: Fragment,
        addToBackStack: Boolean = false
    ) = fragmentCallback?.replaceFragment(containerViewId, fragment, addToBackStack)

    protected fun popCurrentFragment() = fragmentCallback?.popCurrentFragment()

    protected fun setToolbar(homeButton: Boolean = false, title: String, drawable: Drawable?=null) =
        fragmentCallback?.setToolbar(homeButton = homeButton, title = title, drawable = drawable)
}
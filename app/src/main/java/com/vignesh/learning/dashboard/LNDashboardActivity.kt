package com.vignesh.learning.dashboard

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import com.vignesh.learning.R
import com.vignesh.learning.common.LNActivity
import com.vignesh.learning.dashboard.todolist.LNTodoListFragment
import com.vignesh.learning.databinding.LnActivityDashboardBinding

class LNDashboardActivity : LNActivity() {
    private lateinit var dashboardScreen: LnActivityDashboardBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardScreen = LnActivityDashboardBinding.inflate(layoutInflater)
        setContentView(dashboardScreen.root)
        replaceFragment(R.id.fl_dashboard, LNTodoListFragment.newInstance(), false)
    }

    override fun setToolbar(homeButton: Boolean, title: String, drawable: Drawable?) {
        setSupportActionBar(dashboardScreen.tlToolbar)
        supportActionBar?.apply {
            this.title = title
            setDisplayHomeAsUpEnabled(homeButton)
            drawable?.let {
                setHomeAsUpIndicator(it)
            }
        }
    }
}
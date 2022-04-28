package com.vignesh.todo.dashboard

import android.graphics.drawable.Drawable
import android.os.Bundle
import com.vignesh.todo.R
import com.vignesh.todo.common.LNActivity
import com.vignesh.todo.dashboard.todolist.TDTodoListFragment
import com.vignesh.todo.databinding.TdActivityDashboardBinding

class TDDashboardActivity : LNActivity() {
    private lateinit var dashboardScreen: TdActivityDashboardBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardScreen = TdActivityDashboardBinding.inflate(layoutInflater)
        setContentView(dashboardScreen.root)
        replaceFragment(R.id.fl_dashboard, TDTodoListFragment.newInstance(), false)
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
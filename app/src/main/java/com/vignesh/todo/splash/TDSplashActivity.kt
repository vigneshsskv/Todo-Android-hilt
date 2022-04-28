package com.vignesh.todo.splash

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.vignesh.todo.common.LNActivity
import com.vignesh.todo.dashboard.TDDashboardActivity
import com.vignesh.todo.databinding.TdActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

class TDSplashActivity : LNActivity() {
    private lateinit var splashScreen: TdActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreen = TdActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashScreen.root)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@TDSplashActivity, TDDashboardActivity::class.java))
            finish()
        }, 3000L)
    }

    override fun setToolbar(homeButton: Boolean, title: String, drawable: Drawable?) {}
}
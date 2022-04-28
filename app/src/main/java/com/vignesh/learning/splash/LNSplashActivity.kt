package com.vignesh.learning.splash

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.vignesh.learning.common.LNActivity
import com.vignesh.learning.dashboard.LNDashboardActivity
import com.vignesh.learning.databinding.LnActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LNSplashActivity : LNActivity() {
    private lateinit var splashScreen: LnActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreen = LnActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashScreen.root)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@LNSplashActivity, LNDashboardActivity::class.java))
            finish()
        }, 3000L)
    }

    override fun setToolbar(homeButton: Boolean, title: String, drawable: Drawable?) {}
}
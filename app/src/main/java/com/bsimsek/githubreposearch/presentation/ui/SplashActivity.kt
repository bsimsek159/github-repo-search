package com.bsimsek.githubreposearch.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.bsimsek.githubreposearch.R
import com.bsimsek.githubreposearch.core.presentation.base.BaseActivity

class SplashActivity : BaseActivity() {
    override fun getLayoutRes(): Int = R.layout.splash_screen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({goToMainActivity()}, SPLASH_TIME)
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        const val SPLASH_TIME = 500L
    }
}
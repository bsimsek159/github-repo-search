package com.bsimsek.githubreposearch.presentation.ui

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    companion object {
        const val SPLASH_TIME = 2000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({ goToMainActivity() }, SPLASH_TIME)
    }

    private fun goToMainActivity() {
        val intent = MainActivity.getStartIntent(this)

        startActivity(intent)
        finish()
    }
}
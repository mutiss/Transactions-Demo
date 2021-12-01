package com.carlosblaya.transactionsdemo.ui.splash

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.carlosblaya.transactionsdemo.databinding.ActivitySplashBinding
import com.carlosblaya.transactionsdemo.ui.main.MainActivity
import com.carlosblaya.transactionsdemo.util.Konsts
import com.carlosblaya.transactionsdemo.util.createIntent
import com.carlosblaya.transactionsdemo.util.viewBinding

class SplashActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivitySplashBinding::inflate)

    private var mHandler = Handler()
    private var mRunnable: Runnable = Runnable {
        startActivity(createIntent<MainActivity>())
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mHandler.postDelayed(mRunnable, Konsts.SPLASH_TIME_OUT)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mHandler.removeCallbacks(mRunnable)
    }
}
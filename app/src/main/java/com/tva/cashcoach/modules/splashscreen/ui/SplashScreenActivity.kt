package com.tva.cashcoach.modules.splashscreen.ui

import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.databinding.ActivitySplashScreenBinding
import com.tva.cashcoach.modules.login.ui.LoginActivity
import com.tva.cashcoach.modules.splashscreen.data.viewmodel.SplashScreenVM

class SplashScreenActivity :
    BaseActivity<ActivitySplashScreenBinding>(R.layout.activity_splash_screen) {
    private val viewModel: SplashScreenVM by viewModels<SplashScreenVM>()

    override fun onInitialized(): Unit {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.splashScreenVM = viewModel
        Handler(Looper.getMainLooper()).postDelayed({
            val destIntent = LoginActivity.getIntent(this, null)
            startActivity(destIntent)
            finish()
        }, 3000)
    }

    override fun setUpClicks(): Unit {
    }

    companion object {
        const val TAG: String = "SPLASH_SCREEN_ACTIVITY"

    }
}

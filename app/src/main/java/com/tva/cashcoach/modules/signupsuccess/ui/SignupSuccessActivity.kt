package com.tva.cashcoach.modules.signupsuccess.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.databinding.ActivitySignupSuccessBinding
import com.tva.cashcoach.modules.homescreencontainer.ui.HomeScreenContainerActivity
import com.tva.cashcoach.modules.signupsuccess.data.viewmodel.SignupSuccessVM

class SignupSuccessActivity :
    BaseActivity<ActivitySignupSuccessBinding>(R.layout.activity_signup_success) {
    private val viewModel: SignupSuccessVM by viewModels()

    override fun onInitialized() {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.signupSuccessVM = viewModel

        Handler().postDelayed({
            val intent = Intent(this, HomeScreenContainerActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }

    override fun setUpClicks() {
    }

    companion object {
        const val TAG: String = "SIGNUP_SUCCESS_ACTIVITY"

        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, SignupSuccessActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }
}

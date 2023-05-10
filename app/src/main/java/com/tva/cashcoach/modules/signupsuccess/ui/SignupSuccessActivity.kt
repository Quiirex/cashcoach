package com.tva.cashcoach.modules.signupsuccess.ui

import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.databinding.ActivitySignupSuccessBinding
import com.tva.cashcoach.modules.signupsuccess.data.viewmodel.SignupSuccessVM

class SignupSuccessActivity :
    BaseActivity<ActivitySignupSuccessBinding>(R.layout.activity_signup_success) {
    private val viewModel: SignupSuccessVM by viewModels<SignupSuccessVM>()

    override fun onInitialized(): Unit {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.signupSuccessVM = viewModel
    }

    override fun setUpClicks(): Unit {
    }

    companion object {
        const val TAG: String = "SIGNUP_SUCCESS_ACTIVITY"

    }
}

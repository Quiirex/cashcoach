package com.tva.cashcoach.modules.forgotpassword.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.databinding.ActivityForgotPasswordBinding
import com.tva.cashcoach.modules.forgotpassword.data.viewmodel.ForgotPasswordVM

class ForgotPasswordActivity :
    BaseActivity<ActivityForgotPasswordBinding>(R.layout.activity_forgot_password) {
    private val viewModel: ForgotPasswordVM by viewModels()

    override fun onInitialized() {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.forgotPasswordVM = viewModel
    }

    override fun setUpClicks() {
        binding.imageBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val TAG: String = "FORGOT_PASSWORD_ACTIVITY"


        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, ForgotPasswordActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }
}

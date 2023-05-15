package com.tva.cashcoach.modules.login.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.appcomponents.googleauth.GoogleHelper
import com.tva.cashcoach.databinding.ActivityLoginBinding
import com.tva.cashcoach.modules.forgotpassword.ui.ForgotPasswordActivity
import com.tva.cashcoach.modules.homescreencontainer.ui.HomeScreenContainerActivity
import com.tva.cashcoach.modules.login.data.viewmodel.LoginVM
import com.tva.cashcoach.modules.signup.ui.SignUpActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel: LoginVM by viewModels()

    private val REQUEST_CODE_SIGN_UP_ACTIVITY: Int = 265

    private lateinit var googleLogin: GoogleHelper

    private val REQUEST_CODE_HOME_SCREEN_CONTAINER_ACTIVITY: Int = 303

    private val REQUEST_CODE_FORGOT_PASSWORD_ACTIVITY: Int = 984

    override fun onInitialized() {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.loginVM = viewModel
        googleLogin = GoogleHelper(this,
            { accountInfo ->
            }, { error ->

            })
    }

    override fun setUpClicks() {
        binding.txtConfirmation.setOnClickListener {
            val destIntent = SignUpActivity.getIntent(this, null)
            startActivityForResult(destIntent, REQUEST_CODE_SIGN_UP_ACTIVITY)
        }
        binding.linearButtonLoginGoogle.setOnClickListener {
            googleLogin.login()
        }
        binding.btnLogin.setOnClickListener {
            val destIntent = HomeScreenContainerActivity.getIntent(this, null)
            startActivityForResult(destIntent, REQUEST_CODE_HOME_SCREEN_CONTAINER_ACTIVITY)
        }
        binding.txtForgotPassword.setOnClickListener {
            val destIntent = ForgotPasswordActivity.getIntent(this, null)
            startActivityForResult(destIntent, REQUEST_CODE_FORGOT_PASSWORD_ACTIVITY)
        }
    }

    companion object {
        const val TAG: String = "LOGIN_ACTIVITY"

        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, LoginActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }
}

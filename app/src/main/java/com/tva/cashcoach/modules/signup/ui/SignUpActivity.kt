package com.tva.cashcoach.modules.signup.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.appcomponents.googleauth.GoogleHelper
import com.tva.cashcoach.databinding.ActivitySignUpBinding
import com.tva.cashcoach.modules.accountsetup.ui.AccountSetupActivity
import com.tva.cashcoach.modules.login.ui.LoginActivity
import com.tva.cashcoach.modules.signup.data.viewmodel.SignUpVM

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val viewModel: SignUpVM by viewModels<SignUpVM>()

    private val REQUEST_CODE_LOGIN_ACTIVITY: Int = 544


    private val REQUEST_CODE_ACCOUNT_SETUP_ACTIVITY: Int = 264


    private lateinit var googleLogin: GoogleHelper

    override fun onInitialized(): Unit {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.signUpVM = viewModel
        googleLogin = GoogleHelper(this,
            { accountInfo ->
            }, { error ->

            })
    }

    override fun setUpClicks(): Unit {
        binding.linearButtonGoogleSignUp.setOnClickListener {
            googleLogin.login()
        }
        binding.imageBack.setOnClickListener {
            finish()
        }
        binding.txtConfirmation.setOnClickListener {
            val destIntent = LoginActivity.getIntent(this, null)
            startActivityForResult(destIntent, REQUEST_CODE_LOGIN_ACTIVITY)
        }
        binding.btnSignUp.setOnClickListener {
            val destIntent = AccountSetupActivity.getIntent(this, null)
            startActivityForResult(destIntent, REQUEST_CODE_ACCOUNT_SETUP_ACTIVITY)
        }
    }

    companion object {
        const val TAG: String = "SIGN_UP_ACTIVITY"


        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, SignUpActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }
}

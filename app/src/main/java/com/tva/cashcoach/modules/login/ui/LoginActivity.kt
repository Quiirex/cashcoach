package com.tva.cashcoach.modules.login.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.auth.AuthHelper
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.appcomponents.googleauth.GoogleAuthHelper
import com.tva.cashcoach.appcomponents.persistence.repository.user.UserRepository
import com.tva.cashcoach.databinding.ActivityLoginBinding
import com.tva.cashcoach.modules.accountsetup.ui.AccountSetupActivity
import com.tva.cashcoach.modules.forgotpassword.ui.ForgotPasswordActivity
import com.tva.cashcoach.modules.homescreencontainer.ui.HomeScreenContainerActivity
import com.tva.cashcoach.modules.login.data.viewmodel.LoginVM
import com.tva.cashcoach.modules.signup.ui.SignUpActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel: LoginVM by viewModels()

    private val REQUEST_CODE_SIGN_UP_ACTIVITY: Int = 265

    private val REQUEST_CODE_HOME_SCREEN_CONTAINER_ACTIVITY: Int = 303

    private val REQUEST_CODE_FORGOT_PASSWORD_ACTIVITY: Int = 984

    private val REQUEST_CODE_ACCOUNT_SETUP_ACTIVITY: Int = 264

    private lateinit var googleAuth: GoogleAuthHelper

    private lateinit var auth: AuthHelper


    override fun onInitialized() {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.loginVM = viewModel
        val userDao = appDb.getUserDao()
        val userRepository = UserRepository(userDao)
        googleAuth = GoogleAuthHelper(this,
            { _, reqSetup ->
                if (!reqSetup) {
                    val destIntent = HomeScreenContainerActivity.getIntent(this, null)
                    startActivityForResult(destIntent, REQUEST_CODE_HOME_SCREEN_CONTAINER_ACTIVITY)
                } else {
                    val destIntent = AccountSetupActivity.getIntent(this, null)
                    startActivityForResult(destIntent, REQUEST_CODE_ACCOUNT_SETUP_ACTIVITY)
                }
            }, {
                Toast.makeText(
                    this,
                    getString(R.string.an_error_occurred_please_try_again_later),
                    Toast.LENGTH_SHORT
                ).show()
            }, appDb, preferenceHelper, userRepository
        )
        auth = AuthHelper(this,
            {
                val destIntent = HomeScreenContainerActivity.getIntent(this, null)
                startActivityForResult(destIntent, REQUEST_CODE_HOME_SCREEN_CONTAINER_ACTIVITY)
            }, { errorCode ->
                when (errorCode) {
                    "ERROR_INVALID_CREDENTIALS" -> {
                        binding.etInputPassword.error =
                            getString(R.string.error_invalid_credentials)
                        binding.etInputEmail.error = getString(R.string.error_invalid_credentials)
                        binding.etInputPassword.requestFocus()
                    }

                    else -> {
                        Toast.makeText(
                            this,
                            getString(R.string.an_error_occurred_please_try_again_later),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }, appDb, preferenceHelper, userRepository
        )
        if (preferenceHelper.getString("curr_user_uid", "") != "") {
            if (preferenceHelper.getBoolean("googleSignedIn", false)) {
                googleAuth.signOut {
                    preferenceHelper.removeAllValues()
                }
            }
        }
    }

    override fun setUpClicks() {
        binding.txtConfirmation.setOnClickListener {
            val destIntent = SignUpActivity.getIntent(this, null)
            startActivityForResult(destIntent, REQUEST_CODE_SIGN_UP_ACTIVITY)
        }
        binding.linearButtonLoginGoogle.setOnClickListener {
            googleAuth.login()
        }
        binding.btnLogin.setOnClickListener {
            val email = binding.etInputEmail.text.toString()
            val password = binding.etInputPassword.text.toString()

            if (email.isEmpty()) {
                binding.etInputEmail.error = getString(R.string.email_is_required)
                binding.etInputEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.etInputPassword.error = getString(R.string.password_is_required)
                binding.etInputPassword.requestFocus()
                return@setOnClickListener
            }

            auth.login(email, password)
        }
        binding.txtForgotPassword.setOnClickListener {
            val destIntent = ForgotPasswordActivity.getIntent(this, null)
            startActivityForResult(destIntent, REQUEST_CODE_FORGOT_PASSWORD_ACTIVITY)
        }
    }

    override fun onBackPressed() {}

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_CODE_HOME_SCREEN_CONTAINER_ACTIVITY -> {
                binding.etInputEmail.text.clear()
                binding.etInputPassword.text.clear()
                binding.etInputEmail.clearFocus()
                binding.etInputPassword.clearFocus()
            }

            REQUEST_CODE_SIGN_UP_ACTIVITY -> {
                binding.etInputEmail.text.clear()
                binding.etInputPassword.text.clear()
                binding.etInputEmail.clearFocus()
                binding.etInputPassword.clearFocus()
            }

            REQUEST_CODE_FORGOT_PASSWORD_ACTIVITY -> {
                binding.etInputEmail.text.clear()
                binding.etInputPassword.text.clear()
                binding.etInputEmail.clearFocus()
                binding.etInputPassword.clearFocus()
            }
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

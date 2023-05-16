package com.tva.cashcoach.modules.signup.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.appcomponents.googleauth.GoogleHelper
import com.tva.cashcoach.databinding.ActivitySignUpBinding
import com.tva.cashcoach.modules.accountsetup.ui.AccountSetupActivity
import com.tva.cashcoach.modules.login.ui.LoginActivity
import com.tva.cashcoach.modules.signup.data.viewmodel.SignUpVM

class SignUpActivity : BaseActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val viewModel: SignUpVM by viewModels()

    private val REQUEST_CODE_LOGIN_ACTIVITY: Int = 544


    private val REQUEST_CODE_ACCOUNT_SETUP_ACTIVITY: Int = 264


    private lateinit var googleLogin: GoogleHelper

    override fun onInitialized() {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.signUpVM = viewModel
        googleLogin = GoogleHelper(this,
            { accountInfo ->
            }, { error ->

            })
    }

    override fun setUpClicks() {
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
            val name = binding.etInputName.text.toString().trim()
            val surname = binding.etInputSurname.text.toString().trim()
            val email = binding.etInputEmail.text.toString().trim()
            val password = binding.etInputPassword.text.toString().trim()
            val passwordOne = binding.etInputPasswordOne.text.toString().trim()
            val termsChecked = binding.checkBoxTerms.isChecked

            if (name.isEmpty()) {
                binding.etInputName.error = getString(R.string.error_field_required)
                binding.etInputName.requestFocus()
                return@setOnClickListener
            }

            if (surname.isEmpty()) {
                binding.etInputSurname.error = getString(R.string.error_field_required)
                binding.etInputSurname.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                binding.etInputEmail.error = getString(R.string.error_field_required)
                binding.etInputEmail.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.etInputEmail.error = getString(R.string.error_invalid_email)
                binding.etInputEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.etInputPassword.error = getString(R.string.error_field_required)
                binding.etInputPassword.requestFocus()
                return@setOnClickListener
            }

            if (passwordOne.isEmpty()) {
                binding.etInputPasswordOne.error = getString(R.string.error_field_required)
                binding.etInputPasswordOne.requestFocus()
                return@setOnClickListener
            }

            if (password != passwordOne) {
                binding.etInputPasswordOne.error = getString(R.string.error_passwords_mismatch)
                binding.etInputPasswordOne.requestFocus()
                return@setOnClickListener
            }

            if (!termsChecked) {
                binding.checkBoxTerms.error = getString(R.string.error_terms_not_checked)
                binding.checkBoxTerms.requestFocus()
                return@setOnClickListener
            }

            // Validation passed, proceed with sign up
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

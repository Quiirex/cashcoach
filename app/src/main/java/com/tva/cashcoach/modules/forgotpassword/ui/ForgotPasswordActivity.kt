package com.tva.cashcoach.modules.forgotpassword.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.auth.AuthHelper
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.appcomponents.persistence.repository.user.UserRepository
import com.tva.cashcoach.databinding.ActivityForgotPasswordBinding
import com.tva.cashcoach.modules.forgotpassword.data.viewmodel.ForgotPasswordVM

class ForgotPasswordActivity :
    BaseActivity<ActivityForgotPasswordBinding>(R.layout.activity_forgot_password) {
    private val viewModel: ForgotPasswordVM by viewModels()

    private lateinit var auth: AuthHelper

    override fun onInitialized() {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.forgotPasswordVM = viewModel

        val userDao = appDb.getUserDao()
        val userRepository = UserRepository(userDao)

        auth = AuthHelper(this, {}, {}, appDb, preferenceHelper, userRepository)
    }

    override fun setUpClicks() {
        binding.imageBack.setOnClickListener {
            finish()
        }
        binding.btnContinue.setOnClickListener {
            if (binding.etInputEmail.text.toString().isEmpty()) {
                binding.etInputEmail.error = getString(R.string.error_email_empty)
                binding.etInputEmail.requestFocus()
                return@setOnClickListener
            }
            auth.forgotPassword(binding.etInputEmail.text.toString(), {
                binding.etInputEmail.setText("")
                Toast.makeText(
                    this,
                    getString(R.string.password_reset_email_sent),
                    Toast.LENGTH_SHORT
                ).show()
            }, {
                binding.etInputEmail.setText("")
                Toast.makeText(
                    this,
                    getString(R.string.password_reset_email_sent),
                    Toast.LENGTH_SHORT
                ).show()
            })
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

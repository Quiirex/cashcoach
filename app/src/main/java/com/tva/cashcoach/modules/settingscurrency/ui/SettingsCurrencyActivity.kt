package com.tva.cashcoach.modules.settingscurrency.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.tva.cashcoach.R
import com.tva.cashcoach.infrastructure.base.BaseActivity
import com.tva.cashcoach.infrastructure.model.user.UserDao
import com.tva.cashcoach.infrastructure.persistence.repository.user.UserRepository
import com.tva.cashcoach.databinding.ActivitySettingsCurrencyBinding
import com.tva.cashcoach.modules.settingscurrency.data.viewmodel.SettingsCurrencyVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsCurrencyActivity :
    BaseActivity<ActivitySettingsCurrencyBinding>(R.layout.activity_settings_currency) {
    private val viewModel: SettingsCurrencyVM by viewModels()

    private lateinit var userDao: UserDao

    private lateinit var userRepository: UserRepository

    override fun onInitialized() {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.settingsCurrencyVM = viewModel

        userDao = appDb.getUserDao()
        userRepository = UserRepository(userDao)

        val currentUserId = preferenceHelper.getString("curr_user_uid", "")

        lifecycleScope.launch {
            val currentUser = withContext(Dispatchers.IO) {
                userRepository.get(currentUserId)
            }

            when (currentUser?.currency ?: "") {
                "EUR" -> binding.radioEur.isChecked = true
                "USD" -> binding.radioUsd.isChecked = true
            }
        }
    }

    override fun setUpClicks() {
        binding.imageBack.setOnClickListener {
            finish()
        }
        binding.radioEur.setOnClickListener {
            val currentUserId = preferenceHelper.getString("curr_user_uid", "")
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    userRepository.updateCurrencyByUid(currentUserId, "EUR")
                    preferenceHelper.putString("curr_user_currency", "EUR")
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }
        }
        binding.radioUsd.setOnClickListener {
            val currentUserId = preferenceHelper.getString("curr_user_uid", "")
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    userRepository.updateCurrencyByUid(currentUserId, "USD")
                    preferenceHelper.putString("curr_user_currency", "USD")
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            }
        }
    }

    companion object {
        const val TAG: String = "SETTINGS_CURRENCY_ACTIVITY"


        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, SettingsCurrencyActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }
}

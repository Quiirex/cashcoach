package com.tva.cashcoach.modules.settingscurrency.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.databinding.ActivitySettingsCurrencyBinding
import com.tva.cashcoach.modules.settingscurrency.data.viewmodel.SettingsCurrencyVM

class SettingsCurrencyActivity :
    BaseActivity<ActivitySettingsCurrencyBinding>(R.layout.activity_settings_currency) {
    private val viewModel: SettingsCurrencyVM by viewModels<SettingsCurrencyVM>()

    override fun onInitialized(): Unit {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.settingsCurrencyVM = viewModel
    }

    override fun setUpClicks(): Unit {
        binding.imageBack.setOnClickListener {
            finish()
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

package com.tva.cashcoach.modules.settings.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.databinding.ActivitySettingsBinding
import com.tva.cashcoach.modules.settings.data.viewmodel.SettingsVM
import com.tva.cashcoach.modules.settingscurrency.ui.SettingsCurrencyActivity
import com.tva.cashcoach.modules.settingslanguage.ui.SettingsLanguageActivity
import com.tva.cashcoach.modules.settingstheme.ui.SettingsThemeActivity

class SettingsActivity : BaseActivity<ActivitySettingsBinding>(R.layout.activity_settings) {
    private val viewModel: SettingsVM by viewModels<SettingsVM>()

    private val REQUEST_CODE_SETTINGS_THEME_ACTIVITY: Int = 787

    private val REQUEST_CODE_SETTINGS_LANGUAGE_ACTIVITY: Int = 171

    private val REQUEST_CODE_SETTINGS_CURRENCY_ACTIVITY: Int = 461

    override fun onInitialized(): Unit {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.settingsVM = viewModel
    }

    override fun setUpClicks(): Unit {
        binding.btnTheme.setOnClickListener {
            val destIntent = SettingsThemeActivity.getIntent(this, null)
            startActivityForResult(destIntent, REQUEST_CODE_SETTINGS_THEME_ACTIVITY)
        }
        binding.btnLanguage.setOnClickListener {
            val destIntent = SettingsLanguageActivity.getIntent(this, null)
            startActivityForResult(destIntent, REQUEST_CODE_SETTINGS_LANGUAGE_ACTIVITY)
        }
        binding.imageBack.setOnClickListener {
            finish()
        }
        binding.btnCurrency.setOnClickListener {
            val destIntent = SettingsCurrencyActivity.getIntent(this, null)
            startActivityForResult(destIntent, REQUEST_CODE_SETTINGS_CURRENCY_ACTIVITY)
        }
    }

    companion object {
        const val TAG: String = "SETTINGS_ACTIVITY"


        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, SettingsActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }
}

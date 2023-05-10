package com.tva.cashcoach.modules.settingslanguage.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.databinding.ActivitySettingsLanguageBinding
import com.tva.cashcoach.modules.settingslanguage.data.viewmodel.SettingsLanguageVM

class SettingsLanguageActivity :
    BaseActivity<ActivitySettingsLanguageBinding>(R.layout.activity_settings_language) {
    private val viewModel: SettingsLanguageVM by viewModels<SettingsLanguageVM>()

    override fun onInitialized(): Unit {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.settingsLanguageVM = viewModel
    }

    override fun setUpClicks(): Unit {
        binding.imageBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val TAG: String = "SETTINGS_LANGUAGE_ACTIVITY"


        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, SettingsLanguageActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }
}

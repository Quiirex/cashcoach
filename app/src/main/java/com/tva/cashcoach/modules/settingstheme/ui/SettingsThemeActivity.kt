package com.tva.cashcoach.modules.settingstheme.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.databinding.ActivitySettingsThemeBinding
import com.tva.cashcoach.modules.settingstheme.data.viewmodel.SettingsThemeVM

class SettingsThemeActivity :
    BaseActivity<ActivitySettingsThemeBinding>(R.layout.activity_settings_theme) {
    private val viewModel: SettingsThemeVM by viewModels<SettingsThemeVM>()

    override fun onInitialized(): Unit {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.settingsThemeVM = viewModel
    }

    override fun setUpClicks(): Unit {
        binding.imageBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val TAG: String = "SETTINGS_THEME_ACTIVITY"


        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, SettingsThemeActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }
}

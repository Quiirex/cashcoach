package com.tva.cashcoach.modules.settings.ui

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.tva.cashcoach.R
import com.tva.cashcoach.infrastructure.base.BaseActivity
import com.tva.cashcoach.infrastructure.application.App
import com.tva.cashcoach.infrastructure.model.user.UserDao
import com.tva.cashcoach.infrastructure.persistence.repository.user.UserRepository
import com.tva.cashcoach.databinding.ActivitySettingsBinding
import com.tva.cashcoach.modules.settings.data.viewmodel.SettingsVM
import com.tva.cashcoach.modules.settingscurrency.ui.SettingsCurrencyActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsActivity : BaseActivity<ActivitySettingsBinding>(R.layout.activity_settings) {
    private val viewModel: SettingsVM by viewModels()

    private val REQUEST_CODE_SETTINGS_CURRENCY_ACTIVITY: Int = 461

    private lateinit var userDao: UserDao

    private lateinit var userRepository: UserRepository

    override fun onInitialized() {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.settingsVM = viewModel

        userDao = appDb.getUserDao()
        userRepository = UserRepository(userDao)

        updateUI()
    }

    override fun setUpClicks() {
        binding.imageBack.setOnClickListener {
            finish()
        }
        binding.linearRowCurrency.setOnClickListener {
            val destIntent = SettingsCurrencyActivity.getIntent(this, null)
            startActivityForResult(destIntent, REQUEST_CODE_SETTINGS_CURRENCY_ACTIVITY)
        }
        binding.linearRowAbout.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.lbl_about))
            builder.setMessage(getString(R.string.about_app))
            builder.setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_rounded_corners)
            dialog.setOnShowListener {
                dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                    .setTextColor(resources.getColor(android.R.color.black))
            }
            dialog.show()
        }
    }

    private fun updateUI() {
        val currentUserId = preferenceHelper.getString("curr_user_uid", "")

        lifecycleScope.launch {
            val currentUser = withContext(Dispatchers.IO) {
                userRepository.get(currentUserId)
            }

            when (currentUser?.currency ?: "") {
                "EUR" -> binding.txtCurrentCurrency.text =
                    App.getInstance().resources.getString(R.string.lbl_eur)

                "USD" -> binding.txtCurrentCurrency.text =
                    App.getInstance().resources.getString(R.string.lbl_usd)
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SETTINGS_CURRENCY_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                updateUI()
            }
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

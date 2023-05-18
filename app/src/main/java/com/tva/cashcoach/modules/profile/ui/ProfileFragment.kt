package com.tva.cashcoach.modules.profile.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.fragment.app.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.auth.AuthHelper
import com.tva.cashcoach.appcomponents.base.BaseFragment
import com.tva.cashcoach.databinding.FragmentProfileBinding
import com.tva.cashcoach.modules.login.ui.LoginActivity
import com.tva.cashcoach.modules.profile.data.viewmodel.ProfileVM
import com.tva.cashcoach.modules.settings.ui.SettingsActivity
import com.tva.cashcoach.modules.wallets.ui.WalletsActivity

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val viewModel: ProfileVM by viewModels()

    private val REQUEST_CODE_ACCOUNTS_ACTIVITY: Int = 235

    private val REQUEST_CODE_SETTINGS_ACTIVITY: Int = 236

    private val REQUEST_CODE_LOGIN_ACTIVITY: Int = 265

    private lateinit var auth: AuthHelper

    override fun onInitialized() {
        viewModel.navArguments = arguments
        binding.profileVM = viewModel

        binding.txtNameSurname.text = preferenceHelper.getString("curr_user_id", "")

        auth = AuthHelper(ComponentActivity(), {}, {}, appDb, preferenceHelper)
    }

    override fun setUpClicks() {
        binding.linearRowAccounts.setOnClickListener {
            val destIntent = context?.let { it1 -> WalletsActivity.getIntent(it1, null) }
            startActivityForResult(destIntent, REQUEST_CODE_ACCOUNTS_ACTIVITY)
        }
        binding.linearRowsettings.setOnClickListener {
            val destIntent = context?.let { it1 -> SettingsActivity.getIntent(it1, null) }
            startActivityForResult(destIntent, REQUEST_CODE_SETTINGS_ACTIVITY)
        }
        binding.linearRowarrowright.setOnClickListener {
            try {
                auth.signOut()
                preferenceHelper.removeAllValues()
            } catch (e: Exception) {
                Log.d("Auth", "Error signing out")
            }
            val destIntent = context?.let { it1 -> LoginActivity.getIntent(it1, null) }
            startActivityForResult(destIntent, REQUEST_CODE_LOGIN_ACTIVITY)
        }
    }

    companion object {
        const val TAG: String = "PROFILE_FRAGMENT"

        fun getInstance(bundle: Bundle?): ProfileFragment {
            val fragment = ProfileFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}

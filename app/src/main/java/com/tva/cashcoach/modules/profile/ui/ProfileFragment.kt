package com.tva.cashcoach.modules.profile.ui

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseFragment
import com.tva.cashcoach.databinding.FragmentProfileBinding
import com.tva.cashcoach.modules.accounts.ui.AccountsActivity
import com.tva.cashcoach.modules.login.ui.LoginActivity
import com.tva.cashcoach.modules.profile.data.viewmodel.ProfileVM
import com.tva.cashcoach.modules.settings.ui.SettingsActivity

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val viewModel: ProfileVM by viewModels<ProfileVM>()

    private val REQUEST_CODE_ACCOUNTS_ACTIVITY: Int = 933


    private val REQUEST_CODE_SETTINGS_ACTIVITY: Int = 304


    private val REQUEST_CODE_LOGIN_ACTIVITY: Int = 363


    override fun onInitialized(): Unit {
        viewModel.navArguments = arguments
        binding.profileVM = viewModel
    }

    override fun setUpClicks(): Unit {
        binding.btnAccount.setOnClickListener {
            val destIntent = AccountsActivity.getIntent(requireActivity(), null)
            startActivityForResult(destIntent, REQUEST_CODE_ACCOUNTS_ACTIVITY)
            requireActivity().onBackPressed()
        }
        binding.btnRowsettings.setOnClickListener {
            val destIntent = SettingsActivity.getIntent(requireActivity(), null)
            startActivityForResult(destIntent, REQUEST_CODE_SETTINGS_ACTIVITY)
            requireActivity().onBackPressed()
        }
        binding.btnRowarrowright.setOnClickListener {
            val destIntent = LoginActivity.getIntent(requireActivity(), null)
            startActivityForResult(destIntent, REQUEST_CODE_LOGIN_ACTIVITY)
            requireActivity().onBackPressed()
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

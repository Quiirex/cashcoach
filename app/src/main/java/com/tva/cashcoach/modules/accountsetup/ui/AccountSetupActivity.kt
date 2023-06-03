package com.tva.cashcoach.modules.accountsetup.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.databinding.ActivityAccountSetupBinding
import com.tva.cashcoach.modules.accountsetup.data.viewmodel.AccountSetupVM
import com.tva.cashcoach.modules.addnewwallet.ui.AddNewWalletActivity

class AccountSetupActivity :
    BaseActivity<ActivityAccountSetupBinding>(R.layout.activity_account_setup) {
    private val viewModel: AccountSetupVM by viewModels()

    private val REQUEST_CODE_ADD_NEW_WALLET_ACTIVITY: Int = 511

    override fun onInitialized() {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.accountSetupVM = viewModel
    }

    override fun setUpClicks() {
        binding.btnLetsGo.setOnClickListener {
            val destIntent = AddNewWalletActivity.getIntent(this, null)
            startActivityForResult(destIntent, REQUEST_CODE_ADD_NEW_WALLET_ACTIVITY)
        }
    }

    companion object {
        const val TAG: String = "ACCOUNT_SETUP_ACTIVITY"


        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, AccountSetupActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }
}

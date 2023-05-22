package com.tva.cashcoach.modules.addnewwallet.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.appcomponents.persistence.repository.wallet.WalletRepository
import com.tva.cashcoach.appcomponents.utility.WalletHelper
import com.tva.cashcoach.databinding.ActivityAddNewWalletBinding
import com.tva.cashcoach.modules.addnewwallet.data.model.SpinnerDropdownWalletModel
import com.tva.cashcoach.modules.addnewwallet.data.viewmodel.AddNewWalletVM
import com.tva.cashcoach.modules.signupsuccess.ui.SignupSuccessActivity

class AddNewWalletActivity :
    BaseActivity<ActivityAddNewWalletBinding>(R.layout.activity_add_new_wallet) {
    private val viewModel: AddNewWalletVM by viewModels()

    private val REQUEST_CODE_HOME_SCREEN_CONTAINER_ACTIVITY: Int = 303

    private lateinit var walletHelper: WalletHelper

    override fun onInitialized() {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        viewModel.spinnerDropdownWalletList.value = mutableListOf(
            SpinnerDropdownWalletModel("Item1"),
            SpinnerDropdownWalletModel("Item2"),
            SpinnerDropdownWalletModel("Item3"),
            SpinnerDropdownWalletModel("Item4"),
            SpinnerDropdownWalletModel("Item5")
        )
//        val spinnerDropdownWalletAdapter =
//            SpinnerDropdownWalletAdapter(
//                this,
//                R.layout.spinner_item,
//                viewModel.spinnerDropdownWalletList.value ?: mutableListOf()
//            )
//        binding.spinnerDropdownWallet.adapter = spinnerDropdownWalletAdapter
        binding.addNewWalletVM = viewModel

        val walletDao = appDb.getWalletDao()
        val walletRepository = WalletRepository(walletDao)
        walletHelper = WalletHelper(walletRepository)
    }

    override fun setUpClicks() {
        binding.imageBack.setOnClickListener {
            finish()
        }
        binding.btnContinue.setOnClickListener {
            if (binding.etInputWalletNa.text.toString().isEmpty()) {
                binding.etInputWalletNa.error = "Please enter wallet name"
                return@setOnClickListener
            }
            if (binding.etPrice.text.toString().isEmpty()) {
                binding.etPrice.error = "Please enter balance"
                return@setOnClickListener
            }
            // TODO: Add wallet type check when spinners are fixed

            val addedSuccessfully = walletHelper.addWallet(
                binding.etInputWalletNa.text.toString(),
                "Type 1",
                binding.etPrice.text.toString().toDouble(),
            )

            if (!addedSuccessfully) {
                return@setOnClickListener
            }

            val destIntent = SignupSuccessActivity.getIntent(this, null)
            startActivityForResult(destIntent, REQUEST_CODE_HOME_SCREEN_CONTAINER_ACTIVITY)
        }
    }

    companion object {
        const val TAG: String = "ADD_NEW_WALLET_ACTIVITY"


        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, AddNewWalletActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }
}

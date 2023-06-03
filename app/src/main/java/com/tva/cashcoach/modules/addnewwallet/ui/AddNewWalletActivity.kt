package com.tva.cashcoach.modules.addnewwallet.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.appcomponents.persistence.repository.transaction.TransactionRepository
import com.tva.cashcoach.appcomponents.persistence.repository.user.UserRepository
import com.tva.cashcoach.appcomponents.persistence.repository.wallet.WalletRepository
import com.tva.cashcoach.appcomponents.utility.WalletHelper
import com.tva.cashcoach.databinding.ActivityAddNewWalletBinding
import com.tva.cashcoach.modules.addnewwallet.data.model.SpinnerWalletModel
import com.tva.cashcoach.modules.addnewwallet.data.viewmodel.AddNewWalletVM
import com.tva.cashcoach.modules.signupsuccess.ui.SignupSuccessActivity

class AddNewWalletActivity :
    BaseActivity<ActivityAddNewWalletBinding>(R.layout.activity_add_new_wallet) {
    private val viewModel: AddNewWalletVM by viewModels()

    private val REQUEST_CODE_HOME_SCREEN_CONTAINER_ACTIVITY: Int = 303

    private lateinit var walletHelper: WalletHelper

    override fun onInitialized() {
        viewModel.navArguments = intent.extras?.getBundle("bundle")

        viewModel.spinnerWallet.value = mutableListOf(
            SpinnerWalletModel(getString(R.string.bank_account)),
            SpinnerWalletModel(getString(R.string.savings_account)),
            SpinnerWalletModel(getString(R.string.other)),
        )
        val spinnerWalletAdapter =
            SpinnerWalletAdapter(
                this,
                R.layout.spinner_item,
                viewModel.spinnerWallet.value ?: mutableListOf(),
            )
        binding.spinnerWallet.adapter = spinnerWalletAdapter

        binding.addNewWalletVM = viewModel

        val walletDao = appDb.getWalletDao()
        val walletRepository = WalletRepository(walletDao)
        val userDao = appDb.getUserDao()
        val userRepository = UserRepository(userDao)
        val transactionDao = appDb.getTransactionDao()
        val transactionRepository = TransactionRepository(transactionDao)

        walletHelper = WalletHelper(walletRepository, userRepository, transactionRepository)
    }

    override fun setUpClicks() {
        binding.imageBack.setOnClickListener {
            finish()
        }
        binding.btnContinue.setOnClickListener {
            if (binding.etInputWalletNa.text.toString().isEmpty()) {
                binding.etInputWalletNa.error = getString(R.string.please_enter_wallet_name)
                return@setOnClickListener
            }
            if (binding.etPrice.text.toString().isEmpty()) {
                binding.etPrice.error = getString(R.string.please_enter_balance)
                return@setOnClickListener
            }
            // TODO: Add wallet type check when spinners are fixed

            walletHelper.addWallet(
                binding.etInputWalletNa.text.toString(),
                "Type 1",
                binding.etPrice.text.toString().toDouble()
            ) { insertedWalletId ->
                if (insertedWalletId >= 0) {
                    val bundle = intent.getBundleExtra("bundle")
                    var firstSetup = true

                    if (bundle != null) {
                        Log.d(TAG, "Bundle is not null")
                        if (bundle.containsKey("firstSetup")) {
                            Log.d(TAG, "Bundle contains 'firstSetup' key")
                            firstSetup = bundle.getBoolean("firstSetup")
                        } else {
                            Log.d(TAG, "Bundle does not contain 'firstSetup' key")
                        }
                    } else {
                        Log.d(TAG, "Bundle is null")
                    }

                    if (firstSetup) {
                        val setDefaultWallet = walletHelper.setDefaultWallet(insertedWalletId)
                        if (preferenceHelper.getString("curr_wallet_id", "") == "")
                            preferenceHelper.putString(
                                "curr_wallet_id",
                                insertedWalletId.toString()
                            )
                        if (setDefaultWallet) {
                            val destIntent = SignupSuccessActivity.getIntent(this, null)
                            startActivityForResult(
                                destIntent,
                                REQUEST_CODE_HOME_SCREEN_CONTAINER_ACTIVITY
                            )
                        } else {
                            setResult(Activity.RESULT_CANCELED)
                            finish()
                        }
                    } else {
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                } else {
                    setResult(Activity.RESULT_CANCELED)
                    finish()
                }
            }
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

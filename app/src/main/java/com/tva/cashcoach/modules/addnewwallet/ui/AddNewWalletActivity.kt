package com.tva.cashcoach.modules.addnewwallet.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Spinner
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
import com.tva.cashcoach.modules.loadingscreen.ui.LoadingScreenFragment
import com.tva.cashcoach.modules.signupsuccess.ui.SignupSuccessActivity


class AddNewWalletActivity :
    BaseActivity<ActivityAddNewWalletBinding>(R.layout.activity_add_new_wallet) {
    private val viewModel: AddNewWalletVM by viewModels()

    private val REQUEST_CODE_HOME_SCREEN_CONTAINER_ACTIVITY: Int = 303

    private lateinit var walletHelper: WalletHelper

    private val loadingDialogFragment by lazy { LoadingScreenFragment() }

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
            if (binding.etWalletName.text.toString().isEmpty()) {
                binding.etWalletName.error = getString(R.string.please_enter_wallet_name)
                return@setOnClickListener
            }
            if (binding.etBalance.text.toString().isEmpty()) {
                binding.etBalance.error = getString(R.string.please_enter_balance)
                return@setOnClickListener
            }

            val spinner = findViewById<Spinner>(R.id.spinnerWallet)
            val (selectedCategory) = spinner.selectedItem as SpinnerWalletModel

            if (!loadingDialogFragment.isAdded) {
                loadingDialogFragment.show(supportFragmentManager, "loader")
            }

            walletHelper.addWallet(
                binding.etWalletName.text.toString(),
                selectedCategory,
                binding.etBalance.text.toString().toDouble()
            ) { insertedWalletId ->
                if (insertedWalletId >= 0) {
                    val bundle = intent.getBundleExtra("bundle")
                    var firstSetup = true

                    if (bundle != null) {
                        if (bundle.containsKey("firstSetup")) {
                            firstSetup = bundle.getBoolean("firstSetup")
                        } else {
                            Log.e(TAG, "Bundle does not contain key firstSetup")
                        }
                    } else {
                        Log.e(TAG, "Bundle is null")
                    }

                    if (firstSetup) {
                        val setDefaultWallet = walletHelper.setDefaultWallet(insertedWalletId)
                        preferenceHelper.putString(
                            "curr_wallet_id",
                            insertedWalletId.toString()
                        )
                        preferenceHelper.putString("curr_user_currency", "EUR")
                        if (setDefaultWallet) {
                            val destIntent = SignupSuccessActivity.getIntent(this, null)
                            startActivityForResult(
                                destIntent,
                                REQUEST_CODE_HOME_SCREEN_CONTAINER_ACTIVITY
                            )
                            if (loadingDialogFragment.isAdded) {
                                loadingDialogFragment.dismissAllowingStateLoss()
                            }
                        } else {
                            if (loadingDialogFragment.isAdded) {
                                loadingDialogFragment.dismissAllowingStateLoss()
                            }
                            setResult(Activity.RESULT_CANCELED)
                            finish()
                        }
                    } else {
                        if (loadingDialogFragment.isAdded) {
                            loadingDialogFragment.dismissAllowingStateLoss()
                        }
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
                } else {
                    if (loadingDialogFragment.isAdded) {
                        loadingDialogFragment.dismissAllowingStateLoss()
                    }
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

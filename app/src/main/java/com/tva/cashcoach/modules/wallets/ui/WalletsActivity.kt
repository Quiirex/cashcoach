package com.tva.cashcoach.modules.wallets.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.tva.cashcoach.R
import com.tva.cashcoach.databinding.ActivityWalletsBinding
import com.tva.cashcoach.infrastructure.base.BaseActivity
import com.tva.cashcoach.infrastructure.model.transaction.TransactionDao
import com.tva.cashcoach.infrastructure.model.user.UserDao
import com.tva.cashcoach.infrastructure.model.wallet.WalletDao
import com.tva.cashcoach.infrastructure.persistence.repository.transaction.TransactionRepository
import com.tva.cashcoach.infrastructure.persistence.repository.user.UserRepository
import com.tva.cashcoach.infrastructure.persistence.repository.wallet.WalletRepository
import com.tva.cashcoach.infrastructure.utility.WalletHelper
import com.tva.cashcoach.modules.addnewwallet.ui.AddNewWalletActivity
import com.tva.cashcoach.modules.wallets.data.model.WalletsRowModel
import com.tva.cashcoach.modules.wallets.data.viewmodel.WalletsVM
import kotlinx.coroutines.launch

class WalletsActivity : BaseActivity<ActivityWalletsBinding>(R.layout.activity_wallets) {
    private val viewModel: WalletsVM by viewModels()

    private val REQUEST_CODE_ADD_NEW_WALLET_ACTIVITY: Int = 997

    private lateinit var walletHelper: WalletHelper

    private lateinit var walletDao: WalletDao

    private lateinit var walletRepository: WalletRepository

    private lateinit var userDao: UserDao

    private lateinit var userRepository: UserRepository

    private lateinit var transactionDao: TransactionDao

    private lateinit var transactionRepository: TransactionRepository

    private lateinit var walletsAdapter: WalletsAdapter

    override fun onInitialized() {
        viewModel.navArguments = intent.extras?.getBundle("bundle")

        walletDao = appDb.getWalletDao()
        walletRepository = WalletRepository(walletDao)
        userDao = appDb.getUserDao()
        userRepository = UserRepository(userDao)
        transactionDao = appDb.getTransactionDao()
        transactionRepository = TransactionRepository(transactionDao)

        walletHelper = WalletHelper(walletRepository, userRepository, transactionRepository)

        walletsAdapter = WalletsAdapter(
            walletRepository,
            preferenceHelper
        )

        binding.recyclerWallets.adapter = walletsAdapter

        walletHelper.getTotalBalanceOfWallets { totalBalance ->
            val currentWalletId = preferenceHelper.getString("curr_wallet_id", "").toInt()
            walletHelper.getWalletById(currentWalletId) { wallet ->
                val totalBalanceWithCurrency =
                    when (preferenceHelper.getString("curr_user_currency", "")) {
                        "USD" -> {
                            when (wallet.currency) {
                                "EUR" -> {
                                    (totalBalance * 1.07).format() + "$"
                                }

                                else -> {
                                    totalBalance.format() + "$"
                                }
                            }
                        }

                        "EUR" -> {
                            when (wallet.currency) {
                                "USD" -> {
                                    (totalBalance / 1.07).format() + "€"
                                }

                                else -> {
                                    totalBalance.format() + "€"
                                }
                            }
                        }

                        else -> ""
                    }
                binding.txtTotalBudget.text = totalBalanceWithCurrency
            }
        }

        walletsAdapter.setOnItemClickListener(
            object : WalletsAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int, item: WalletsRowModel) {
                    onClickRecyclerWallets(view, position, item)
                }
            }
        )

        binding.walletsVM = viewModel

        // Call fetchWallets() to fetch the wallets and update the adapter
        lifecycleScope.launch {
            walletsAdapter.fetchWallets()
        }
    }

    private fun Double.format(): String = String.format("%.2f", this)

    override fun setUpClicks() {
        binding.btnAddNewWalletOne.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("firstSetup", false)
            val destIntent = AddNewWalletActivity.getIntent(this, bundle)
            startActivityForResult(destIntent, REQUEST_CODE_ADD_NEW_WALLET_ACTIVITY)
        }
        binding.imageArrowleft.setOnClickListener {
            finish()
        }
    }

    fun onClickRecyclerWallets(
        view: View,
        position: Int,
        item: WalletsRowModel
    ) {
        when (view.id) {
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_ADD_NEW_WALLET_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                lifecycleScope.launch {
                    walletHelper.getTotalBalanceOfWallets { totalBalance ->
                        binding.txtTotalBudget.text =
                            totalBalance.format() + preferenceHelper.getString(
                                "curr_user_currency",
                                ""
                            )
                    }
                    walletsAdapter.fetchWallets()
                }
            }
        }
    }

    private fun Double.formatDecimal(): String {
        return if (this >= 0) {
            "%.2f".format(this)
        } else {
            "-%.2f".format(-this)
        }
    }

    companion object {
        const val TAG: String = "ACCOUNTS_ACTIVITY"

        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, WalletsActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }
}


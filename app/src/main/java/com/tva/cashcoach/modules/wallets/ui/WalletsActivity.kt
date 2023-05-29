package com.tva.cashcoach.modules.wallets.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.appcomponents.model.user.UserDao
import com.tva.cashcoach.appcomponents.model.wallet.WalletDao
import com.tva.cashcoach.appcomponents.persistence.repository.user.UserRepository
import com.tva.cashcoach.appcomponents.persistence.repository.wallet.WalletRepository
import com.tva.cashcoach.appcomponents.utility.WalletHelper
import com.tva.cashcoach.databinding.ActivityWalletsBinding
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

    private lateinit var walletsAdapter: WalletsAdapter

    override fun onInitialized() {
        viewModel.navArguments = intent.extras?.getBundle("bundle")

        walletDao = appDb.getWalletDao()
        walletRepository = WalletRepository(walletDao)
        userDao = appDb.getUserDao()
        userRepository = UserRepository(userDao)

        walletHelper = WalletHelper(walletRepository, userRepository)

        walletsAdapter = WalletsAdapter(
            walletRepository,
            preferenceHelper
        )

        binding.recyclerWallets.adapter = walletsAdapter

        walletHelper.getTotalBalanceOfWallets { totalBalance ->
            binding.txtTotalBudget.text = totalBalance.toString() + "€"
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
                        binding.txtTotalBudget.text = totalBalance.toString() + "€"
                    }
                    walletsAdapter.fetchWallets()
                }
            }
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


package com.tva.cashcoach.modules.wallets.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.databinding.ActivityWalletsBinding
import com.tva.cashcoach.modules.addnewwallet.ui.AddNewWalletActivity
import com.tva.cashcoach.modules.wallets.data.model.WalletsRowModel
import com.tva.cashcoach.modules.wallets.data.viewmodel.WalletsVM

class WalletsActivity : BaseActivity<ActivityWalletsBinding>(R.layout.activity_wallets) {
    private val viewModel: WalletsVM by viewModels()

    private val REQUEST_CODE_ADD_NEW_WALLET_ACTIVITY: Int = 997


    override fun onInitialized() {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        val walletsAdapter = WalletsAdapter(viewModel.accountsList.value ?: mutableListOf())
        binding.recyclerAccounts.adapter = walletsAdapter
        walletsAdapter.setOnItemClickListener(
            object : WalletsAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int, item: WalletsRowModel) {
                    onClickRecyclerAccounts(view, position, item)
                }
            }
        )
        viewModel.accountsList.observe(this) {
            walletsAdapter.updateData(it)
        }
        binding.walletsVM = viewModel
    }

    override fun setUpClicks() {
        binding.btnAddNewWalletOne.setOnClickListener {
            val destIntent = AddNewWalletActivity.getIntent(this, null)
            startActivityForResult(destIntent, REQUEST_CODE_ADD_NEW_WALLET_ACTIVITY)
        }
        binding.imageArrowleft.setOnClickListener {
            finish()
        }
    }

    fun onClickRecyclerAccounts(
        view: View,
        position: Int,
        item: WalletsRowModel
    ): Unit {
        when (view.id) {
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

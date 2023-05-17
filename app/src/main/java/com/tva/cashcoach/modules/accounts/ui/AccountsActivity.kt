package com.tva.cashcoach.modules.accounts.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.databinding.ActivityAccountsBinding
import com.tva.cashcoach.modules.accounts.data.model.AccountsRowModel
import com.tva.cashcoach.modules.accounts.data.viewmodel.AccountsVM
import com.tva.cashcoach.modules.addnewwallet.ui.AddNewWalletActivity

class AccountsActivity : BaseActivity<ActivityAccountsBinding>(R.layout.activity_accounts) {
    private val viewModel: AccountsVM by viewModels()

    private val REQUEST_CODE_ADD_NEW_WALLET_ACTIVITY: Int = 997


    override fun onInitialized() {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        val accountsAdapter = AccountsAdapter(viewModel.accountsList.value ?: mutableListOf())
        binding.recyclerAccounts.adapter = accountsAdapter
        accountsAdapter.setOnItemClickListener(
            object : AccountsAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int, item: AccountsRowModel) {
                    onClickRecyclerAccounts(view, position, item)
                }
            }
        )
        viewModel.accountsList.observe(this) {
            accountsAdapter.updateData(it)
        }
        binding.accountsVM = viewModel
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
        item: AccountsRowModel
    ): Unit {
        when (view.id) {
        }
    }

    companion object {
        const val TAG: String = "ACCOUNTS_ACTIVITY"


        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, AccountsActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }
}

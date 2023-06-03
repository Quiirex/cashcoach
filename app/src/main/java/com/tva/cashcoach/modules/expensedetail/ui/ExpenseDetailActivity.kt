package com.tva.cashcoach.modules.expensedetail.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.appcomponents.model.transaction.TransactionDao
import com.tva.cashcoach.appcomponents.persistence.repository.transaction.TransactionRepository
import com.tva.cashcoach.appcomponents.persistence.repository.user.UserRepository
import com.tva.cashcoach.appcomponents.persistence.repository.wallet.WalletRepository
import com.tva.cashcoach.appcomponents.utility.WalletHelper
import com.tva.cashcoach.databinding.ActivityExpenseDetailBinding
import com.tva.cashcoach.modules.expensedetail.data.viewmodel.ExpenseDetailVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExpenseDetailActivity :
    BaseActivity<ActivityExpenseDetailBinding>(R.layout.activity_expense_detail) {
    private val viewModel: ExpenseDetailVM by viewModels()

    private lateinit var transactionDao: TransactionDao

    private lateinit var transactionRepository: TransactionRepository

    private lateinit var walletHelper: WalletHelper

    override fun onInitialized() {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.expenseDetailVM = viewModel

        val walletDao = appDb.getWalletDao()
        val walletRepository = WalletRepository(walletDao)
        val userDao = appDb.getUserDao()
        val userRepository = UserRepository(userDao)
        val transactionDao = appDb.getTransactionDao()
        val transactionRepository = TransactionRepository(transactionDao)

        walletHelper = WalletHelper(
            walletRepository,
            userRepository,
            transactionRepository
        )

        val currency = when (viewModel.navArguments?.getString("currency")) {
            "EUR" -> "€"
            "USD" -> "$"
            else -> "€"
        }

        binding.txtValue.text = viewModel.navArguments?.getString("value") + currency
        binding.txtDate.text = viewModel.navArguments?.getString("date")
        binding.txtCategory.text = viewModel.navArguments?.getString("category")
        binding.txtWallet.text = walletHelper.getWalletNameById(
            viewModel.navArguments?.getString("wallet_id")!!.toInt()
        ) { walletName ->
            binding.txtWallet.text = walletName
        }.toString()
        binding.txtDescription.text = viewModel.navArguments?.getString("description")
    }

    override fun setUpClicks() {
        binding.imageBack.setOnClickListener {
            finish()
        }

        binding.btnDelete.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    try {
                        transactionRepository.deleteById(viewModel.navArguments?.getInt("id")!!)
                        finish()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    companion object {
        const val TAG: String = "EXPENSE_DETAIL_ACTIVITY"

        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, ExpenseDetailActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }
}

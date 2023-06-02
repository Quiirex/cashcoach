package com.tva.cashcoach.modules.transaction.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseFragment
import com.tva.cashcoach.appcomponents.model.transaction.TransactionDao
import com.tva.cashcoach.appcomponents.persistence.repository.transaction.TransactionRepository
import com.tva.cashcoach.databinding.FragmentTransactionBinding
import com.tva.cashcoach.modules.expensedetail.ui.ExpenseDetailActivity
import com.tva.cashcoach.modules.incomedetail.ui.IncomeDetailActivity
import com.tva.cashcoach.modules.transaction.data.model.TransactionRowModel
import com.tva.cashcoach.modules.transaction.data.viewmodel.TransactionVM
import kotlinx.coroutines.launch

class TransactionFragment :
    BaseFragment<FragmentTransactionBinding>(R.layout.fragment_transaction) {
    private val viewModel: TransactionVM by viewModels()

    private lateinit var transactionAdapter: TransactionAdapter

    private lateinit var transactionRepository: TransactionRepository

    private lateinit var transactionDao: TransactionDao

    override fun onInitialized() {
        viewModel.navArguments = arguments

        transactionDao = appDb.getTransactionDao()
        transactionRepository = TransactionRepository(transactionDao)

        transactionAdapter = TransactionAdapter(
            transactionRepository,
            preferenceHelper
        )

        binding.recyclerTransactions.adapter = transactionAdapter

        transactionAdapter.setOnItemClickListener(
            object : TransactionAdapter.OnItemClickListener {
                override fun onItemClick(
                    view: View,
                    position: Int,
                    transaction: TransactionRowModel
                ) {
                    onClickRecyclerTransactions(view, position, transaction)
                }
            }
        )

        binding.transactionVM = viewModel

        lifecycleScope.launch {
            transactionAdapter.fetchTransactions(preferenceHelper.getString("curr_wallet_id", ""))
        }
    }

    override fun setUpClicks() {
        binding.btnSort.setOnClickListener {
            Toast.makeText(context, "Tu pride popup za sorting", Toast.LENGTH_SHORT).show()
        }
    }

    fun onClickRecyclerTransactions(
        view: View,
        position: Int,
        transaction: TransactionRowModel
    ) {
        val intent = when (transaction.type) {
            "expense" -> Intent(context, ExpenseDetailActivity::class.java)
            "income" -> Intent(context, IncomeDetailActivity::class.java)
            else -> null
        }

        intent?.let {
            val bundle = Bundle().apply {
                putString("value", transaction.value.toString())
                putString("date", transaction.date)
                putString("category_id", transaction.category_id)
                putString("wallet_id", transaction.wallet_id.toString())
                putString("description", transaction.description)
                putString("currency", transaction.currency)
                putInt("id", transaction.id)
            }
            it.putExtra("bundle", bundle)
            startActivity(it)
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            transactionAdapter.fetchTransactions(preferenceHelper.getString("curr_wallet_id", ""))
        }
    }

    companion object {
        const val TAG: String = "TRANSACTION_FRAGMENT"

        fun getInstance(bundle: Bundle?): TransactionFragment {
            val fragment = TransactionFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}

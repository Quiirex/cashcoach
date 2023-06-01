package com.tva.cashcoach.modules.transaction.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseFragment
import com.tva.cashcoach.appcomponents.model.transaction.TransactionDao
import com.tva.cashcoach.appcomponents.persistence.repository.transaction.TransactionRepository
import com.tva.cashcoach.databinding.FragmentTransactionBinding
import com.tva.cashcoach.modules.expensedetail.ui.ExpenseDetailActivity
import com.tva.cashcoach.modules.incomedetail.ui.IncomeDetailActivity
import com.tva.cashcoach.modules.transaction.data.model.SpinnerDropdownMonthModel
import com.tva.cashcoach.modules.transaction.data.model.TransactionRowModel
import com.tva.cashcoach.modules.transaction.data.viewmodel.TransactionVM
import kotlinx.coroutines.launch

class TransactionFragment :
    BaseFragment<FragmentTransactionBinding>(R.layout.fragment_transaction) {
    private val viewModel: TransactionVM by viewModels()

    private val REQUEST_CODE_DETAIL_INCOME: Int = 12

    private val REQUEST_CODE_DETAIL_EXPENSE: Int = 13

    private lateinit var transactionAdapter: TransactionAdapter

    private lateinit var transactionRepository: TransactionRepository

    private lateinit var transactionDao: TransactionDao

    override fun onInitialized() {
        viewModel.navArguments = arguments
        viewModel.spinnerDropdownMonthList.value = mutableListOf(
            SpinnerDropdownMonthModel("Item1"),
            SpinnerDropdownMonthModel("Item2"),
            SpinnerDropdownMonthModel("Item3"),
            SpinnerDropdownMonthModel("Item4"),
            SpinnerDropdownMonthModel("Item5")
        )

        transactionDao = appDb.getTransactionDao()
        transactionRepository = TransactionRepository(transactionDao)

//        val spinnerDropdownMonthAdapter =
//            SpinnerDropdownMonthAdapter(
//                requireActivity(),
//                R.layout.spinner_item,
//                viewModel.spinnerDropdownMonthList.value ?: mutableListOf()
//            )
//        binding.spinnerDropdownMonth.adapter = spinnerDropdownMonthAdapter

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
//        viewModel.recyclerTransactions.observe(requireActivity()) {
//            listtrashAdapter.updateData(it)
//        }
        binding.transactionVM = viewModel

        lifecycleScope.launch {
            transactionAdapter.fetchTransactions(preferenceHelper.getString("curr_wallet_id", ""))
        }
    }

    override fun setUpClicks() {
    }

    fun onClickRecyclerTransactions(
        view: View,
        position: Int,
        transaction: TransactionRowModel
    ) {
        when (transaction.type) {
            "expense" -> {
                val intent = Intent(context, ExpenseDetailActivity::class.java)
                val bundle = Bundle()
                bundle.putString("value", transaction.value.toString())
                bundle.putString("date", transaction.date)
                bundle.putString("category_id", transaction.category_id)
                bundle.putString("wallet_id", transaction.wallet_id.toString())
                bundle.putString("description", transaction.description)
                bundle.putString("currency", transaction.currency)
                bundle.putInt("id", transaction.id)
                intent.putExtra("bundle", bundle)
                startActivity(intent)
            }

            "income" -> {
                val intent = Intent(context, IncomeDetailActivity::class.java)
                val bundle = Bundle()
                bundle.putString("value", transaction.value.toString())
                bundle.putString("date", transaction.date)
                bundle.putString("category_id", transaction.category_id)
                bundle.putString("wallet_id", transaction.wallet_id.toString())
                bundle.putString("description", transaction.description)
                bundle.putString("currency", transaction.currency)
                bundle.putInt("id", transaction.id)
                intent.putExtra("bundle", bundle)
                startActivity(intent)
            }
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

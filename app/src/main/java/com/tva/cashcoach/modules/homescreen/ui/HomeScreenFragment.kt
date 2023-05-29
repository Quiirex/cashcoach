package com.tva.cashcoach.modules.homescreen.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseFragment
import com.tva.cashcoach.appcomponents.model.transaction.TransactionDao
import com.tva.cashcoach.appcomponents.persistence.repository.transaction.TransactionRepository
import com.tva.cashcoach.databinding.FragmentHomeScreenBinding
import com.tva.cashcoach.modules.expensedetail.ui.ExpenseDetailActivity
import com.tva.cashcoach.modules.homescreen.data.model.ListframefiveRowModel
import com.tva.cashcoach.modules.homescreen.data.model.SpinnerDropdownMonthModel
import com.tva.cashcoach.modules.homescreen.data.viewmodel.HomeScreenVM
import com.tva.cashcoach.modules.incomedetail.ui.IncomeDetailActivity
import com.tva.cashcoach.modules.newexpense.ui.NewExpenseActivity
import com.tva.cashcoach.modules.newincome.ui.NewIncomeActivity
import com.tva.cashcoach.modules.transaction.data.model.TransactionRowModel
import com.tva.cashcoach.modules.transaction.ui.TransactionAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeScreenFragment : BaseFragment<FragmentHomeScreenBinding>(R.layout.fragment_home_screen) {
    private val viewModel: HomeScreenVM by viewModels()

    private lateinit var transactionAdapter: TransactionAdapter

    private lateinit var transactionRepository: TransactionRepository

    private lateinit var transactionDao: TransactionDao

    private val OPEN_INCOME_ACTIVITY: Int = 666

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

        transactionAdapter = TransactionAdapter(
            transactionRepository,
            preferenceHelper
        )
        binding.recyclerTransactions.adapter = transactionAdapter
        transactionAdapter.setOnItemClickListener(
            object : TransactionAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int, item: TransactionRowModel) {
                    onClickRecyclerTransactions(view, position, item)
                }
            }
        )

        binding.linearRowIncome.setOnClickListener {
            val intent = Intent(context, NewIncomeActivity::class.java)
            startActivity(intent)
        }

        binding.linearRowExpense.setOnClickListener {
            val intent = Intent(context, NewExpenseActivity::class.java)
            startActivity(intent)
        }

        binding.btnSeeAll.setOnClickListener {
            val intent = Intent(context, IncomeDetailActivity::class.java)
            startActivity(intent)
        }

        binding.txtSpendFrequency.setOnClickListener {
            val intent = Intent(context, ExpenseDetailActivity::class.java)
            startActivity(intent)
        }

        binding.homeScreenVM = viewModel

        lifecycleScope.launch {
            transactionAdapter.fetchTransactions()
        }
    }

    override fun setUpClicks() {
    }

    fun onClickRecyclerTransactions(
        view: View,
        position: Int,
        item: TransactionRowModel
    ) {
        when (view.id) {
        }
    }

    companion object {
        const val TAG: String = "HOME_SCREEN_FRAGMENT"

        fun getInstance(bundle: Bundle?): HomeScreenFragment {
            val fragment = HomeScreenFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}

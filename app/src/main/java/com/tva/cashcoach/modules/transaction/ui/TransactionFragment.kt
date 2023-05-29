package com.tva.cashcoach.modules.transaction.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseFragment
import com.tva.cashcoach.appcomponents.model.transaction.TransactionDao
import com.tva.cashcoach.appcomponents.model.user.UserDao
import com.tva.cashcoach.appcomponents.persistence.repository.transaction.TransactionRepository
import com.tva.cashcoach.appcomponents.persistence.repository.wallet.WalletRepository
import com.tva.cashcoach.databinding.FragmentTransactionBinding
import com.tva.cashcoach.modules.transaction.data.model.ListquestionRowModel
import com.tva.cashcoach.modules.transaction.data.model.ListtrashRowModel
import com.tva.cashcoach.modules.transaction.data.model.SpinnerDropdownMonthModel
import com.tva.cashcoach.modules.transaction.data.viewmodel.TransactionVM
import com.tva.cashcoach.modules.wallets.ui.WalletsAdapter
import kotlinx.coroutines.launch

class TransactionFragment :
    BaseFragment<FragmentTransactionBinding>(R.layout.fragment_transaction) {
    private val viewModel: TransactionVM by viewModels<TransactionVM>()

    private lateinit var transactionAdapter: TransactionAdapter

    private lateinit var transactionRepository: TransactionRepository

    private lateinit var transactionDao: TransactionDao

    override fun onInitialized(): Unit {

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

        val listtrashAdapter = ListtrashAdapter(viewModel.listtrashList.value ?: mutableListOf())
        binding.recyclerListtrash.adapter = listtrashAdapter
        listtrashAdapter.setOnItemClickListener(
            object : ListtrashAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int, item: ListtrashRowModel) {
                    onClickRecyclerListtrash(view, position, item)
                }
            }
        )
        viewModel.listtrashList.observe(requireActivity()) {
            listtrashAdapter.updateData(it)
        }
        binding.transactionVM = viewModel
        lifecycleScope.launch {
            transactionAdapter.fetchTransactions()
        }
    }

    override fun setUpClicks(): Unit {
    }

    fun onClickRecyclerListtrash(
        view: View,
        position: Int,
        item: ListtrashRowModel
    ): Unit {
        when (view.id) {
        }
    }

    fun onClickRecyclerListquestion(
        view: View,
        position: Int,
        item: ListquestionRowModel
    ): Unit {
        when (view.id) {
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

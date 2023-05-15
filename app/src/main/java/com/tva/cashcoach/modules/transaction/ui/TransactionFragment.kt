package com.tva.cashcoach.modules.transaction.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseFragment
import com.tva.cashcoach.databinding.FragmentTransactionBinding
import com.tva.cashcoach.modules.transaction.data.model.ListquestionRowModel
import com.tva.cashcoach.modules.transaction.data.model.ListtrashRowModel
import com.tva.cashcoach.modules.transaction.data.model.SpinnerDropdownMonthModel
import com.tva.cashcoach.modules.transaction.data.viewmodel.TransactionVM

class TransactionFragment :
    BaseFragment<FragmentTransactionBinding>(R.layout.fragment_transaction) {
    private val viewModel: TransactionVM by viewModels<TransactionVM>()

    override fun onInitialized(): Unit {
        viewModel.navArguments = arguments
        viewModel.spinnerDropdownMonthList.value = mutableListOf(
            SpinnerDropdownMonthModel("Item1"),
            SpinnerDropdownMonthModel("Item2"),
            SpinnerDropdownMonthModel("Item3"),
            SpinnerDropdownMonthModel("Item4"),
            SpinnerDropdownMonthModel("Item5")
        )
//        val spinnerDropdownMonthAdapter =
//            SpinnerDropdownMonthAdapter(
//                requireActivity(),
//                R.layout.spinner_item,
//                viewModel.spinnerDropdownMonthList.value ?: mutableListOf()
//            )
//        binding.spinnerDropdownMonth.adapter = spinnerDropdownMonthAdapter
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
        val listquestionAdapter =
            ListquestionAdapter(viewModel.listquestionList.value ?: mutableListOf())
        binding.recyclerListquestion.adapter = listquestionAdapter
        listquestionAdapter.setOnItemClickListener(
            object : ListquestionAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int, item: ListquestionRowModel) {
                    onClickRecyclerListquestion(view, position, item)
                }
            }
        )
        viewModel.listquestionList.observe(requireActivity()) {
            listquestionAdapter.updateData(it)
        }
        binding.transactionVM = viewModel
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

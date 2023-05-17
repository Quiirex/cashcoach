package com.tva.cashcoach.modules.homescreen.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseFragment
import com.tva.cashcoach.appcomponents.model.expense.Expense
import com.tva.cashcoach.databinding.FragmentHomeScreenBinding
import com.tva.cashcoach.modules.forgotpassword.ui.ForgotPasswordActivity
import com.tva.cashcoach.modules.homescreen.data.model.ListframefiveRowModel
import com.tva.cashcoach.modules.homescreen.data.model.SpinnerDropdownMonthModel
import com.tva.cashcoach.modules.homescreen.data.viewmodel.HomeScreenVM
import com.tva.cashcoach.modules.income.ui.IncomeActivity
import com.tva.cashcoach.modules.newexpense.ui.NewExpenseActivity

class HomeScreenFragment : BaseFragment<FragmentHomeScreenBinding>(R.layout.fragment_home_screen) {
    private val viewModel: HomeScreenVM by viewModels()

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
//        val spinnerDropdownMonthAdapter =
//            SpinnerDropdownMonthAdapter(
//                requireActivity(),
//                R.layout.spinner_item,
//                viewModel.spinnerDropdownMonthList.value ?: mutableListOf()
//            )
//        binding.spinnerDropdownMonth.adapter = spinnerDropdownMonthAdapter
        val listframefiveAdapter =
            ListframefiveAdapter(viewModel.listframefiveList.value ?: mutableListOf())
        binding.recyclerListframefive.adapter = listframefiveAdapter
        listframefiveAdapter.setOnItemClickListener(
            object : ListframefiveAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int, item: ListframefiveRowModel) {
                    onClickRecyclerListframefive(view, position, item)
                }
            }
        )

        binding.btnEye.setOnClickListener {
            val intent = Intent(context, IncomeActivity::class.java)
            startActivity(intent)
        }

        binding.btnTelevision.setOnClickListener {
            val intent = Intent(context, NewExpenseActivity::class.java)
            startActivity(intent)
        }

        viewModel.listframefiveList.observe(requireActivity()) {
            listframefiveAdapter.updateData(it)
        }
        binding.homeScreenVM = viewModel
    }

    override fun setUpClicks() {
    }

    fun onClickRecyclerListframefive(
        view: View,
        position: Int,
        item: ListframefiveRowModel
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

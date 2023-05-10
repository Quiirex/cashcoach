package com.tva.cashcoach.modules.expensedetail.ui

import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.databinding.ActivityExpenseDetailBinding
import com.tva.cashcoach.modules.expensedetail.data.viewmodel.ExpenseDetailVM

class ExpenseDetailActivity :
    BaseActivity<ActivityExpenseDetailBinding>(R.layout.activity_expense_detail) {
    private val viewModel: ExpenseDetailVM by viewModels<ExpenseDetailVM>()

    override fun onInitialized(): Unit {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.expenseDetailVM = viewModel
    }

    override fun setUpClicks(): Unit {
        binding.imageBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val TAG: String = "EXPENSE_DETAIL_ACTIVITY"

    }
}

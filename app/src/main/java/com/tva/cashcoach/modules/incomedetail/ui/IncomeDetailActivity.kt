package com.tva.cashcoach.modules.incomedetail.ui

import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.databinding.ActivityIncomeDetailBinding
import com.tva.cashcoach.modules.incomedetail.data.viewmodel.IncomeDetailVM

class IncomeDetailActivity :
    BaseActivity<ActivityIncomeDetailBinding>(R.layout.activity_income_detail) {
    private val viewModel: IncomeDetailVM by viewModels<IncomeDetailVM>()

    override fun onInitialized(): Unit {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.incomeDetailVM = viewModel
    }

    override fun setUpClicks(): Unit {
        binding.imageBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val TAG: String = "INCOME_DETAIL_ACTIVITY"

    }
}

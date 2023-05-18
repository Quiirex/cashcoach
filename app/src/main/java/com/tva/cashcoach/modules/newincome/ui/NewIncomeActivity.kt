package com.tva.cashcoach.modules.newincome.ui

import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.databinding.ActivityNewIncomeBinding
import com.tva.cashcoach.modules.homescreencontainer.ui.HomeScreenContainerActivity
import com.tva.cashcoach.modules.newincome.data.model.SpinnerDropdownCategoModel
import com.tva.cashcoach.modules.newincome.data.model.SpinnerDropdownWalletModel
import com.tva.cashcoach.modules.newincome.data.viewmodel.NewIncomeVM

class NewIncomeActivity : BaseActivity<ActivityNewIncomeBinding>(R.layout.activity_new_income) {
    private val viewModel: NewIncomeVM by viewModels()

    private val REQUEST_CODE_HOME_SCREEN_CONTAINER_ACTIVITY: Int = 815

    override fun onInitialized() {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        viewModel.spinnerDropdownCategoList.value = mutableListOf(
            SpinnerDropdownCategoModel("Item1"),
            SpinnerDropdownCategoModel("Item2"),
            SpinnerDropdownCategoModel("Item3"),
            SpinnerDropdownCategoModel("Item4"),
            SpinnerDropdownCategoModel("Item5")
        )
//        val spinnerDropdownCategoAdapter =
//            SpinnerDropdownCategoAdapter(
//                this,
//                R.layout.spinner_item,
//                viewModel.spinnerDropdownCategoList.value ?: mutableListOf()
//            )
//        binding.spinnerDropdownCatego.adapter = spinnerDropdownCategoAdapter
        viewModel.spinnerDropdownWalletList.value = mutableListOf(
            SpinnerDropdownWalletModel("Item1"),
            SpinnerDropdownWalletModel("Item2"),
            SpinnerDropdownWalletModel("Item3"),
            SpinnerDropdownWalletModel("Item4"),
            SpinnerDropdownWalletModel("Item5")
        )
//        val spinnerDropdownWalletAdapter =
//            SpinnerDropdownWalletAdapter(
//                this,
//                R.layout.spinner_item,
//                viewModel.spinnerDropdownWalletList.value ?: mutableListOf()
//            )
//        binding.spinnerDropdownWallet.adapter = spinnerDropdownWalletAdapter
        binding.incomeVM = viewModel
    }

    override fun setUpClicks() {
        binding.btnContinue.setOnClickListener {
            val destIntent = HomeScreenContainerActivity.getIntent(this, null)
            startActivityForResult(destIntent, REQUEST_CODE_HOME_SCREEN_CONTAINER_ACTIVITY)
        }
        binding.imageBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val TAG: String = "INCOME_ACTIVITY"

    }
}

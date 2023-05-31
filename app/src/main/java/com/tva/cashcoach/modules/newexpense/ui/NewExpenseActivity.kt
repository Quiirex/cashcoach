package com.tva.cashcoach.modules.newexpense.ui

import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.appcomponents.model.transaction.Transaction
import com.tva.cashcoach.appcomponents.model.transaction.TransactionDao
import com.tva.cashcoach.appcomponents.persistence.repository.transaction.TransactionRepository
import com.tva.cashcoach.databinding.ActivityNewExpenseBinding
import com.tva.cashcoach.modules.homescreencontainer.ui.HomeScreenContainerActivity
import com.tva.cashcoach.modules.newexpense.data.model.SpinnerDropdownCategoModel
import com.tva.cashcoach.modules.newexpense.data.model.SpinnerDropdownWalletModel
import com.tva.cashcoach.modules.newexpense.data.viewmodel.NewExpenseVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class NewExpenseActivity : BaseActivity<ActivityNewExpenseBinding>(R.layout.activity_new_expense) {
    private val viewModel: NewExpenseVM by viewModels()

    private lateinit var transactionDao: TransactionDao

    private lateinit var transactionRepository: TransactionRepository

    private val REQUEST_CODE_HOME_SCREEN_CONTAINER_ACTIVITY: Int = 355

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
        binding.newExpenseVM = viewModel

        transactionDao = appDb.getTransactionDao()
        transactionRepository = TransactionRepository(transactionDao)
    }

    override fun setUpClicks() {
        binding.btnContinue.setOnClickListener {
            val newTransaction = Transaction(
                id = null,
                value = binding.etValue.text.toString().toDouble(),
                description = binding.etDescription.text.toString(),
                date = Date(),
                category_id = 1,
                wallet_id = preferenceHelper.getString("curr_wallet_id", "").toInt(),
                type = "expense",
                currency = "EUR"
            )

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    transactionRepository.insert(newTransaction)
                }
            }

            Toast.makeText(
                applicationContext,
                getString(R.string.new_expense_added),
                Toast.LENGTH_SHORT
            ).show()

            val destIntent = HomeScreenContainerActivity.getIntent(this, null)
            startActivityForResult(destIntent, REQUEST_CODE_HOME_SCREEN_CONTAINER_ACTIVITY)
        }
        binding.imageBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val TAG: String = "NEW_EXPENSE_ACTIVITY"

    }
}

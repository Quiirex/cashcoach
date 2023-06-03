package com.tva.cashcoach.modules.newincome.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.appcomponents.model.category.CategoryDao
import com.tva.cashcoach.appcomponents.model.transaction.Transaction
import com.tva.cashcoach.appcomponents.model.transaction.TransactionDao
import com.tva.cashcoach.appcomponents.persistence.repository.category.CategoryRepository
import com.tva.cashcoach.appcomponents.persistence.repository.transaction.TransactionRepository
import com.tva.cashcoach.databinding.ActivityNewIncomeBinding
import com.tva.cashcoach.modules.homescreencontainer.ui.HomeScreenContainerActivity
import com.tva.cashcoach.modules.newincome.data.model.SpinnerCategoryModel
import com.tva.cashcoach.modules.newincome.data.viewmodel.NewIncomeVM
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date


class NewIncomeActivity : BaseActivity<ActivityNewIncomeBinding>(R.layout.activity_new_income) {
    private val viewModel: NewIncomeVM by viewModels()

    private lateinit var transactionDao: TransactionDao

    private lateinit var transactionRepository: TransactionRepository

    private lateinit var categoryDao: CategoryDao

    private lateinit var categoryRepository: CategoryRepository

    private val REQUEST_CODE_HOME_SCREEN_CONTAINER_ACTIVITY: Int = 815

    @OptIn(DelicateCoroutinesApi::class)
    override fun onInitialized() {
        viewModel.navArguments = intent.extras?.getBundle("bundle")

        categoryDao = appDb.getCategoryDao()
        categoryRepository = CategoryRepository(categoryDao)

        GlobalScope.launch(Dispatchers.IO) {
            val categories = categoryRepository.getAll()
            withContext(Dispatchers.Main) {
                viewModel.spinnerCategoryList.value =
                    SpinnerCategoryModel.fromCategoryList(categories) as MutableList<SpinnerCategoryModel>

                val spinnerCategoryAdapter =
                    SpinnerCategoryAdapter(
                        this@NewIncomeActivity,
                        R.layout.spinner_item,
                        viewModel.spinnerCategoryList.value ?: mutableListOf()
                    )
                binding.spinnerCategory.adapter = spinnerCategoryAdapter
            }
        }

        binding.incomeVM = viewModel

        transactionDao = appDb.getTransactionDao()
        transactionRepository = TransactionRepository(transactionDao)
    }

    override fun setUpClicks() {
        binding.btnContinue.setOnClickListener {

            val spinner = findViewById<Spinner>(R.id.spinnerCategory)
            val (selectedCategory) = spinner.selectedItem as SpinnerCategoryModel

            val newTransaction = Transaction(
                id = null,
                value = binding.etValue.text.toString().toDouble(),
                description = binding.etDescription.text.toString(),
                date = Date(),
                category = selectedCategory,
                wallet_id = preferenceHelper.getString("curr_wallet_id", "").toInt(),
                type = "income",
                currency = "EUR"
            )


            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    transactionRepository.insert(newTransaction)
                }
            }

            Toast.makeText(
                applicationContext,
                getString(R.string.new_income_added),
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
        const val TAG: String = "INCOME_ACTIVITY"

        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, NewIncomeActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }
}

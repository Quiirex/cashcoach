package com.tva.cashcoach.modules.newexpense.ui

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
import com.tva.cashcoach.databinding.ActivityNewExpenseBinding
import com.tva.cashcoach.modules.homescreencontainer.ui.HomeScreenContainerActivity
import com.tva.cashcoach.modules.newexpense.data.model.SpinnerCategoryModel
import com.tva.cashcoach.modules.newexpense.data.viewmodel.NewExpenseVM
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.bind
import java.util.Date

class NewExpenseActivity : BaseActivity<ActivityNewExpenseBinding>(R.layout.activity_new_expense) {
    private val viewModel: NewExpenseVM by viewModels()

    private lateinit var transactionDao: TransactionDao

    private lateinit var transactionRepository: TransactionRepository

    private lateinit var categoryDao: CategoryDao

    private lateinit var categoryRepository: CategoryRepository

    private val REQUEST_CODE_HOME_SCREEN_CONTAINER_ACTIVITY: Int = 355

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
                        this@NewExpenseActivity,
                        R.layout.spinner_item,
                        viewModel.spinnerCategoryList.value ?: mutableListOf()
                    )
                binding.spinnerCategory.adapter = spinnerCategoryAdapter
            }
        }

        binding.newExpenseVM = viewModel

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
                type = "expense",
                currency = preferenceHelper.getString("curr_user_currency", "")
            )

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    transactionRepository.insert(newTransaction)
                }
            }

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

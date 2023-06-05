package com.tva.cashcoach.modules.newincome.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.appcomponents.model.category.CategoryDao
import com.tva.cashcoach.appcomponents.model.transaction.Transaction
import com.tva.cashcoach.appcomponents.model.transaction.TransactionDao
import com.tva.cashcoach.appcomponents.model.user.UserDao
import com.tva.cashcoach.appcomponents.model.wallet.WalletDao
import com.tva.cashcoach.appcomponents.persistence.repository.category.CategoryRepository
import com.tva.cashcoach.appcomponents.persistence.repository.transaction.TransactionRepository
import com.tva.cashcoach.appcomponents.persistence.repository.user.UserRepository
import com.tva.cashcoach.appcomponents.persistence.repository.wallet.WalletRepository
import com.tva.cashcoach.appcomponents.utility.WalletHelper
import com.tva.cashcoach.databinding.ActivityNewIncomeBinding
import com.tva.cashcoach.modules.homescreencontainer.ui.HomeScreenContainerActivity
import com.tva.cashcoach.modules.newincome.data.model.SpinnerCategoryModel
import com.tva.cashcoach.modules.newincome.data.viewmodel.NewIncomeVM
import com.tva.cashcoach.modules.successscreen.ui.SuccessScreenFragment
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.concurrent.TimeUnit

class NewIncomeActivity : BaseActivity<ActivityNewIncomeBinding>(R.layout.activity_new_income) {
    private val viewModel: NewIncomeVM by viewModels()

    private lateinit var transactionDao: TransactionDao

    private lateinit var transactionRepository: TransactionRepository

    private lateinit var categoryDao: CategoryDao

    private lateinit var categoryRepository: CategoryRepository

    private lateinit var userDao: UserDao

    private lateinit var userRepository: UserRepository

    private lateinit var walletDao: WalletDao

    private lateinit var walletRepository: WalletRepository

    private lateinit var walletHelper: WalletHelper

    private val REQUEST_CODE_HOME_SCREEN_CONTAINER_ACTIVITY: Int = 815

    private val successDialogFragment by lazy { SuccessScreenFragment() }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onInitialized() {
        viewModel.navArguments = intent.extras?.getBundle("bundle")

        categoryDao = appDb.getCategoryDao()
        categoryRepository = CategoryRepository(categoryDao)

        userDao = appDb.getUserDao()
        userRepository = UserRepository(userDao)

        walletDao = appDb.getWalletDao()
        walletRepository = WalletRepository(walletDao)

        transactionDao = appDb.getTransactionDao()
        transactionRepository = TransactionRepository(transactionDao)

        walletHelper = WalletHelper(
            walletRepository = walletRepository,
            userRepository = userRepository,
            transactionRepository = transactionRepository
        )

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
                currency = preferenceHelper.getString("curr_user_currency", "")
            )

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val insertedTransactionId = transactionRepository.insert(newTransaction)
                    val tempTransaction = newTransaction.copy(id = insertedTransactionId.toInt())

                    walletHelper.addTransactionToFirestore(tempTransaction) { isSuccess ->
                        if (isSuccess) {
                            if (!successDialogFragment.isAdded) {
                                successDialogFragment.show(supportFragmentManager, "loader")
                            }
                            lifecycleScope.launch {
                                delay(TimeUnit.MILLISECONDS.toMillis(1300))
                                if (successDialogFragment.isAdded) {
                                    successDialogFragment.dismissAllowingStateLoss()
                                }
                                val destIntent =
                                    HomeScreenContainerActivity.getIntent(
                                        this@NewIncomeActivity,
                                        null
                                    )
                                startActivityForResult(
                                    destIntent,
                                    REQUEST_CODE_HOME_SCREEN_CONTAINER_ACTIVITY
                                )
                            }
                        }
                    }
                }
            }
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

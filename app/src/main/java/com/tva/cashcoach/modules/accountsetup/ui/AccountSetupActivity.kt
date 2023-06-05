package com.tva.cashcoach.modules.accountsetup.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.tva.cashcoach.R
import com.tva.cashcoach.infrastructure.base.BaseActivity
import com.tva.cashcoach.infrastructure.model.category.Category
import com.tva.cashcoach.infrastructure.model.category.CategoryDao
import com.tva.cashcoach.infrastructure.persistence.repository.category.CategoryRepository
import com.tva.cashcoach.databinding.ActivityAccountSetupBinding
import com.tva.cashcoach.modules.accountsetup.data.viewmodel.AccountSetupVM
import com.tva.cashcoach.modules.addnewwallet.ui.AddNewWalletActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountSetupActivity :
    BaseActivity<ActivityAccountSetupBinding>(R.layout.activity_account_setup) {
    private val viewModel: AccountSetupVM by viewModels()

    private val REQUEST_CODE_ADD_NEW_WALLET_ACTIVITY: Int = 511

    private lateinit var categoryDao: CategoryDao

    private lateinit var categoryRepository: CategoryRepository

    override fun onInitialized() {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.accountSetupVM = viewModel

        categoryDao = appDb.getCategoryDao()
        categoryRepository = CategoryRepository(categoryDao)

        lifecycleScope.launch(Dispatchers.IO) {
            val categories = categoryRepository.getAll()
            if (categories.isEmpty()) {
                val categoriesList = listOf(
                    Category(null, getString(R.string.food_drinks)),
                    Category(null, getString(R.string.clothes_shoes)),
                    Category(null, getString(R.string.transportation)),
                    Category(null, getString(R.string.health_beauty)),
                    Category(null, getString(R.string.home_utilities)),
                    Category(null, getString(R.string.entertainment)),
                    Category(null, getString(R.string.gifts_donations)),
                    Category(null, getString(R.string.education)),
                    Category(null, getString(R.string.other)),
                )
                categoryRepository.insertAll(categoriesList)
            }
        }
    }

    override fun setUpClicks() {
        binding.btnLetsGo.setOnClickListener {
            val destIntent = AddNewWalletActivity.getIntent(this, null)
            startActivityForResult(destIntent, REQUEST_CODE_ADD_NEW_WALLET_ACTIVITY)
        }
    }

    companion object {
        const val TAG: String = "ACCOUNT_SETUP_ACTIVITY"


        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, AccountSetupActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }
}

package com.tva.cashcoach.modules.homescreencontainer.ui

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.auth.AuthHelper
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.appcomponents.googleauth.GoogleAuthHelper
import com.tva.cashcoach.appcomponents.persistence.repository.user.UserRepository
import com.tva.cashcoach.databinding.ActivityHomeScreenContainerBinding
import com.tva.cashcoach.extensions.loadFragment
import com.tva.cashcoach.modules.financialreport.ui.FinancialReportActivity
import com.tva.cashcoach.modules.homescreen.ui.HomeScreenFragment
import com.tva.cashcoach.modules.homescreencontainer.data.viewmodel.HomeScreenContainerVM
import com.tva.cashcoach.modules.newexpense.ui.NewExpenseActivity
import com.tva.cashcoach.modules.newincome.ui.NewIncomeActivity
import com.tva.cashcoach.modules.profile.ui.ProfileFragment
import com.tva.cashcoach.modules.transaction.ui.TransactionFragment

class HomeScreenContainerActivity :
    BaseActivity<ActivityHomeScreenContainerBinding>(R.layout.activity_home_screen_container) {
    private val viewModel: HomeScreenContainerVM by viewModels()

    private val REQUEST_CODE_FINANCIAL_REPORT_LINE_CHART_ACTIVITY: Int = 276

    private lateinit var googleAuth: GoogleAuthHelper

    private lateinit var auth: AuthHelper

    override fun onInitialized() {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.homeScreenContainerVM = viewModel
        val userDao = appDb.getUserDao()
        val userRepository = UserRepository(userDao)
        googleAuth =
            GoogleAuthHelper(this, { _, _ -> }, {}, appDb, preferenceHelper, userRepository)
        auth = AuthHelper(this, {}, {}, appDb, preferenceHelper, userRepository)
        val destFragment = HomeScreenFragment.getInstance(null)
        this.loadFragment(
            R.id.fragmentContainer,
            destFragment,
            bundle = destFragment.arguments,
            tag = HomeScreenFragment.TAG,
            addToBackStack = false,
            add = false,
            enter = null,
            exit = null,
            onFragmentLoaded = ::updateButtonColors
        )
    }

    override fun setUpClicks() {
        binding.linearHome.setOnClickListener {
            if (HomeScreenFragment.currentFragmentTag == HomeScreenFragment.TAG) {
                return@setOnClickListener
            }
            val destFragment = HomeScreenFragment.getInstance(null)
            this.loadFragment(
                R.id.fragmentContainer,
                destFragment,
                bundle = destFragment.arguments,
                tag = HomeScreenFragment.TAG,
                addToBackStack = true,
                add = false,
                enter = null,
                exit = null,
                onFragmentLoaded = ::updateButtonColors
            )
        }
        binding.linearBudget.setOnClickListener {
            val destIntent = FinancialReportActivity.getIntent(this, null)
            startActivityForResult(destIntent, REQUEST_CODE_FINANCIAL_REPORT_LINE_CHART_ACTIVITY)
        }
        binding.linearProfile.setOnClickListener {
            if (ProfileFragment.currentFragmentTag == ProfileFragment.TAG) {
                return@setOnClickListener
            }
            val destFragment = ProfileFragment.getInstance(null)
            this.loadFragment(
                R.id.fragmentContainer,
                destFragment,
                bundle = destFragment.arguments,
                tag = ProfileFragment.TAG,
                addToBackStack = true,
                add = false,
                enter = null,
                exit = null,
                onFragmentLoaded = ::updateButtonColors
            )
        }
        binding.linearTransaction.setOnClickListener {
            if (TransactionFragment.currentFragmentTag == TransactionFragment.TAG) {
                return@setOnClickListener
            }
            val destFragment = TransactionFragment.getInstance(null)
            this.loadFragment(
                R.id.fragmentContainer,
                destFragment,
                bundle = destFragment.arguments,
                tag = TransactionFragment.TAG,
                addToBackStack = true,
                add = false,
                enter = null,
                exit = null,
                onFragmentLoaded = ::updateButtonColors
            )
        }
        binding.frameAdd.setOnClickListener {
            val dialog = Dialog(this)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.add_income_expense_dialog)

            val buttonIncome = dialog.findViewById<LinearLayout>(R.id.buttonIncome)
            val buttonExpense = dialog.findViewById<LinearLayout>(R.id.buttonExpense)

            buttonIncome.setOnClickListener {
                val intent = Intent(this, NewIncomeActivity::class.java)
                startActivity(intent)
                dialog.dismiss()
            }

            buttonExpense.setOnClickListener {
                val intent = Intent(this, NewExpenseActivity::class.java)
                startActivity(intent)
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    private fun updateButtonColors(activeFragmentTag: String) {
        // Update ImageView assets
        binding.imageHome.setImageResource(if (activeFragmentTag == HomeScreenFragment.TAG) R.drawable.baseline_home_24_black else R.drawable.baseline_home_24)
        binding.imageTransaction.setImageResource(if (activeFragmentTag == TransactionFragment.TAG) R.drawable.baseline_swap_horiz_24_black else R.drawable.baseline_swap_horiz_24)
        binding.imageProfile.setImageResource(if (activeFragmentTag == ProfileFragment.TAG) R.drawable.baseline_account_circle_24_black else R.drawable.baseline_account_circle_24)
        binding.imageReport.setImageResource(if (activeFragmentTag == FinancialReportActivity.TAG) R.drawable.baseline_bar_chart_24_black else R.drawable.baseline_bar_chart_24)

        // Update TextView colors
        binding.txtHome.setTextColor(if (activeFragmentTag == HomeScreenFragment.TAG) Color.BLACK else Color.GRAY)
        binding.txtTransaction.setTextColor(if (activeFragmentTag == TransactionFragment.TAG) Color.BLACK else Color.GRAY)
        binding.txtProfile.setTextColor(if (activeFragmentTag == ProfileFragment.TAG) Color.BLACK else Color.GRAY)
        binding.txtReport.setTextColor(if (activeFragmentTag == FinancialReportActivity.TAG) Color.BLACK else Color.GRAY)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
    }

    companion object {
        const val TAG: String = "HOME_SCREEN_CONTAINER_ACTIVITY"

        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, HomeScreenContainerActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }
}

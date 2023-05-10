package com.tva.cashcoach.modules.homescreencontainer.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.databinding.ActivityHomeScreenContainerBinding
import com.tva.cashcoach.extensions.loadFragment
import com.tva.cashcoach.modules.financialreportlinechart.ui.FinancialReportLineChartActivity
import com.tva.cashcoach.modules.homescreen.ui.HomeScreenFragment
import com.tva.cashcoach.modules.homescreencontainer.data.viewmodel.HomeScreenContainerVM
import com.tva.cashcoach.modules.profile.ui.ProfileFragment
import com.tva.cashcoach.modules.transaction.ui.TransactionFragment

class HomeScreenContainerActivity :
    BaseActivity<ActivityHomeScreenContainerBinding>(R.layout.activity_home_screen_container) {
    private val viewModel: HomeScreenContainerVM by viewModels<HomeScreenContainerVM>()

    private val REQUEST_CODE_FINANCIAL_REPORT_LINE_CHART_ACTIVITY: Int = 276


    override fun onInitialized(): Unit {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        binding.homeScreenContainerVM = viewModel
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
        )
    }

    override fun setUpClicks(): Unit {
        binding.linearHome.setOnClickListener {
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
            )
        }
        binding.linearBudget.setOnClickListener {
            val destIntent = FinancialReportLineChartActivity.getIntent(this, null)
            startActivityForResult(destIntent, REQUEST_CODE_FINANCIAL_REPORT_LINE_CHART_ACTIVITY)
        }
        binding.linearProfile.setOnClickListener {
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
            )
        }
        binding.linearTransaction.setOnClickListener {
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
            )
        }
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

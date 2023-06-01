package com.tva.cashcoach.modules.financialreport.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.appcomponents.model.transaction.Transaction
import com.tva.cashcoach.appcomponents.model.transaction.TransactionDao
import com.tva.cashcoach.appcomponents.persistence.repository.transaction.TransactionRepository
import com.tva.cashcoach.databinding.ActivityFinancialReportBinding
import com.tva.cashcoach.modules.financialreport.data.model.Listtrash1RowModel
import com.tva.cashcoach.modules.financialreport.data.model.SpinnerDropdownMonthModel
import com.tva.cashcoach.modules.financialreport.data.model.SpinnerDropdownTransaModel
import com.tva.cashcoach.modules.financialreport.data.viewmodel.FinancialReportVM
import com.tva.cashcoach.modules.transaction.data.model.TransactionRowModel
import com.tva.cashcoach.modules.transaction.ui.TransactionAdapter
import kotlinx.coroutines.launch

class FinancialReportActivity :
    BaseActivity<ActivityFinancialReportBinding>(R.layout.activity_financial_report) {
    private val viewModel: FinancialReportVM by viewModels()

    private val REQUEST_CODE_FINANCIAL_REPORT_PIE_CHART_ACTIVITY: Int = 545

    private lateinit var transactionAdapter: TransactionAdapter

    private lateinit var transactionRepository: TransactionRepository

    private lateinit var transactionDao: TransactionDao

    private lateinit var transactions: List<Transaction>

    override fun onInitialized() {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        viewModel.spinnerDropdownMonthList.value = mutableListOf(
            SpinnerDropdownMonthModel("Item1"),
            SpinnerDropdownMonthModel("Item2"),
            SpinnerDropdownMonthModel("Item3"),
            SpinnerDropdownMonthModel("Item4"),
            SpinnerDropdownMonthModel("Item5")
        )

        transactionDao = appDb.getTransactionDao()
        transactionRepository = TransactionRepository(transactionDao)

//        val spinnerDropdownMonthAdapter =
//            SpinnerDropdownMonthAdapter(
//                this,
//                R.layout.spinner_item,
//                viewModel.spinnerDropdownMonthList.value ?: mutableListOf()
//            )
//        binding.spinnerDropdownMonth.adapter = spinnerDropdownMonthAdapter

        transactionAdapter = TransactionAdapter(
            transactionRepository,
            preferenceHelper
        )

        viewModel.spinnerDropdownTransaList.value = mutableListOf(
            SpinnerDropdownTransaModel("Item1"),
            SpinnerDropdownTransaModel("Item2"),
            SpinnerDropdownTransaModel("Item3"),
            SpinnerDropdownTransaModel("Item4"),
            SpinnerDropdownTransaModel("Item5")
        )
//        val spinnerDropdownTransaAdapter =
//            SpinnerDropdownTransaAdapter(
//                this,
//                R.layout.spinner_item,
//                viewModel.spinnerDropdownTransaList.value ?: mutableListOf()
//            )
//        binding.spinnerDropdownTransa.adapter = spinnerDropdownTransaAdapter

        binding.recyclerTransactions.adapter = transactionAdapter

        transactionAdapter.setOnItemClickListener(
            object : TransactionAdapter.OnItemClickListener {
                override fun onItemClick(
                    view: View,
                    position: Int,
                    transaction: TransactionRowModel
                ) {
                    onClickRecyclerTransactions(view, position, transaction)
                }
            }
        )
        binding.financialReportLineChartVM = viewModel

        lifecycleScope.launch {
            transactions = transactionAdapter.fetchAllTransactions(preferenceHelper.getString("curr_wallet_id", ""))
            transactionAdapter.fetchTransactions(preferenceHelper.getString("curr_wallet_id", ""))

            if (preferenceHelper.getString("curr_user_currency", "") == "EUR") {
                binding.valBudget.text = String.format(
                    "%.2f€",
                    transactionAdapter.fetchIncomesSum(preferenceHelper.getString("curr_wallet_id", "")) - transactionAdapter.fetchExpensesSum(
                        preferenceHelper.getString("curr_wallet_id", "")
                    )
                )
            } else {
                binding.valBudget.text = String.format(
                    "%.2f$",
                    transactionAdapter.fetchIncomesSum(preferenceHelper.getString("curr_wallet_id", "")) - transactionAdapter.fetchExpensesSum(
                        preferenceHelper.getString("curr_wallet_id", "")
                    )
                )
            }
        }

        val chart = findViewById<AAChartView>(R.id.chartView)
        val aaChartModel : AAChartModel = AAChartModel()
            .chartType(AAChartType.Area)
            .dataLabelsEnabled(true)
            .colorsTheme(arrayOf("#3D85C6"))
            .series(
                arrayOf(
                    AASeriesElement()
                    .name("Budget")
                        //namesto fiksnih podatkov uporabi podatke iz transactions (idi cez list, in shrani .value v array)
                    .data(arrayOf(1000.0, 800.0, 850.0, 700.0, 500.0, 100.0, 300.0))
                )
            )
        chart.aa_drawChartWithChartModel(aaChartModel)

    }

    override fun setUpClicks() {
        binding.imageBack.setOnClickListener {
            finish()
        }
    }

    fun onClickRecyclerTransactions(
        view: View,
        position: Int,
        item: TransactionRowModel
    ): Unit {
        when (view.id) {
        }
    }

    companion object {
        const val TAG: String = "FINANCIAL_REPORT_LINE_CHART_ACTIVITY"


        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, FinancialReportActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }
}
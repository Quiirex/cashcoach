package com.tva.cashcoach.modules.financialreport.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

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
            graph(setDefaultDates().first, setDefaultDates().second)
        }

    }

    fun setDefaultDates(): Pair<Date, Date> {
        val startDateEditText: EditText = findViewById(R.id.startDate)
        val endDateEditText: EditText = findViewById(R.id.endDate)

        // Get today's date
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)

        // Set the end date to today's date
        val endDateString = dateFormat.format(currentDate)
        endDateEditText.setText(endDateString)

        // Calculate the start date as one week before today
        val startDate = Calendar.getInstance()
        startDate.add(Calendar.DAY_OF_MONTH, -7)
        val startDateString = dateFormat.format(startDate.time)
        startDateEditText.setText(startDateString)

        return Pair(startDate.time, currentDate)
    }

    fun graph(startDate: Date, endDate: Date) {
        val budgetList = mutableListOf<Double>()
        var budget = 0.0

        for (transaction in transactions) {
            if (transaction.date in startDate..endDate) {
                if (transaction.type == "income") {
                    budget += transaction.value
                } else if (transaction.type == "expense") {
                    budget -= transaction.value
                }
                budgetList.add(budget)
            }
        }

        val chart = findViewById<AAChartView>(R.id.chartView)
        val aaChartModel: AAChartModel = AAChartModel()
            .chartType(AAChartType.Areaspline)
            .dataLabelsEnabled(true)
            .colorsTheme(arrayOf("#3D85C6"))
            .series(
                arrayOf(
                    AASeriesElement()
                        .name("Budget")
                        .data(budgetList.toTypedArray())
                )
            )
        chart.aa_drawChartWithChartModel(aaChartModel)
    }

    override fun setUpClicks() {
        binding.imageBack.setOnClickListener {
            finish()
        }

        binding.updateGraph.setOnClickListener {
            val startDateEditText: EditText = findViewById(R.id.startDate)
            val endDateEditText: EditText = findViewById(R.id.endDate)

            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)

            val updateGraphButton: Button = findViewById(R.id.updateGraph)
            updateGraphButton.setOnClickListener {
                val startDateString = startDateEditText.text.toString()
                val endDateString = endDateEditText.text.toString()

                val startDate = dateFormat.parse(startDateString)
                var endDate = dateFormat.parse(endDateString)

                // Move end date one day forward
                val calendar = Calendar.getInstance()
                calendar.time = endDate
                calendar.add(Calendar.DAY_OF_MONTH, 1)
                endDate = calendar.time

                graph(startDate, endDate)
            }
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
package com.tva.cashcoach.modules.financialreport.ui

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.DatePicker
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
import com.tva.cashcoach.modules.financialreport.data.viewmodel.FinancialReportVM
import com.tva.cashcoach.modules.transaction.ui.TransactionAdapter
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class FinancialReportActivity :
    BaseActivity<ActivityFinancialReportBinding>(R.layout.activity_financial_report) {
    private val viewModel: FinancialReportVM by viewModels()

    private lateinit var transactionAdapter: TransactionAdapter

    private lateinit var transactionRepository: TransactionRepository

    private lateinit var transactionDao: TransactionDao

    private lateinit var transactions: List<Transaction>

    override fun onInitialized() {
        viewModel.navArguments = intent.extras?.getBundle("bundle")

        transactionDao = appDb.getTransactionDao()
        transactionRepository = TransactionRepository(transactionDao)

        transactionAdapter = TransactionAdapter(
            transactionRepository, preferenceHelper
        )

        lifecycleScope.launch {
            transactions = transactionAdapter.fetchAllTransactions(
                preferenceHelper.getString(
                    "curr_wallet_id", ""
                )
            )
            budgetGraph(setDefaultDates().first, setDefaultDates().second)
            incomeGraph(setDefaultDates().first, setDefaultDates().second)
            expenseGraph(setDefaultDates().first, setDefaultDates().second)
        }

        setUpDatePickers()
    }

    private fun setUpDatePickers() {
        val startDateEditText: EditText = findViewById(R.id.startDate)
        val endDateEditText: EditText = findViewById(R.id.endDate)

        startDateEditText.apply {
            isFocusable = false
            isFocusableInTouchMode = false
            setOnClickListener {
                showDatePicker(startDateEditText)
            }
        }

        endDateEditText.apply {
            isFocusable = false
            isFocusableInTouchMode = false
            setOnClickListener {
                showDatePicker(endDateEditText)
            }
        }
    }


    private fun showDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            R.style.DatePickerTheme,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)
                val dateString = dateFormat.format(selectedDate.time)
                editText.setText(dateString)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
        setUpDateInputListeners()
    }

    private fun setUpDateInputListeners() {
        binding.startDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                updateGraphs()
            }
        })

        binding.endDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                updateGraphs()
            }
        })
    }

    fun updateGraphs() {
        val startDateEditText: EditText = findViewById(R.id.startDate)
        val endDateEditText: EditText = findViewById(R.id.endDate)
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)
        val startDateString = startDateEditText.text.toString()
        val endDateString = endDateEditText.text.toString()
        val startDate = dateFormat.parse(startDateString)
        var endDate = dateFormat.parse(endDateString)
        val calendar = Calendar.getInstance()
        if (endDate != null) {
            calendar.time = endDate
        }
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        endDate = calendar.time
        if (startDate != null) {
            if (endDate != null) {
                budgetGraph(startDate, endDate)
            }
        }
        if (startDate != null) {
            if (endDate != null) {
                incomeGraph(startDate, endDate)
            }
        }
        if (startDate != null) {
            if (endDate != null) {
                expenseGraph(startDate, endDate)
            }
        }
    }

    private fun setDefaultDates(): Pair<Date, Date> {
        val startDateEditText: EditText = findViewById(R.id.startDate)
        val endDateEditText: EditText = findViewById(R.id.endDate)

        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)

        val endDateString = dateFormat.format(currentDate)
        endDateEditText.setText(endDateString)

        val startDate = Calendar.getInstance()
        startDate.add(Calendar.DAY_OF_MONTH, -7)
        val startDateString = dateFormat.format(startDate.time)
        startDateEditText.setText(startDateString)

        return Pair(startDate.time, currentDate)
    }

    private fun budgetGraph(startDate: Date, endDate: Date) {
        val budgetList = mutableListOf<Double>()
        var budget: Double

        val relevantTransactions = transactions.filter { it.date <= startDate }
        budget =
            relevantTransactions.sumOf { if (it.type == "income") it.value else -it.value }
        budgetList.add(budget)

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
        budgetList.removeAt(0)
        val chart = findViewById<AAChartView>(R.id.BudgetChartView)
        val aaChartModel: AAChartModel =
            AAChartModel().chartType(AAChartType.Areaspline)
                .title(getString(R.string.lbl_graph_budget))
                .dataLabelsEnabled(true)
                .yAxisVisible(false)
                .xAxisVisible(false)
                .colorsTheme(arrayOf("#3D85C6")).series(
                    arrayOf(
                        AASeriesElement().name(getString(R.string.budget))
                            .data(budgetList.toTypedArray())
                    )
                )
        chart.aa_drawChartWithChartModel(aaChartModel)
    }

    private fun incomeGraph(startDate: Date, endDate: Date) {
        val incomes: MutableMap<String, Double> = HashMap()

        for (transaction in transactions) {
            if (transaction.date in startDate..endDate && transaction.type == "income" && transaction.category != getString(
                    R.string.initial_budget
                )
            ) {
                val category = transaction.category
                incomes[category] = incomes.getOrDefault(category, 0.0) + transaction.value
            }
        }

        val chart = findViewById<AAChartView>(R.id.IncomeChartView)
        val aaChartModel = AAChartModel()
            .title(getString(R.string.lbl_graph_income))
            .chartType(AAChartType.Pie)
            .categories(incomes.keys.toTypedArray())
            .series(
                arrayOf(
                    AASeriesElement()
                        .data(
                            incomes.entries.map { arrayOf(it.key, it.value) }.toTypedArray()
                        )
                )
            )

        chart.aa_drawChartWithChartModel(aaChartModel)
    }

    private fun expenseGraph(startDate: Date, endDate: Date) {
        val expenses: MutableMap<String, Double> = HashMap()

        for (transaction in transactions) {
            if (transaction.date in startDate..endDate && transaction.type == "expense") {
                val category = transaction.category
                expenses[category] = expenses.getOrDefault(category, 0.0) + transaction.value
            }
        }

        val chart = findViewById<AAChartView>(R.id.ExpenseChartView)
        val aaChartModel = AAChartModel()
            .title(getString(R.string.lbl_graph_expense))
            .chartType(AAChartType.Pie)
            .categories(expenses.keys.toTypedArray())
            .series(
                arrayOf(
                    AASeriesElement()
                        .data(
                            expenses.entries.map { arrayOf(it.key, it.value) }.toTypedArray()
                        )
                )
            )

        chart.aa_drawChartWithChartModel(aaChartModel)
    }


    override fun setUpClicks() {
        binding.imageBack.setOnClickListener {
            finish()
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
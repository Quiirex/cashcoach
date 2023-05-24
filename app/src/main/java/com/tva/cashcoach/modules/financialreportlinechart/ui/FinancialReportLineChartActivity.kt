package com.tva.cashcoach.modules.financialreportlinechart.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.databinding.ActivityFinancialReportLineChartBinding
import com.tva.cashcoach.modules.financialreportlinechart.data.model.Listtrash1RowModel
import com.tva.cashcoach.modules.financialreportlinechart.data.model.SpinnerDropdownMonthModel
import com.tva.cashcoach.modules.financialreportlinechart.data.model.SpinnerDropdownTransaModel
import com.tva.cashcoach.modules.financialreportlinechart.data.viewmodel.FinancialReportLineChartVM

class FinancialReportLineChartActivity :
    BaseActivity<ActivityFinancialReportLineChartBinding>(R.layout.activity_financial_report_line_chart) {
    private val viewModel: FinancialReportLineChartVM by viewModels<FinancialReportLineChartVM>()

    private val REQUEST_CODE_FINANCIAL_REPORT_PIE_CHART_ACTIVITY: Int = 545


    override fun onInitialized(): Unit {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        viewModel.spinnerDropdownMonthList.value = mutableListOf(
            SpinnerDropdownMonthModel("Item1"),
            SpinnerDropdownMonthModel("Item2"),
            SpinnerDropdownMonthModel("Item3"),
            SpinnerDropdownMonthModel("Item4"),
            SpinnerDropdownMonthModel("Item5")
        )
//        val spinnerDropdownMonthAdapter =
//            SpinnerDropdownMonthAdapter(
//                this,
//                R.layout.spinner_item,
//                viewModel.spinnerDropdownMonthList.value ?: mutableListOf()
//            )
//        binding.spinnerDropdownMonth.adapter = spinnerDropdownMonthAdapter
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
        val listtrashAdapter = ListtrashAdapter(viewModel.listtrashList.value ?: mutableListOf())
        binding.recyclerListtrash.adapter = listtrashAdapter
        listtrashAdapter.setOnItemClickListener(
            object : ListtrashAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int, item: Listtrash1RowModel) {
                    onClickRecyclerListtrash(view, position, item)
                }
            }
        )
        viewModel.listtrashList.observe(this) {
            listtrashAdapter.updateData(it)
        }
        binding.financialReportLineChartVM = viewModel
    }

    override fun setUpClicks() {
        binding.imageBack.setOnClickListener {
            finish()
        }
    }

    fun onClickRecyclerListtrash(
        view: View,
        position: Int,
        item: Listtrash1RowModel
    ): Unit {
        when (view.id) {
        }
    }

    companion object {
        const val TAG: String = "FINANCIAL_REPORT_LINE_CHART_ACTIVITY"


        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, FinancialReportLineChartActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }
}
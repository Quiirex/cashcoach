package com.tva.cashcoach.modules.financialreportpiechart.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.base.BaseActivity
import com.tva.cashcoach.databinding.ActivityFinancialReportPieChartBinding
import com.tva.cashcoach.modules.financialreportlinechart.ui.FinancialReportLineChartActivity
import com.tva.cashcoach.modules.financialreportpiechart.data.model.SpinnerDropdownCategoModel
import com.tva.cashcoach.modules.financialreportpiechart.data.viewmodel.FinancialReportPieChartVM

class FinancialReportPieChartActivity :
    BaseActivity<ActivityFinancialReportPieChartBinding>(R.layout.activity_financial_report_pie_chart) {
    private val viewModel: FinancialReportPieChartVM by viewModels<FinancialReportPieChartVM>()

    private val REQUEST_CODE_FINANCIAL_REPORT_LINE_CHART_ACTIVITY: Int = 609

    override fun onInitialized(): Unit {
        viewModel.navArguments = intent.extras?.getBundle("bundle")
        viewModel.spinnerDropdownCategoList.value = mutableListOf(
            SpinnerDropdownCategoModel("Item1"),
            SpinnerDropdownCategoModel("Item2"),
            SpinnerDropdownCategoModel("Item3"),
            SpinnerDropdownCategoModel("Item4"),
            SpinnerDropdownCategoModel("Item5")
        )
//        val spinnerDropdownCategoAdapter =
//            SpinnerDropdownCategoAdapter(
//                this,
//                R.layout.spinner_item,
//                viewModel.spinnerDropdownCategoList.value ?: mutableListOf()
//            )
//        binding.spinnerDropdownCatego.adapter = spinnerDropdownCategoAdapter
        binding.financialReportPieChartVM = viewModel
    }

    override fun setUpClicks(): Unit {
        binding.imageBack.setOnClickListener {
            finish()
        }
        binding.btnLineCHART.setOnClickListener {
            val destIntent = FinancialReportLineChartActivity.getIntent(this, null)
            startActivityForResult(destIntent, REQUEST_CODE_FINANCIAL_REPORT_LINE_CHART_ACTIVITY)
        }
    }

    companion object {
        const val TAG: String = "FINANCIAL_REPORT_PIE_CHART_ACTIVITY"


        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, FinancialReportPieChartActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }
}

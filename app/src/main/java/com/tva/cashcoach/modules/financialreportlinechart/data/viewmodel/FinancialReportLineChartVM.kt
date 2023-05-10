package com.tva.cashcoach.modules.financialreportlinechart.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tva.cashcoach.modules.financialreportlinechart.data.model.FinancialReportLineChartModel
import com.tva.cashcoach.modules.financialreportlinechart.data.model.Listtrash1RowModel
import com.tva.cashcoach.modules.financialreportlinechart.data.model.SpinnerDropdownMonthModel
import com.tva.cashcoach.modules.financialreportlinechart.data.model.SpinnerDropdownTransaModel
import org.koin.core.KoinComponent

class FinancialReportLineChartVM : ViewModel(), KoinComponent {
    val financialReportLineChartModel: MutableLiveData<FinancialReportLineChartModel> =
        MutableLiveData(FinancialReportLineChartModel())

    var navArguments: Bundle? = null

    val spinnerDropdownMonthList: MutableLiveData<MutableList<SpinnerDropdownMonthModel>> =
        MutableLiveData()

    val spinnerDropdownTransaList: MutableLiveData<MutableList<SpinnerDropdownTransaModel>> =
        MutableLiveData()

    val listtrashList: MutableLiveData<MutableList<Listtrash1RowModel>> =
        MutableLiveData(mutableListOf())
}

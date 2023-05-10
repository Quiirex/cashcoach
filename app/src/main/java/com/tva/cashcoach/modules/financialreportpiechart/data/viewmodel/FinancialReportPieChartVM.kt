package com.tva.cashcoach.modules.financialreportpiechart.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tva.cashcoach.modules.financialreportpiechart.data.model.FinancialReportPieChartModel
import com.tva.cashcoach.modules.financialreportpiechart.data.model.SpinnerDropdownCategoModel
import org.koin.core.KoinComponent

class FinancialReportPieChartVM : ViewModel(), KoinComponent {
    val financialReportPieChartModel: MutableLiveData<FinancialReportPieChartModel> =
        MutableLiveData(FinancialReportPieChartModel())

    var navArguments: Bundle? = null

    val spinnerDropdownCategoList: MutableLiveData<MutableList<SpinnerDropdownCategoModel>> =
        MutableLiveData()
}

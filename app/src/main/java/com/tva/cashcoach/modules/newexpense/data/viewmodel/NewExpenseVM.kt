package com.tva.cashcoach.modules.newexpense.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tva.cashcoach.modules.newexpense.data.model.NewExpenseModel
import com.tva.cashcoach.modules.newexpense.data.model.SpinnerCategoryModel
import org.koin.core.KoinComponent

class NewExpenseVM : ViewModel(), KoinComponent {
    val newExpenseModel: MutableLiveData<NewExpenseModel> = MutableLiveData(NewExpenseModel())

    var navArguments: Bundle? = null

    val spinnerCategoryList: MutableLiveData<MutableList<SpinnerCategoryModel>> =
        MutableLiveData()
}

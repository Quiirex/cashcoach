package com.tva.cashcoach.modules.transaction.`data`.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import org.koin.core.KoinComponent

class TransactionVM : ViewModel(), KoinComponent {
    var navArguments: Bundle? = null
}

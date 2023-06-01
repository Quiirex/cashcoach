package com.tva.cashcoach.modules.transaction.`data`.model

data class TransactionRowModel(
    val category_id: String = "",
    val description: String = "",
    val amount: String = "",
    val time: String = "",
    val type: String = "",
    val value: Double = 0.0,
    val date: String = "",
    val wallet_id: Int = 0,
    val currency: String = "",
    val id: Int = 0
)

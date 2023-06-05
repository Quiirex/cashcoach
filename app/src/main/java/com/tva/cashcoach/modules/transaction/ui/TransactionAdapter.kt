package com.tva.cashcoach.modules.transaction.ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tva.cashcoach.R
import com.tva.cashcoach.infrastructure.application.App
import com.tva.cashcoach.infrastructure.model.transaction.Transaction
import com.tva.cashcoach.infrastructure.persistence.repository.transaction.TransactionRepository
import com.tva.cashcoach.infrastructure.utility.PreferenceHelper
import com.tva.cashcoach.databinding.RowTransactionBinding
import com.tva.cashcoach.modules.transaction.data.model.TransactionRowModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransactionAdapter(
    private val transactionRepository: TransactionRepository,
    val preferenceHelper: PreferenceHelper
) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    private var clickListener: OnItemClickListener? = null
    private var transactions: List<Transaction> = emptyList()
    private var filtered: MutableList<Transaction> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        val transactionRowModel = TransactionRowModel(
            transaction.category,
            transaction.description,
            transaction.value.toString(),
            transaction.date.toString(),
            transaction.type,
            transaction.value,
            transaction.date.toString(),
            transaction.wallet_id,
            transaction.currency,
            transaction.id ?: 0
        )
        holder.binding.transactionRowModel = transactionRowModel
        holder.binding.txtCategory.text = transaction.category
        holder.binding.txtDescription.text = transaction.description
        holder.binding.imageTransaction.setImageResource(
            when (transaction.category) {
                App.getInstance().resources.getString(R.string.food_drinks) -> R.drawable.baseline_food_drinks
                App.getInstance().resources.getString(R.string.clothes_shoes) -> R.drawable.baseline_clothes_shoes
                App.getInstance().resources.getString(R.string.transportation) -> R.drawable.baseline_transportation
                App.getInstance().resources.getString(R.string.health_beauty) -> R.drawable.baseline_health_beauty
                App.getInstance().resources.getString(R.string.home_utilities) -> R.drawable.baseline_home_utilities
                App.getInstance().resources.getString(R.string.entertainment) -> R.drawable.baseline_entertainment
                App.getInstance().resources.getString(R.string.gifts_donations) -> R.drawable.baseline_gifts
                App.getInstance().resources.getString(R.string.education) -> R.drawable.baseline_education
                App.getInstance().resources.getString(R.string.other) -> R.drawable.baseline_attach_money
                else -> R.drawable.baseline_attach_money
            }
        )

        val date = formatDate(transaction.date.toString())
        holder.binding.txtTime.text = date

        val amountColorRes = when (transaction.type) {
            "income" -> R.color.teal_400
            "expense" -> R.color.red_A200
            else -> R.color.black
        }
        val amountColor = holder.itemView.context.resources.getColor(amountColorRes)
        holder.binding.txtAmount.setTextColor(amountColor)

        val currency = preferenceHelper.getString("curr_user_currency", "")
        val amountWithCurrency = when (transaction.type) {
            "income" -> {
                val formattedValue = when {
                    transaction.currency == "EUR" && currency == "USD" ->
                        (transaction.value * 1.07).formatDecimal()
                    transaction.currency == "USD" && currency == "EUR" ->
                        (transaction.value / 1.07).formatDecimal()
                    else ->
                        transaction.value.formatDecimal()
                }
                "+ $formattedValue${if (currency == "EUR") "€" else "$"}"
            }
            "expense" -> {
                val formattedValue = when {
                    transaction.currency == "EUR" && currency == "USD" ->
                        (transaction.value * 1.07).formatDecimal()
                    transaction.currency == "USD" && currency == "EUR" ->
                        (transaction.value / 1.07).formatDecimal()
                    else ->
                        transaction.value.formatDecimal()
                }
                "- $formattedValue${if (currency == "EUR") "€" else "$"}"
            }
            else -> ""
        }
        holder.binding.txtAmount.text = amountWithCurrency

        holder.itemView.setOnClickListener {
            clickListener?.onItemClick(it, position, transactionRowModel)
        }
    }

    private fun Double.formatDecimal(): String {
        return if (this >= 0) {
            "%.2f".format(this)
        } else {
            "-%.2f".format(-this)
        }
    }


    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.US)
        val outputFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)
        val outputFormatToday = SimpleDateFormat("HH:mm", Locale.US)
        val date = inputFormat.parse(dateString) ?: Date()

        val todayString = Date().toString()
        val inputToday = SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.US)
        val outputToday = SimpleDateFormat("dd.MM.yyyy", Locale.US)
        val today = inputToday.parse(todayString) ?: Date()

        return if (outputToday.format(today) == outputFormat.format(date)) {
            outputFormatToday.format(date)
        } else {
            outputFormat.format(date)
        }
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    fun setOnItemClickListener(clickListener: OnItemClickListener) {
        this.clickListener = clickListener
    }

    interface OnItemClickListener {
        fun onItemClick(
            view: View,
            position: Int,
            transaction: TransactionRowModel
        ) {
        }
    }

    inner class TransactionViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val binding: RowTransactionBinding = RowTransactionBinding.bind(itemView)
    }

    suspend fun fetchTransactions(wallet_id: String) {
        transactions = withContext(Dispatchers.IO) {
            transactionRepository.getAll(wallet_id)
        }
        notifyDataSetChanged()
    }


    suspend fun fetchAllTransactions(wallet_id: String): List<Transaction> {
        return withContext(Dispatchers.IO) {
            transactionRepository.getAllTransactions(wallet_id)
        }
        notifyDataSetChanged()
    }

    suspend fun fetchIncomesSum(wallet_id: String, currency: String): Double {
        return withContext(Dispatchers.IO) {
            transactionRepository.getIncomesSum(wallet_id, currency)
        }
        notifyDataSetChanged()
    }

    suspend fun fetchExpensesSum(wallet_id: String, currency: String): Double {
        return withContext(Dispatchers.IO) {
            transactionRepository.getExpensesSum(wallet_id, currency)
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterTransactions(
        startDate: Date?,
        endDate: Date?,
        transactionType: String?,
        sortBy: String?,
        categories: Array<String>?
    ) {
        Log.d(
            "filterTransactions",
            "startDate: $startDate, endDate: $endDate, transaction: $transactionType, sortBy: $sortBy, categories: $categories"
        )
        GlobalScope.launch(Dispatchers.IO) {
            var transactions = transactionRepository.getAllTransactions(
                preferenceHelper.getString(
                    "curr_wallet_id",
                    ""
                )
            )

            for (transaction in transactions) {
                if (transaction.date >= startDate && transaction.date <= endDate) {
                    if (transactionType == "Income") {
                        if (categories != null) {
                            if (transaction.type.equals("income") && categories.contains(transaction.category)) {
                                filtered.add(transaction);
                            }
                        }

                    } else if (transactionType == "Expense") {
                        if (categories != null) {
                            if (transaction.type.equals("expense") && categories.contains(
                                    transaction.category
                                )
                            ) {
                                filtered.add(transaction);
                            }
                        }
                    } else {
                        if (categories != null) {
                            if (categories.contains(transaction.category)) {
                                filtered.add(transaction);
                            }
                        }
                    }
                }
            }
//            when (sortBy) {
//                "Newest" -> {
//                    filtered = filtered.sortedBy { it.date } as MutableList<Transaction>
//                }
//                "Oldest" -> {
//                    filtered = filtered.sortedByDescending { it.date } as MutableList<Transaction>
//                }
//                "Highest" -> {
//                    filtered = filtered.sortedByDescending { it.value } as MutableList<Transaction>
//                }
//                "Lowest" -> {
//                    filtered = filtered.sortedBy { it.value } as MutableList<Transaction>
//                }
//            }

        }
        transactions = filtered
        filtered.clear()
        notifyDataSetChanged()

    }

}

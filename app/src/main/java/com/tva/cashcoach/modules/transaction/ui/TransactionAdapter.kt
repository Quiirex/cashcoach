package com.tva.cashcoach.modules.transaction.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.model.transaction.Transaction
import com.tva.cashcoach.appcomponents.persistence.repository.transaction.TransactionRepository
import com.tva.cashcoach.appcomponents.utility.PreferenceHelper
import com.tva.cashcoach.databinding.RowTransactionBinding
import com.tva.cashcoach.modules.transaction.data.model.TransactionRowModel
import kotlinx.coroutines.Dispatchers
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        val transactionRowModel = TransactionRowModel(
            transaction.category_id.toString(),
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
        holder.binding.txtCategory.text = "ID KATEGORIJE"//transaction.category_id.toString()
        holder.binding.txtDescription.text = transaction.description
        holder.binding.txtTime.text = transaction.date.toString()

        val date = formatDate(transaction.date.toString())
        holder.binding.txtTime.text = date

        val amountColorRes = when (transaction.type) {
            "income" -> R.color.teal_400
            "expense" -> R.color.red_A200
            else -> R.color.black
        }
        val amountColor = holder.itemView.context.resources.getColor(amountColorRes)
        holder.binding.txtAmount.setTextColor(amountColor)

        if (preferenceHelper.getString("curr_user_currency", "") == "EUR") {
            val amountWithCurrency = String.format("%.2f€", transaction.value)
            holder.binding.txtAmount.text = amountWithCurrency
        } else {
            val amountWithCurrency = String.format("%.2f$", transaction.value)
            holder.binding.txtAmount.text = amountWithCurrency
        }
        holder.itemView.setOnClickListener {
            clickListener?.onItemClick(it, position, transactionRowModel)
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

    suspend fun fetchIncomesSum(wallet_id: String): Double {
        return withContext(Dispatchers.IO) {
            transactionRepository.getIncomesSum(wallet_id)
        }
        notifyDataSetChanged()
    }

    suspend fun fetchExpensesSum(wallet_id: String): Double {
        return withContext(Dispatchers.IO) {
            transactionRepository.getExpensesSum(wallet_id)
        }
        notifyDataSetChanged()
    }
}

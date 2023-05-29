package com.tva.cashcoach.modules.transaction.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.model.transaction.Transaction
import com.tva.cashcoach.appcomponents.model.wallet.Wallet
import com.tva.cashcoach.appcomponents.persistence.repository.transaction.TransactionRepository
import com.tva.cashcoach.appcomponents.persistence.repository.wallet.WalletRepository
import com.tva.cashcoach.appcomponents.utility.PreferenceHelper
import com.tva.cashcoach.databinding.RowTransactionBinding
import com.tva.cashcoach.databinding.RowWalletBinding
import com.tva.cashcoach.modules.transaction.data.model.TransactionModel
import com.tva.cashcoach.modules.wallets.data.model.WalletsRowModel
import com.tva.cashcoach.modules.wallets.ui.WalletsAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionAdapter(
    private val transactionRepository: TransactionRepository,
    val preferenceHelper: PreferenceHelper
    ) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {
    private var clickListener: TransactionAdapter.OnItemClickListener? = null

    fun setOnItemClickListener(clickListener: TransactionAdapter.OnItemClickListener) {
        this.clickListener = clickListener
    }

    interface OnItemClickListener {
        fun onItemClick(
            view: View,
            position: Int,
            item: WalletsRowModel
        ) {
        }
    }

    private var transactions: List<Transaction> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        val transactionRowModel = TransactionModel(transaction.category_id.toString(), transaction.description, transaction.value.toString(), transaction.date.toString())
        holder.binding.transactionRowModel = transactionRowModel
        holder.binding.txtCategory.text = transaction.category_id.toString()
        holder.binding.txtDescription.text = transaction.description
        holder.binding.txtAmount.text = transaction.value.toString()
        holder.binding.txtTime.text = transaction.date.toString()
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    inner class TransactionViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val binding: RowTransactionBinding = RowTransactionBinding.bind(itemView)
    }

    suspend fun fetchTransactions() {
        transactions = withContext(Dispatchers.IO) {
            transactionRepository.allTransactions
        }
        notifyDataSetChanged()
    }
}

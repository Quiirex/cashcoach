package com.tva.cashcoach.modules.accounts.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tva.cashcoach.R
import com.tva.cashcoach.databinding.RowAccountsBinding
import com.tva.cashcoach.modules.accounts.data.model.AccountsRowModel

class AccountsAdapter(
    var list: List<AccountsRowModel>
) : RecyclerView.Adapter<AccountsAdapter.RowAccountsVH>() {
    private var clickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowAccountsVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_accounts, parent, false)
        return RowAccountsVH(view)
    }

    override fun onBindViewHolder(holder: RowAccountsVH, position: Int) {
        val accountsRowModel = AccountsRowModel()
        // TODO uncomment following line after integration with data source
        // val accountsRowModel = list[position]
        holder.binding.accountsRowModel = accountsRowModel
    }

    override fun getItemCount(): Int = 2
    // TODO uncomment following line after integration with data source
    // return list.size

    fun updateData(newData: List<AccountsRowModel>) {
        list = newData
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(clickListener: OnItemClickListener) {
        this.clickListener = clickListener
    }

    interface OnItemClickListener {
        fun onItemClick(
            view: View,
            position: Int,
            item: AccountsRowModel
        ) {
        }
    }

    inner class RowAccountsVH(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val binding: RowAccountsBinding = RowAccountsBinding.bind(itemView)

        init {
            /*binding.btnRowprice.setOnClickListener {
                // TODO replace with value from datasource
                clickListener?.onItemClick(it, adapterPosition, AccountsRowModel())
            }*/
        }
    }
}

package com.tva.cashcoach.modules.wallets.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tva.cashcoach.R
import com.tva.cashcoach.databinding.RowAccountsBinding
import com.tva.cashcoach.modules.wallets.data.model.WalletsRowModel

class WalletsAdapter(
    var list: List<WalletsRowModel>
) : RecyclerView.Adapter<WalletsAdapter.RowAccountsVH>() {
    private var clickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowAccountsVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_accounts, parent, false)
        return RowAccountsVH(view)
    }

    override fun onBindViewHolder(holder: RowAccountsVH, position: Int) {
        val walletsRowModel = WalletsRowModel()
        // TODO uncomment following line after integration with data source
        // val accountsRowModel = list[position]
        holder.binding.accountsRowModel = walletsRowModel
    }

    override fun getItemCount(): Int = 2
    // TODO uncomment following line after integration with data source
    // return list.size

    fun updateData(newData: List<WalletsRowModel>) {
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
            item: WalletsRowModel
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

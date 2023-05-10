package com.tva.cashcoach.modules.financialreportlinechart.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tva.cashcoach.R
import com.tva.cashcoach.databinding.RowListtrash1Binding
import com.tva.cashcoach.modules.financialreportlinechart.data.model.Listtrash1RowModel

class ListtrashAdapter(
    var list: List<Listtrash1RowModel>
) : RecyclerView.Adapter<ListtrashAdapter.RowListtrash1VH>() {
    private var clickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowListtrash1VH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_listtrash1, parent, false)
        return RowListtrash1VH(view)
    }

    override fun onBindViewHolder(holder: RowListtrash1VH, position: Int) {
        val listtrash1RowModel = Listtrash1RowModel()
        // TODO uncomment following line after integration with data source
        // val listtrash1RowModel = list[position]
        holder.binding.listtrash1RowModel = listtrash1RowModel
    }

    override fun getItemCount(): Int = 3
    // TODO uncomment following line after integration with data source
    // return list.size

    public fun updateData(newData: List<Listtrash1RowModel>) {
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
            item: Listtrash1RowModel
        ) {
        }
    }

    inner class RowListtrash1VH(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val binding: RowListtrash1Binding = RowListtrash1Binding.bind(itemView)
    }
}

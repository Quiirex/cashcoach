package com.tva.cashcoach.modules.transaction.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tva.cashcoach.R
import com.tva.cashcoach.databinding.RowListtrashBinding
import com.tva.cashcoach.modules.transaction.data.model.ListtrashRowModel

class ListtrashAdapter(
    var list: List<ListtrashRowModel>
) : RecyclerView.Adapter<ListtrashAdapter.RowListtrashVH>() {
    private var clickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowListtrashVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_listtrash, parent, false)
        return RowListtrashVH(view)
    }

    override fun onBindViewHolder(holder: RowListtrashVH, position: Int) {
        val listtrashRowModel = ListtrashRowModel()
        // TODO uncomment following line after integration with data source
        // val listtrashRowModel = list[position]
        holder.binding.listtrashRowModel = listtrashRowModel
    }

    override fun getItemCount(): Int = 3
    // TODO uncomment following line after integration with data source
    // return list.size

    public fun updateData(newData: List<ListtrashRowModel>) {
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
            item: ListtrashRowModel
        ) {
        }
    }

    inner class RowListtrashVH(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val binding: RowListtrashBinding = RowListtrashBinding.bind(itemView)
    }
}

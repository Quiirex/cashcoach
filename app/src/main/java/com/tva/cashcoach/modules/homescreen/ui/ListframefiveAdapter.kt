package com.tva.cashcoach.modules.homescreen.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tva.cashcoach.R
import com.tva.cashcoach.databinding.RowListframefiveBinding
import com.tva.cashcoach.modules.homescreen.data.model.ListframefiveRowModel

class ListframefiveAdapter(
    var list: List<ListframefiveRowModel>
) : RecyclerView.Adapter<ListframefiveAdapter.RowListframefiveVH>() {
    private var clickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowListframefiveVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_listframefive, parent, false)
        return RowListframefiveVH(view)
    }

    override fun onBindViewHolder(holder: RowListframefiveVH, position: Int) {
        val listframefiveRowModel = ListframefiveRowModel()
        // TODO uncomment following line after integration with data source
        // val listframefiveRowModel = list[position]
        holder.binding.listframefiveRowModel = listframefiveRowModel
    }

    override fun getItemCount(): Int = 3
    // TODO uncomment following line after integration with data source
    // return list.size

    fun updateData(newData: List<ListframefiveRowModel>) {
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
            item: ListframefiveRowModel
        ) {
        }
    }

    inner class RowListframefiveVH(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val binding: RowListframefiveBinding = RowListframefiveBinding.bind(itemView)
    }
}

package com.tva.cashcoach.modules.homescreen.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tva.cashcoach.R
import com.tva.cashcoach.databinding.RowListeyeBinding
import com.tva.cashcoach.modules.homescreen.data.model.ListeyeRowModel

class ListeyeAdapter(
    var list: List<ListeyeRowModel>
) : RecyclerView.Adapter<ListeyeAdapter.RowListeyeVH>() {
    private var clickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowListeyeVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_listeye, parent, false)
        return RowListeyeVH(view)
    }

    override fun onBindViewHolder(holder: RowListeyeVH, position: Int) {
        val listeyeRowModel = ListeyeRowModel()
        // TODO uncomment following line after integration with data source
        // val listeyeRowModel = list[position]
        holder.binding.listeyeRowModel = listeyeRowModel
    }

    override fun getItemCount(): Int = 2
    // TODO uncomment following line after integration with data source
    // return list.size

    public fun updateData(newData: List<ListeyeRowModel>) {
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
            item: ListeyeRowModel
        ) {
        }
    }

    inner class RowListeyeVH(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val binding: RowListeyeBinding = RowListeyeBinding.bind(itemView)
    }
}

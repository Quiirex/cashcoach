package com.tva.cashcoach.modules.transaction.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tva.cashcoach.R
import com.tva.cashcoach.databinding.RowListquestionBinding
import com.tva.cashcoach.modules.transaction.data.model.ListquestionRowModel

class ListquestionAdapter(
    var list: List<ListquestionRowModel>
) : RecyclerView.Adapter<ListquestionAdapter.RowListquestionVH>() {
    private var clickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowListquestionVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_listquestion, parent, false)
        return RowListquestionVH(view)
    }

    override fun onBindViewHolder(holder: RowListquestionVH, position: Int) {
        val listquestionRowModel = ListquestionRowModel()
        // TODO uncomment following line after integration with data source
        // val listquestionRowModel = list[position]
        holder.binding.listquestionRowModel = listquestionRowModel
    }

    override fun getItemCount(): Int = 2
    // TODO uncomment following line after integration with data source
    // return list.size

    public fun updateData(newData: List<ListquestionRowModel>) {
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
            item: ListquestionRowModel
        ) {
        }
    }

    inner class RowListquestionVH(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val binding: RowListquestionBinding = RowListquestionBinding.bind(itemView)
    }
}

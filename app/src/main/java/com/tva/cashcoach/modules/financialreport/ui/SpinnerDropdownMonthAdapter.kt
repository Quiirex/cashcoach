package com.tva.cashcoach.modules.financialreport.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.tva.cashcoach.R
import com.tva.cashcoach.modules.financialreport.data.model.SpinnerDropdownMonthModel

class SpinnerDropdownMonthAdapter(
    private val mContext: Context,
    @LayoutRes
    private val layoutResource: Int,
    private val allItems: List<SpinnerDropdownMonthModel>
) : ArrayAdapter<SpinnerDropdownMonthModel>(mContext, layoutResource, allItems) {
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View = this.createView(position, convertView, parent)

    override fun getDropDownView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View = this.createView(position, convertView, parent)

    private fun createView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val item = getItem(position)
        val view = convertView ?: LayoutInflater.from(mContext)
            .inflate(layoutResource, parent, false)
        view.findViewById<TextView>(R.id.txtTitle).text = item?.itemName
        return view
    }
}

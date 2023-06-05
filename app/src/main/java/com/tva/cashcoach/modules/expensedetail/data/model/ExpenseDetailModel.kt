package com.tva.cashcoach.modules.expensedetail.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.infrastructure.application.App

data class ExpenseDetailModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtTitle: String? = App.getInstance().resources.getString(R.string.msg_detail_transact),
    /**
     * TODO Replace with dynamic value
     */
    var txtPrice: String? = App.getInstance().resources.getString(R.string.lbl_1202),
    /**
     * TODO Replace with dynamic value
     */
    var txtGroceries: String? = App.getInstance().resources.getString(R.string.lbl_groceries),
    /**
     * TODO Replace with dynamic value
     */
    var txtWeekday: String? = App.getInstance().resources.getString(R.string.msg_saturday_4_june),
    /**
     * TODO Replace with dynamic value
     */
    var txtTime: String? = App.getInstance().resources.getString(R.string.lbl_16_20),
    /**
     * TODO Replace with dynamic value
     */
    var txtDescription: String? = App.getInstance().resources.getString(R.string.lbl_description),
    /**
     * TODO Replace with dynamic value
     */
    var txtDescriptionOne: String? =
        App.getInstance().resources.getString(R.string.msg_amet_minim_moll),
    /**
     * TODO Replace with dynamic value
     */
    var txtAttachment: String? = App.getInstance().resources.getString(R.string.lbl_attachment),
    /**
     * TODO Replace with dynamic value
     */
    var txtType: String? = App.getInstance().resources.getString(R.string.lbl_type),
    /**
     * TODO Replace with dynamic value
     */
    var txtExpense: String? = App.getInstance().resources.getString(R.string.lbl_expense),
    /**
     * TODO Replace with dynamic value
     */
    var txtCategory: String? = App.getInstance().resources.getString(R.string.lbl_category_with_colon),
    /**
     * TODO Replace with dynamic value
     */
    var txtShopping: String? = App.getInstance().resources.getString(R.string.lbl_shopping),
    /**
     * TODO Replace with dynamic value
     */
    var txtWallet: String? = App.getInstance().resources.getString(R.string.lbl_wallet_with_colon),
    /**
     * TODO Replace with dynamic value
     */
    var txtMainWallet: String? = App.getInstance().resources.getString(R.string.lbl_main_wallet)

)

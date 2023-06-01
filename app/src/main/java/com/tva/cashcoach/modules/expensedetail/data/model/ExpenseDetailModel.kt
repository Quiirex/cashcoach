package com.tva.cashcoach.modules.expensedetail.`data`.model

import com.tva.cashcoach.R
import com.tva.cashcoach.appcomponents.di.MyApp

data class ExpenseDetailModel(
    /**
     * TODO Replace with dynamic value
     */
    var txtTitle: String? = MyApp.getInstance().resources.getString(R.string.msg_detail_transact),
    /**
     * TODO Replace with dynamic value
     */
    var txtPrice: String? = MyApp.getInstance().resources.getString(R.string.lbl_1202),
    /**
     * TODO Replace with dynamic value
     */
    var txtGroceries: String? = MyApp.getInstance().resources.getString(R.string.lbl_groceries),
    /**
     * TODO Replace with dynamic value
     */
    var txtWeekday: String? = MyApp.getInstance().resources.getString(R.string.msg_saturday_4_june),
    /**
     * TODO Replace with dynamic value
     */
    var txtTime: String? = MyApp.getInstance().resources.getString(R.string.lbl_16_20),
    /**
     * TODO Replace with dynamic value
     */
    var txtDescription: String? = MyApp.getInstance().resources.getString(R.string.lbl_description),
    /**
     * TODO Replace with dynamic value
     */
    var txtDescriptionOne: String? =
        MyApp.getInstance().resources.getString(R.string.msg_amet_minim_moll),
    /**
     * TODO Replace with dynamic value
     */
    var txtAttachment: String? = MyApp.getInstance().resources.getString(R.string.lbl_attachment),
    /**
     * TODO Replace with dynamic value
     */
    var txtType: String? = MyApp.getInstance().resources.getString(R.string.lbl_type),
    /**
     * TODO Replace with dynamic value
     */
    var txtExpense: String? = MyApp.getInstance().resources.getString(R.string.lbl_expense),
    /**
     * TODO Replace with dynamic value
     */
    var txtCategory: String? = MyApp.getInstance().resources.getString(R.string.lbl_category_with_colon),
    /**
     * TODO Replace with dynamic value
     */
    var txtShopping: String? = MyApp.getInstance().resources.getString(R.string.lbl_shopping),
    /**
     * TODO Replace with dynamic value
     */
    var txtWallet: String? = MyApp.getInstance().resources.getString(R.string.lbl_wallet_with_colon),
    /**
     * TODO Replace with dynamic value
     */
    var txtMainWallet: String? = MyApp.getInstance().resources.getString(R.string.lbl_main_wallet)

)

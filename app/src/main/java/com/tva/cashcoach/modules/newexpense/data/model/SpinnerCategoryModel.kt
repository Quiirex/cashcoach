package com.tva.cashcoach.modules.newexpense.`data`.model

import com.tva.cashcoach.infrastructure.model.category.Category

data class SpinnerCategoryModel(
    val itemName: String
) {
    companion object {
        fun fromCategoryList(categories: List<Category>): Any {
            val spinnerCategoryList = mutableListOf<SpinnerCategoryModel>()
            categories.forEach {
                spinnerCategoryList.add(SpinnerCategoryModel(it.name))
            }
            return spinnerCategoryList
        }
    }
}

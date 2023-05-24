package com.tva.cashcoach.appcomponents.persistence.repository.category

import androidx.lifecycle.LiveData
import com.tva.cashcoach.appcomponents.model.category.Category
import com.tva.cashcoach.appcomponents.model.category.CategoryDao

class CategoryRepository(private val categoryDao: CategoryDao) {

    val allCategories: LiveData<List<Category>> = categoryDao.getAll()

    fun insert(category: Category) {
        categoryDao.insert(category)
    }

    fun delete(category: Category) {
        categoryDao.delete(category)
    }

    fun update(category: Category) {
        categoryDao.update(category)
    }

    fun wipeTable() {
        categoryDao.wipeTable()
    }
}
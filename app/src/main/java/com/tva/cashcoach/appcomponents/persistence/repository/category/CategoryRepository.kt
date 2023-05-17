package com.tva.cashcoach.appcomponents.persistence.repository.category

import androidx.lifecycle.LiveData
import com.tva.cashcoach.appcomponents.model.category.Category
import com.tva.cashcoach.appcomponents.model.category.CategoryDao

class CategoryRepository(private val categoryDao: CategoryDao) {

    val allCategories: LiveData<List<Category>> = categoryDao.getAll()

    suspend fun insert(category: Category) {
        categoryDao.insert(category)
    }

    suspend fun delete(category: Category) {
        categoryDao.delete(category)
    }

    suspend fun update(category: Category) {
        categoryDao.update(category)
    }

    suspend fun wipeTable() {
        categoryDao.wipeTable()
    }
}
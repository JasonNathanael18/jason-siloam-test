package com.example.domain.repository

import com.example.domain.model.Meal
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow


interface MealRepository {
    fun getMealList(query: String): Flow<Resource<List<Meal>>>
    fun getMealDetail(idMeal: String): Flow<Resource<List<Meal>>>
}
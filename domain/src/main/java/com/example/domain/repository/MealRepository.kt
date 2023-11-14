package com.example.domain.repository

import com.example.domain.model.Meal
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow


interface MealRepository {
    fun getMealList(): Flow<Resource<List<Meal>>>
}
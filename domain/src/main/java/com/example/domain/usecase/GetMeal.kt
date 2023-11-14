package com.example.domain.usecase

import com.example.domain.model.Meal
import com.example.domain.repository.MealRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetMeal(
    private val mealRepository: MealRepository
) {

    fun getMealList(query: String = ""): Flow<Resource<List<Meal>>> {
        return mealRepository.getMealList(query)
    }

    fun getMealDetail(idMeal: String = ""): Flow<Resource<List<Meal>>> {
        return mealRepository.getMealDetail(idMeal)
    }
}
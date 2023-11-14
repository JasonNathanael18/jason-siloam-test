package com.example.data.repository

import com.example.data.source.local.roomdb.dao.MealDao
import com.example.data.source.local.roomdb.dao.MealResponseDao
import com.example.data.source.remote.MealApiService
import com.example.domain.model.Meal
import com.example.domain.repository.MealRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val mealApiService: MealApiService,
    private val mealResponseDao: MealResponseDao,
    private val mealDao: MealDao
) : MealRepository {
    override fun getMealList(query: String): Flow<Resource<List<Meal>>> = flow {
        emit(Resource.Loading())
        try {
            val remoteMealList = mealApiService.getMealList(query)

            if (!remoteMealList.results.isNullOrEmpty()) {
                val mealResponseEntity = remoteMealList.toMealResponseEntity()
                mealResponseEntity?.let {
                    mealResponseDao.insertMealResponse(remoteMealList.toMealResponseEntity()!!)
                    mealDao.insertMealList(remoteMealList.results.map { it.toMealEntity() }) //now insert newly fetched data to db
                    emit(Resource.Success(mealResponseEntity.toMealList().results))
                }
            } else {
                emit(
                    Resource.Error(
                        message = "Oops, data not found"
                    )
                )
            }
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection."
                )
            )
        }
//        // single source of truth we will emit data from db only and not directly from remote
//        emit(Resource.Success(getMealsFromDb(mealDao)))
    }


    private suspend fun getMealsFromDb(mealDao: MealDao): List<Meal> {
        val newmeals = mealDao.getMealList().map { it.toMeal() }
        return newmeals
    }

    override fun getMealDetail(idMeal: String): Flow<Resource<List<Meal>>> = flow {
        emit(Resource.Loading())
        try {
            val remoteMealList = mealApiService.getMealDetail(idMeal)

            if (!remoteMealList.results.isNullOrEmpty()) {
                val mealResponseEntity = remoteMealList.toMealResponseEntity()
                mealResponseEntity?.let {
                    mealResponseDao.insertMealResponse(remoteMealList.toMealResponseEntity()!!)
                    mealDao.insertMealList(remoteMealList.results.map { it.toMealEntity() }) //now insert newly fetched data to db
                    emit(Resource.Success(mealResponseEntity.toMealList().results))
                }
            } else {
                emit(
                    Resource.Error(
                        message = "Oops, data not found"
                    )
                )
            }
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection."
                )
            )
        }
//        // single source of truth we will emit data from db only and not directly from remote
//        emit(Resource.Success(getMealsFromDb(mealDao)))
    }

}
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
    override fun getMealList(): Flow<Resource<List<Meal>>> = flow {
        emit(Resource.Loading())
        try {
            fetchAndInsertMealList(mealApiService, mealResponseDao, mealDao)
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
        // single source of truth we will emit data from db only and not directly from remote
        emit(Resource.Success(getPopularMoviesFromDb(mealDao)))
    }

    private suspend fun fetchAndInsertMealList(
        mealApiService: MealApiService,
        mealResponseDao: MealResponseDao,
        mealDao: MealDao
    ) {
        val remoteMealList = mealApiService.getMealList()
        mealResponseDao.insertMealResponse(remoteMealList.toMealResponseEntity())
        mealDao.insertMealList(remoteMealList.results.map { it.toMealEntity() }) //now insert newly fetched data to db
    }


    private suspend fun getPopularMoviesFromDb(mealDao: MealDao): List<Meal> {
        val newPopularMovies = mealDao.getMealList().map { it.toMeal() }
        return newPopularMovies
    }


}
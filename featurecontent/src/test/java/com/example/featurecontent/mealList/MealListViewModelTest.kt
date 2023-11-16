package com.example.featurecontent.mealList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.model.Meal
import com.example.domain.usecase.GetMeal
import com.example.featurecontent.TestCoroutineRule
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import app.cash.turbine.test
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.mockito.Mockito.doReturn

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MealListViewModelTest : TestCase() {
    @get:Rule
    var taskRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getMeal: GetMeal

    @Test
    fun `when get meal return SUCCESS and empty list`() {
        runTest {
            val responseList: List<Meal> = listOf()
            doReturn(flowOf(Resource.Success(responseList))).`when`(getMeal).getMealList("a")
            val viewModel =
                MealListViewModel(getMeal)
            viewModel.getMealList()
            viewModel.uiState.test {
                assertEquals(MealUiState.MealListEmpty(
                    isLoading = false,
                    error = "",
                    isPreviousPageLoaded = false
                ), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when get meal return SUCCESS and has list`() {
        runTest {
            val responseList: List<Meal> = listOf(
                Meal(
                    idMeal = "idMeal",
                    strMeal = "strMeal",
                    strCategory = "strCategory",
                    strArea = "strArea",
                    strMealThumb = "strMealThumb",
                    strTags = "strTags"
                )
            )
            doReturn(flowOf(Resource.Success(responseList))).`when`(getMeal).getMealList("a")
            val viewModel =
                MealListViewModel(getMeal)
            viewModel.getMealList()
            viewModel.uiState.test {
                assertEquals(MealUiState.HasMealList(
                    isLoading = false,
                    error = "",
                    mealList = responseList
                ), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when requestSearch should return Loading`() {
        runTest {
            val responseList: List<Meal> = listOf()
            doReturn(flowOf(Resource.Loading(responseList))).`when`(getMeal).getMealList("a")
            val viewModel = MealListViewModel(getMeal)
            viewModel.requestSearch("a")
            viewModel.uiState.test {
                assertEquals(MealUiState.MealListEmpty(
                    isLoading = true,
                    error = "",
                    isPreviousPageLoaded = false
                ), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when requestSearch should return ERROR`() {
        runTest {
            val responseList: List<Meal> = listOf()
            doReturn(flowOf(Resource.Error(message = "error", responseList))).`when`(getMeal).getMealList("a")
            val viewModel = MealListViewModel(getMeal)
            viewModel.requestSearch("a")
            viewModel.uiState.test {
                assertEquals(MealUiState.MealListEmpty(
                    isLoading = false,
                    error = "error",
                    isPreviousPageLoaded = false
                ), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}
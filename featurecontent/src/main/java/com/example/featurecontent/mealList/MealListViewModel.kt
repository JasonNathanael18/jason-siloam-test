package com.example.featurecontent.mealList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Meal
import com.example.domain.usecase.GetMeal
import com.example.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealListViewModel @Inject constructor(
    private val getMeal: GetMeal,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(MealViewModelState(isLoading = true))

    val uiState = viewModelState
        .map(MealViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    var query = "a"

    fun getMealList() {
        viewModelScope.launch {
            getMeal.getMealList(query).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        viewModelState.update {
                            it.copy(error = "", isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { list ->
                            viewModelState.update {
                                it.copy(mealList = list, isLoading = false)
                            }
                        }
                    }
                    is Resource.Error -> {
                        viewModelState.update {
                            it.copy(
                                error = result.message ?: "",
                                mealList = listOf(),
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun requestSearch(query: String) {
        this.query = query
        getMealList()
    }

}

private data class MealViewModelState(
    val isLoading: Boolean = false,
    val error: String = "",
    val mealList: List<Meal> = listOf(),
    val isPreviousPageLoaded: Boolean = false
) {
    fun toUiState(): MealUiState =
        if (mealList.isEmpty()) MealUiState.MealListEmpty(
            isLoading = isLoading,
            error = error,
            isPreviousPageLoaded = isPreviousPageLoaded
        )
        else MealUiState.HasMealList(isLoading = isLoading, error = error, mealList = mealList)
}

sealed interface MealUiState {
    val isLoading: Boolean
    val error: String

    data class HasMealList(
        val mealList: List<Meal>,
        override val isLoading: Boolean,
        override val error: String
    ) : MealUiState

    data class MealListEmpty(
        override val isLoading: Boolean,
        override val error: String,
        val isPreviousPageLoaded: Boolean
    ) : MealUiState
}
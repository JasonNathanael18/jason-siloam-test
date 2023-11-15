package com.example.featurecontent.mealDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Meal
import com.example.domain.usecase.AuthUseCase
import com.example.domain.usecase.GetMeal
import com.example.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailViewModel @Inject constructor(
    private val getMeal: GetMeal,
    private val authUseCase: AuthUseCase,
) : ViewModel() {

    private val viewModelState = MutableStateFlow(MealDetailViewModelState(isLoading = true))
    private val authViewModelState = MutableStateFlow(AuthViewModelState(isLoading = true))

    val uiState = viewModelState
        .map(MealDetailViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    val authUiState = authViewModelState
        .map(AuthViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            authViewModelState.value.toUiState()
        )

    fun getMeal(idMeal : String) {
        viewModelScope.launch {
            getMeal.getMealDetail(idMeal).collect { result ->
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



    fun getCredential(username: String, password: String) {
        viewModelScope.launch {
            authUseCase.checkIfUserCredentialExist(username,password).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        authViewModelState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { isCredentialFound->
                            authViewModelState.update {
                                it.copy(isCredentialExist = isCredentialFound, isLoading = false)
                            }
                        }
                    }
                    is Resource.Error -> {
                        authViewModelState.update {
                            it.copy(isCredentialExist = false, isLoading = false)
                        }
                    }
                }
            }
        }
    }

}

private data class MealDetailViewModelState(
    val isLoading: Boolean = false,
    val error: String = "",
    val mealList: List<Meal> = listOf(),
    val isPreviousPageLoaded: Boolean = false
) {
    fun toUiState(): MealDetailUiState =
        if (mealList.isEmpty()) MealDetailUiState.MealDetailEmpty(
            isLoading = isLoading,
            error = error,
            isPreviousPageLoaded = isPreviousPageLoaded
        )
        else MealDetailUiState.HasMealDetail(isLoading = isLoading, error = error, mealList = mealList)
}

sealed interface MealDetailUiState {
    val isLoading: Boolean
    val error: String

    data class HasMealDetail(
        val mealList: List<Meal>,
        override val isLoading: Boolean,
        override val error: String
    ) : MealDetailUiState

    data class MealDetailEmpty(
        override val isLoading: Boolean,
        override val error: String,
        val isPreviousPageLoaded: Boolean
    ) : MealDetailUiState
}

private data class AuthViewModelState(
    val isLoading: Boolean = false,
    val isCredentialExist: Boolean = false
) {
    fun toUiState(): AuthUiState =
        AuthUiState.IsCredentialFound(isCredentialExist = isCredentialExist, isLoading = isLoading)
}

sealed interface AuthUiState {
    val isCredentialExist: Boolean
    val isLoading: Boolean

    data class IsCredentialFound(
        override val isCredentialExist: Boolean,
        override val isLoading: Boolean
    ) : AuthUiState
}
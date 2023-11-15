package com.example.featureauth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.AuthUseCase
import com.example.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
) : ViewModel() {

    private val registerViewModelState = MutableStateFlow(RegisterViewModelState(isLoading = true))

    val authUiState = registerViewModelState
        .map(RegisterViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            registerViewModelState.value.toUiState()
        )

    fun checkUserNameAvailability(username: String) {
        viewModelScope.launch {
            authUseCase.checkIfUserNameAvailable(username).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        registerViewModelState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { isUserNameAvailable->
                            registerViewModelState.update {
                                it.copy(isUserNameAvailable = isUserNameAvailable, isLoading = false)
                            }
                        }
                    }
                    is Resource.Error -> {
                        registerViewModelState.update {
                            it.copy(isUserNameAvailable = false, isLoading = false)
                        }
                    }
                }
            }
        }
    }

    fun insertData(username: String, password: String) {
        viewModelScope.launch {
            authUseCase.insertNewUserCredential(username,password)
        }
    }
}

private data class RegisterViewModelState(
    val isLoading: Boolean = false,
    val isUserNameAvailable: Boolean = false
) {
    fun toUiState(): RegisterUiState =
        RegisterUiState.IsUserNameFound(isUserNameAvailable = isUserNameAvailable, isLoading = isLoading)
}

sealed interface RegisterUiState {
    val isUserNameAvailable: Boolean
    val isLoading: Boolean

    data class IsUserNameFound(
        override val isUserNameAvailable: Boolean,
        override val isLoading: Boolean
    ) : RegisterUiState
}
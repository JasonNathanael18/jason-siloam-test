package com.example.featureauth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.AuthUseCase
import com.example.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase,
) : ViewModel() {

    private val authViewModelState = MutableStateFlow(AuthViewModelState(isLoading = true))

    val authUiState = authViewModelState
        .map(AuthViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            authViewModelState.value.toUiState()
        )

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
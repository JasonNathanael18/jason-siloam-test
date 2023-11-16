package com.example.featureauth.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.domain.usecase.AuthUseCase
import com.example.domain.utils.Resource
import com.example.featureauth.TestCoroutineRule
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest : TestCase() {
    @get:Rule
    var taskRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var authUseCase: AuthUseCase

    @Test
    fun `when get credential return SUCCESS and isCredentialExist`() {
        runTest {
            doReturn(flowOf(Resource.Success(true))).`when`(authUseCase)
                .checkIfUserCredentialExist("username", "password")
            val viewModel =
                LoginViewModel(authUseCase)
            viewModel.getCredential(
                "username", "password"
            )
            viewModel.authUiState.test {
                assertEquals(
                    AuthUiState.IsCredentialFound(
                        isLoading = false,
                        isCredentialExist = true
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when get credential return SUCCESS and isCredentialNotExist`() {
        runTest {
            doReturn(flowOf(Resource.Success(false))).`when`(authUseCase)
                .checkIfUserCredentialExist("username", "password")
            val viewModel =
                LoginViewModel(authUseCase)
            viewModel.getCredential(
                "username", "password"
            )
            viewModel.authUiState.test {
                assertEquals(
                    AuthUiState.IsCredentialFound(
                        isLoading = false,
                        isCredentialExist = false
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when getCredential should return Loading`() {
        runTest {
            doReturn(flowOf(Resource.Loading(true))).`when`(authUseCase)
                .checkIfUserCredentialExist("username", "password")
            val viewModel = LoginViewModel(authUseCase)
            viewModel.getCredential("username", "password")
            viewModel.authUiState.test {
                assertEquals(
                    AuthUiState.IsCredentialFound(
                        isLoading = true,
                        isCredentialExist = false
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when getCredential should return Error`() {
        runTest {
            doReturn(flowOf(Resource.Error("error", false))).`when`(authUseCase)
                .checkIfUserCredentialExist("username", "password")
            val viewModel = LoginViewModel(authUseCase)
            viewModel.getCredential("username", "password")
            viewModel.authUiState.test {
                assertEquals(
                    AuthUiState.IsCredentialFound(
                        isLoading = false,
                        isCredentialExist = false
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

}
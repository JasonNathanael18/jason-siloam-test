package com.example.featureauth.register

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
class RegisterViewModelTest : TestCase() {
    @get:Rule
    var taskRule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var authUseCase: AuthUseCase

    @Test
    fun `when checkUserNameAvailability return SUCCESS and isUserNameAvailable`() {
        runTest {
            doReturn(flowOf(Resource.Success(true))).`when`(authUseCase)
                .checkIfUserNameAvailable("username")
            val viewModel =
                RegisterViewModel(authUseCase)
            viewModel.checkUserNameAvailability(
                "username"
            )
            viewModel.authUiState.test {
                assertEquals(
                    RegisterUiState.IsUserNameFound(
                        isLoading = false,
                        isUserNameAvailable = true
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when checkUserNameAvailability return SUCCESS and isUserNameNotAvailable`() {
        runTest {
            doReturn(flowOf(Resource.Success(false))).`when`(authUseCase)
                .checkIfUserNameAvailable("username")
            val viewModel =
                RegisterViewModel(authUseCase)
            viewModel.checkUserNameAvailability(
                "username"
            )
            viewModel.authUiState.test {
                assertEquals(
                    RegisterUiState.IsUserNameFound(
                        isLoading = false,
                        isUserNameAvailable = false
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when checkUserNameAvailability should return Loading`() {
        runTest {
            doReturn(flowOf(Resource.Loading(true))).`when`(authUseCase)
                .checkIfUserNameAvailable("username")
            val viewModel = RegisterViewModel(authUseCase)
            viewModel.checkUserNameAvailability("username")
            viewModel.authUiState.test {
                assertEquals(
                    RegisterUiState.IsUserNameFound(
                        isLoading = true,
                        isUserNameAvailable = false
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
                .checkIfUserNameAvailable("username")
            val viewModel = RegisterViewModel(authUseCase)
            viewModel.checkUserNameAvailability("username")
            viewModel.authUiState.test {
                assertEquals(
                    RegisterUiState.IsUserNameFound(
                        isLoading = false,
                        isUserNameAvailable = false
                    ), awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `when insertData should do success`() {
        runTest {
            val viewModel = RegisterViewModel(authUseCase)
            viewModel.insertData("username", "password")
        }
    }
}
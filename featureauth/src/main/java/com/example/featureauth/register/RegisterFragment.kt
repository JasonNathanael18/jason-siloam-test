package com.example.featureauth.register

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.commonui.BaseFragment
import com.example.domain.SharedPreferenceModule
import com.example.featureauth.R
import com.example.featureauth.databinding.FragmentRegisterBinding
import com.example.navigation.NavigationFlow
import com.example.navigation.ToFlowNavigatable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : BaseFragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding

    private val viewModel: RegisterViewModel by viewModels()

    @Inject
    lateinit var pref: SharedPreferenceModule

    override fun initComponent() {
        super.initComponent()
        binding = FragmentRegisterBinding.bind(requireView())
    }

    override fun initObserver() {
        super.initObserver()
        observeAuth()
    }

    override fun initEventListener() {
        super.initEventListener()
        binding.btnLogin.setOnClickListener {
            binding.errorText.visibility = View.GONE
            viewModel.checkUserNameAvailability(
                binding.etUsername.text.toString()
            )
        }
    }

    private fun observeAuth() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.authUiState.collect {
                    when (it) {
                        is RegisterUiState.IsUserNameFound -> {
                            if (it.isUserNameAvailable && !it.isLoading) {
                                pref.setLoginStatus(isLogin = true)
                                binding.errorText.visibility = View.GONE
                                viewModel.insertData(
                                    binding.etUsername.text.toString(),
                                    binding.etPassword.text.toString()
                                )
                                (requireActivity() as ToFlowNavigatable).navigateToFlow(
                                    NavigationFlow.ContentFlow
                                )
                            } else if (!it.isLoading) {
                                binding.errorText.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }
}
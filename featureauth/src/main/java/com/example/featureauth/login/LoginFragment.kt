package com.example.featureauth.login

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.commonui.BaseFragment
import com.example.domain.SharedPreferenceModule
import com.example.featureauth.R
import com.example.featureauth.databinding.FragmentLoginBinding
import com.example.navigation.NavigationFlow
import com.example.navigation.ToFlowNavigatable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var pref: SharedPreferenceModule

    override fun initComponent() {
        super.initComponent()
        binding = FragmentLoginBinding.bind(requireView())
    }

    override fun initObserver() {
        super.initObserver()
        observeAuth()
    }

    override fun initEventListener() {
        super.initEventListener()
        binding.btnLogin.setOnClickListener {
            binding.errorText.visibility = View.GONE
            viewModel.getCredential(
                binding.etUsername.text.toString(),
                binding.etPassword.text.toString()
            )
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            )
        }
    }

    private fun observeAuth(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.authUiState.collect {
                    when (it) {
                        is AuthUiState.IsCredentialFound -> {
                            if (it.isCredentialExist && !it.isLoading){
                                pref.setLoginStatus(isLogin = true)
                                binding.errorText.visibility = View.GONE
                                (requireActivity() as ToFlowNavigatable).navigateToFlow(
                                    NavigationFlow.ContentFlow)
                            }
                            else if (!it.isLoading) {
                                binding.errorText.visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }
}
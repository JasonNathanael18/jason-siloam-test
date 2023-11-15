package com.example.commonui

import android.os.Bundle
import com.example.domain.SharedPreferenceModule
import com.example.navigation.NavigationFlow
import com.example.navigation.ToFlowNavigatable
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StartFragment : BaseFragment(R.layout.fragment_start) {

    @Inject
    lateinit var pref: SharedPreferenceModule

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (!pref.getLoginStatus()){
            (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.AuthenticationFlow)
        } else {
            (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.ContentFlow)
        }
    }
}
package com.example.commonui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.navigation.NavigationFlow
import com.example.navigation.ToFlowNavigatable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        // decide where to go on the first app launch, check auth tokens if login needed etc...
        super.onActivityCreated(savedInstanceState)
//        (0..1).random().let {
//            when (it) {
//                0 -> (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.MovieListFlow)
//                1 -> (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.MovieDetailFlow("From start fragment"))
//            }
//        }
        (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.ContentFlow)

        //(requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.MovieDetailFlow("From start fragment"))
    }
}
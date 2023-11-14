package com.example.navigation

import androidx.navigation.NavController

class Navigator {
    lateinit var navController: NavController

     //navigate on main thread or nav component crashes sometimes
    fun navigateToFlow(navigationFlow: NavigationFlow) = when (navigationFlow) {
        NavigationFlow.ContentFlow -> navController.navigate(MainNavGraphDirections.actionGlobalContentFlow())
        NavigationFlow.AuthenticationFlow -> navController.navigate(MainNavGraphDirections.actionGlobalAuthenticationFlow())
     }
}
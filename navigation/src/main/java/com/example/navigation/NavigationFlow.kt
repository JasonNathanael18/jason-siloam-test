package com.example.navigation

sealed class NavigationFlow {
    object ContentFlow : NavigationFlow()
    object AuthenticationFlow : NavigationFlow()
}

package com.example.korlearn2.view

sealed class Screen(val route: String) {
    object Map: Screen("map_screen")
    object Start: Screen("start_screen")
    object MainInfo: Screen("main_info_screen")
}
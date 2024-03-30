package com.example.korlearn2.view

sealed class Screen(val route: String) {
    object Map: Screen("map_screen")
    object Start: Screen("start_screen")
    object MainInfo: Screen("main_info_screen")
    object SquadsAndSpies: Screen("squads_spies_screen")
    object Month: Screen("month_screen")
    object Locations: Screen("locations_screen")
    object Upkeep: Screen("upkeep_screen")
}
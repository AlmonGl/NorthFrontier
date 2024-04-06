package com.game.northFrontier.view

sealed class Screen(val route: String) {
    object Map: Screen("map_screen")
    object Start: Screen("start_screen")
    object MainInfo: Screen("main_info_screen")
    object SquadsAndSpies: Screen("squads_spies_screen")
    object Month: Screen("month_screen")
    object Locations: Screen("locations_screen")
    object Upkeep: Screen("upkeep_screen")
    object SquadManager: Screen("Squad_manager_screen")
    object End: Screen("end_screen")
    object RulersManaging: Screen("rulers_managing_screen")
}
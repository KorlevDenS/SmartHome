package com.example.smarthome.navigation

import com.example.smarthome.R
import com.example.smarthome.constants.AppStructure

sealed class BottomItem(val title: String, val iconId: Int, val route: String) {

    data object Screen1: BottomItem("Home", R.drawable.home, AppStructure.HOME_ROUTE)
    data object Screen2: BottomItem("Devices", R.drawable.socket, AppStructure.DEVICES_ROUTE)
    data object Screen3: BottomItem("Lighting", R.drawable.lighting, AppStructure.LIGHTING_ROUTE)
    data object Screen4: BottomItem("Stats", R.drawable.stats, AppStructure.STATS_ROUTE)
    data object Screen5: BottomItem("Systems", R.drawable.systems, AppStructure.SYSTEMS_ROUTE)

}
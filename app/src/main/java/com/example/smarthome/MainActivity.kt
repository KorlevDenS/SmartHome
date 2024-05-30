package com.example.smarthome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.smarthome.constants.AppStructure
import com.example.smarthome.model.Device
import com.example.smarthome.model.HomeViewModel
import com.example.smarthome.model.Room
import com.example.smarthome.navigation.MainScreen
import com.example.smarthome.ui.theme.SmartHomeTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {

    private val myViewModel = HomeViewModel()
    override fun onCreate(savedInstanceState: Bundle?) = runBlocking {
        super.onCreate(savedInstanceState)
        setContent {
            SmartHomeTheme {
                MainScreen(viewModel = myViewModel)
            }
        }
        performBackgroundTask()
    }

    /**
     * Method is a simulation of interaction with cloud resource.
     * Operations inside this method represents Model in MVVM template.
     * Executes in high level coroutine
     * */
    @OptIn(DelicateCoroutinesApi::class)
    fun performBackgroundTask() {
        GlobalScope.launch(Dispatchers.IO) {
            myViewModel.waterCounter = 245.894
            myViewModel.energyCounter = 11543.81

            myViewModel.addRoom(Room(AppStructure.WHOLE_SPACES, true))
            myViewModel.addRoom(Room("Living room"))
            myViewModel.addRoom(Room("Bedroom"))
            val kitchen = myViewModel.addRoom(Room("Kitchen"))
            myViewModel.addRoom(Room("Bathroom"))
            val basement = myViewModel.addRoom(Room("Basement"))

            myViewModel.addDevice(Device("Nuke", true, kitchen))
            myViewModel.addDevice(Device("Teapot", true, kitchen))
            myViewModel.addDevice(Device("Toaster", true, kitchen))
            myViewModel.addDevice(Device("Lamp", true, kitchen))
            myViewModel.addDevice(Device("Fridge", true, kitchen))

            delay(20000)

            myViewModel.addDevice(Device("Toaster2", true, basement))

            println("Background task finished")
        }
    }

}
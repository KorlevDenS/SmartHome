package com.example.smarthome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.smarthome.constants.AppStructure
import com.example.smarthome.model.Device
import com.example.smarthome.model.HomeViewModel
import com.example.smarthome.model.Hygrometer
import com.example.smarthome.model.Lighting
import com.example.smarthome.model.Room
import com.example.smarthome.model.Thermostat
import com.example.smarthome.navigation.MainScreen
import com.example.smarthome.navigation.MeterStatItem
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
            myViewModel.energyCounter = 1000.00

            myViewModel.addMeterStats(MeterStatItem("01.02.2024", "29.02.2024", 43.8, "М³"))
            myViewModel.addMeterStats(MeterStatItem("01.02.2024", "29.02.2024", 332.8, "Kw"))

            myViewModel.addMeterStats(MeterStatItem("01.03.2024", "31.03.2024", 87.8, "М³"))
            myViewModel.addMeterStats(MeterStatItem("01.03.2024", "31.03.2024", 432.8, "Kw"))

            myViewModel.addMeterStats(MeterStatItem("01.04.2024", "30.04.2024", 120.8, "М³"))
            myViewModel.addMeterStats(MeterStatItem("01.04.2024", "30.04.2024", 643.8, "Kw"))

            myViewModel.addMeterStats(MeterStatItem("01.05.2024", "31.05.2024", 92.8, "М³"))
            myViewModel.addMeterStats(MeterStatItem("01.05.2024", "31.05.2024", 654.8, "Kw"))

            myViewModel.addRoom(Room(AppStructure.WHOLE_SPACES))
            val livingRoom = myViewModel.addRoom(Room("Living room"))
            myViewModel.addRoom(Room("Bedroom"))
            val kitchen = myViewModel.addRoom(Room("Kitchen"))
            myViewModel.addRoom(Room("Bathroom"))
            val basement = myViewModel.addRoom(Room("Basement"))

            myViewModel.addDevice(Lighting("Chandelier", true, livingRoom, 150.0, 2160.0))
            myViewModel.addDevice(Device("Nuke", true, kitchen, 900.0))
            myViewModel.addDevice(Device("Teapot", true, kitchen, 2000.0))
            myViewModel.addDevice(Device("Toaster", true, kitchen, 1200.0))
            myViewModel.addDevice(Lighting("Lamp1", true, kitchen, 5.0,120.0))
            myViewModel.addDevice(Device("Fridge", true, kitchen, 200.0))

            myViewModel.addDevice(Device("Toaster2", true, basement, 850.0))

            val thermostat1 = myViewModel.addDevice(
                Thermostat("Thermostat1", true, livingRoom,1.0, 24.66)
            )
            val hygrometer1 = myViewModel.addDevice(
                Hygrometer("Hygrometer", true, livingRoom, 1.0,22.1)
            )
            //delay(20000)
            myViewModel.installTemperature(thermostat1 as Thermostat, 30.00)
            myViewModel.installHumidity(hygrometer1 as Hygrometer, 28.5)

            startTurnListeners()

            println("Background task finished")
        }
    }

    private fun startTurnListeners() = runBlocking {

        //val turnedOnList = ArrayList<Device>()

        launch {
            while (true) {
                val device = myViewModel.deviceTurnChannel.receive()
                println("Inspecting ${device.name} !")
            }
        }

        launch {
            while (true) {
                delay(30000)
                if (myViewModel.turnedOnDevices.isNotEmpty()) {
                    var sum = 0.0
                    myViewModel.turnedOnDevices.forEach {
                        sum += it.powerInWatts.div(120)
                    }
                    myViewModel.energyCounter += sum.div(1000)
                }
            }
        }
    }

}
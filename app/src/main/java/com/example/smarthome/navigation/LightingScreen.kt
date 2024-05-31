package com.example.smarthome.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smarthome.R
import com.example.smarthome.model.HomeViewModel
import com.example.smarthome.model.Lighting
import com.example.smarthome.ui.theme.Purple40
import com.example.smarthome.ui.theme.PurpleGrey80

@Composable
fun LightingScreen(viewModel: HomeViewModel) {
    ScreenBox {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            TitleText(text = "Lighting")
            LightDevicesData(viewModel = viewModel)
            TitleText(text = "Devices")
            LightDevices(viewModel = viewModel)
        }
    }
}

@Composable
fun LightDevicesData(viewModel: HomeViewModel) {

    var checkedState by remember { mutableStateOf(
        viewModel.selectedDevices.filterIsInstance<Lighting>().any { it in viewModel.turnedOnDevices }
    ) }

    Box(
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp)
            .background(Color.White)
            .fillMaxWidth()
    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
        ) {

            ControlData(title = "All in area", iconId = R.drawable.lighting) {
                Switch(
                    checked = viewModel.selectedDevices.filterIsInstance<Lighting>()
                        .any { it in viewModel.turnedOnDevices },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = Purple40,
                        uncheckedTrackColor = PurpleGrey80,
                        uncheckedBorderColor = PurpleGrey80,
                        uncheckedThumbColor = Color.White
                    ),
                    onCheckedChange = {
                        checkedState = it
                        viewModel.selectedDevices.filterIsInstance<Lighting>().forEach {
                                d -> viewModel.turnDevice(d, checkedState)
                        }
                    })
            }

            SensorData(sensorItem = SensorItem(
                title = "Power / hour",
                iconId = R.drawable.energy,
                measureUnit = "w",
                value = viewModel.turnedOnDevices.filterIsInstance<Lighting>()
                    .filter { it in viewModel.selectedDevices}.sumOf { it.powerInWatts }
            ))
        }
    }
}

@Composable
fun LightDevices(viewModel: HomeViewModel) {
    LazyColumn(
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp)
    ) {
        itemsIndexed(
            items = viewModel.selectedDevices.filterIsInstance<Lighting>(),
            key = {_, device -> device.name}
        ) { _, device ->
            DeviceLayout(device = device, viewModel = viewModel,
                iconId = R.drawable.sun, value = "${device.luminance}lm")
        }
    }
}







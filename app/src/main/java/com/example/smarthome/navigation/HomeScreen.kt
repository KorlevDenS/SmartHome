package com.example.smarthome.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Switch
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarthome.R
import com.example.smarthome.model.Device
import com.example.smarthome.model.HomeViewModel
import com.example.smarthome.model.Hygrometer
import com.example.smarthome.model.Thermostat
import com.example.smarthome.ui.theme.Purple40
import com.example.smarthome.ui.theme.PurpleGrey80

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    ScreenBox {
        Column (
            modifier = Modifier.fillMaxWidth()
        ) {
            HomeSensorData(viewModel = viewModel)
            TitleText(text = "Working devices")
            WorkingDevices(viewModel = viewModel)
        }
    }
}

@Composable
fun HomeSensorData(viewModel: HomeViewModel) {
    Box(
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp)
            .background(Color.White)
            .fillMaxWidth()
    ) {
        Column {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
            ) {
                SensorData(sensorItem = SensorItem(
                    title = "Temperature",
                    iconId = R.drawable.thermostat,
                    measureUnit = "°C",
                    value = viewModel.selectedDevices.filterIsInstance<Thermostat>()
                        .map{it.temperature}.average()
                ))
                SensorData(sensorItem = SensorItem(
                    title = "Humidity",
                   iconId = R.drawable.drop,
                    measureUnit = "%",
                    value = viewModel.selectedDevices.filterIsInstance<Hygrometer>()
                        .map{it.humidity}.average()
                ))
            }
            MetersData(viewModel = viewModel)
        }
    }
}

@Composable
fun MetersData(viewModel: HomeViewModel) {
    Card(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .height(130.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(width = 1.dp, color = Color.Black)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            MeterDataLayout(title = "Energy", value = viewModel.energyCounter,
                iconId = R.drawable.energy, measureUnit = "Kw")
            MeterDataLayout(title = "Water", value = viewModel.waterCounter,
                iconId = R.drawable.drop, measureUnit = "М³")
        }
    }
}

@Composable
fun MeterDataLayout(title: String, value: Double, iconId: Int, measureUnit: String) {
    Column (
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "Icon"
            )
            Text(text = title, fontWeight = FontWeight.Medium, fontSize = 20.sp)
        }
        Text(
            text = "%.3f".format(value),
            fontFamily = FontFamily.Monospace,
            fontSize = 27.sp)
        Text(text = measureUnit, fontWeight = FontWeight.Medium, fontSize = 20.sp)
    }
}

@Composable
fun WorkingDevices(viewModel: HomeViewModel) {

    LazyColumn {
        itemsIndexed(
            viewModel.selectedDevices.filter {
                d -> d in viewModel.turnedOnDevices
            }.chunked(3)
        ) { _, li ->
            LazyRow (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                itemsIndexed(
                    items = li,
                    key = {_, device -> device.name}
                ) { _, device ->
                    HomeDeviseLayout(device = device, viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun HomeDeviseLayout(device: Device, viewModel: HomeViewModel) {
    var checkedState by remember { mutableStateOf(device.turnedOn) }

    Card(
        modifier = Modifier
            .padding(top = 20.dp)
            .width(100.dp)
            .height(100.dp),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(width = 1.dp, color = Color.Black)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = device.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 6.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Purple40,
                fontSize = 20.sp
            )
            Switch(
                modifier = Modifier.fillMaxWidth(),
                colors = SwitchDefaults.colors(
                    checkedTrackColor = Purple40,
                    uncheckedTrackColor = PurpleGrey80,
                    uncheckedBorderColor = PurpleGrey80,
                    uncheckedThumbColor = Color.White
                ),
                checked = checkedState,
                onCheckedChange = {
                    checkedState = it
                    viewModel.turnDevice(device, checkedState)
                    viewModel.installTurnedOnDevices()
                })
        }
    }
}
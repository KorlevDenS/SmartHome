package com.example.smarthome.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarthome.R
import com.example.smarthome.model.Device
import com.example.smarthome.model.HomeViewModel
import com.example.smarthome.model.Hygrometer
import com.example.smarthome.model.Lighting
import com.example.smarthome.model.Thermostat
import com.example.smarthome.ui.theme.Purple100
import com.example.smarthome.ui.theme.Purple40
import com.example.smarthome.ui.theme.PurpleGrey80

@Composable
fun DevicesScreen(viewModel: HomeViewModel) {
    ScreenBox {
        Column (
            modifier = Modifier.fillMaxWidth()
        ) {
            TitleText(text = "Devices")
            DevicesData(viewModel = viewModel)
            TitleText(text = "Management")
            Devices(viewModel = viewModel)
        }
    }
}

@Composable
fun DevicesData(viewModel: HomeViewModel) {

    var checkedState by remember { mutableStateOf(
        viewModel.selectedDevices.any { it in viewModel.turnedOnDevices }
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
                ControlData(title = "All in area", iconId = R.drawable.socket) {
                    Switch(
                        checked = viewModel.selectedDevices.any { it in viewModel.turnedOnDevices },
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = Purple40,
                            uncheckedTrackColor = PurpleGrey80,
                            uncheckedBorderColor = PurpleGrey80,
                            uncheckedThumbColor = Color.White
                        ),
                        onCheckedChange = {
                            checkedState = it
                            viewModel.selectedDevices.forEach {
                                    d -> viewModel.turnDevice(d, checkedState)
                            }
                        })
                }
                SensorData(sensorItem = SensorItem(
                    title = "Power / hour",
                    iconId = R.drawable.energy,
                    measureUnit = "w",
                    value = viewModel.turnedOnDevices
                        .filter { it in viewModel.selectedDevices}.sumOf { it.powerInWatts }
                ))
            }
    }
}

@Composable
fun Devices(viewModel: HomeViewModel) {
    LazyColumn(
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp)
    ) {
        itemsIndexed(
            items = viewModel.selectedDevices,
            key = {_, device -> device.name}
        ) { _, device ->
            DeviceLayout(device = device, viewModel = viewModel,
            iconId = R.drawable.energy, value = "${device.powerInWatts}W")
        }
    }
}

@Composable
fun DeviceLayout(device: Device, viewModel: HomeViewModel, value: String, iconId: Int) {

    var checkedState by remember { mutableStateOf(device.turnedOn)}

    Card(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(width = 1.dp, color = Color.Black)
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth(),
                text = device.name,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Purple40,
                fontSize = 25.sp
            )
            Row(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DevicePage(device = device, viewModel = viewModel)
                Row {
                    Icon(
                        painter = painterResource(id = iconId),
                        contentDescription = "Icon",
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = value, fontSize = 30.sp)
                }
                Switch(
                    checked = when (device) {
                        in viewModel.turnedOnDevices -> true
                        else -> false
                    },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = Purple40,
                        uncheckedTrackColor = PurpleGrey80,
                        uncheckedBorderColor = PurpleGrey80,
                        uncheckedThumbColor = Color.White
                    ),
                    onCheckedChange = {
                        checkedState = it
                        viewModel.turnDevice(device, checkedState)
                    }
                )
            }
        }
    }

}

@Composable
fun DevicePage(device: Device, viewModel: HomeViewModel) {

    var showDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    IconButton(onClick ={ showDialog = true }) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_info_outline_50),
            contentDescription = "Icon",
            tint = Purple40
        )
    }

    if (showDialog) {
        AlertDialog(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            containerColor = Purple100,
            onDismissRequest = { showDialog = false },
            text = {
                DeviceAlertContent(device = device)
            },
            confirmButton = {
                Button(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Purple40)
                ) {
                    Text(text = "Close",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Purple100)
                }
            },
            dismissButton = {
                Button(onClick = {
                    showDialog = false
                    showDeleteDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Purple40)) {
                    Text(text = "Delete",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Purple100)
                }

            }
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            containerColor = Purple100,
            onDismissRequest = { showDeleteDialog = false },
            text = { TitleText(text = "Delete device ${device.name} ?") },
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteDialog = false
                        viewModel.deleteDevice(device)
                              },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Purple40)
                ) {
                    Text(text = "Yes",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Purple100)
                }
            },
            dismissButton = {
                Button( onClick = { showDeleteDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Purple40)) {
                    Text(text = "No",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Purple100)
                }

            }
        )
    }
}

@Composable
fun DeviceAlertContent(device: Device)  {
    Column(Modifier.height(300.dp)) {
        TitleText(text = device.name)
        if (device is Hygrometer) {
            DeviceFieldText(text1 = "Humidity:",
                text2 = "${device.humidity} %")
        }
        if (device is Lighting) {
            DeviceFieldText(text1 = "Luminance:",
                text2 = "${device.luminance} lm")
        }
        if (device is Thermostat) {
            DeviceFieldText(text1 = "Temperature:",
                text2 = "${device.temperature} degrees")
        }
        DeviceFieldText(text1 = "Watts per hour:",
            text2 = "${device.powerInWatts}W")
        DeviceFieldText(text1 = "Condition:",
            text2 = when (device.turnedOn) {
                true -> "working" else -> "turned off"
            })
        DeviceFieldText(text1 = "Room:",
            text2 = device.room.name
        )
    }
}

@Composable
fun DeviceFieldText(text1: String, text2: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 5.dp),
            text = text1,
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray
        )
        Text(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 5.dp),
            text = text2,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray
        )
    }
}













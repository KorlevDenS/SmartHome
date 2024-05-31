package com.example.smarthome.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarthome.ui.theme.Purple40

@Composable
fun ScreenBox(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(713.dp)
            .background(Color.White)
    ) {
        content()
    }
}

@Composable
fun TitleText(text: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 5.dp),
        text = text,
        textAlign = TextAlign.Center,
        fontSize = 25.sp,
        fontWeight = FontWeight.Medium,
        color = Purple40
    )
}

@Composable
fun TitleTextWithIcon(text: String, iconId: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = "Icon"
        )
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            fontWeight = FontWeight.Medium,
            color = Purple40
        )
    }
}

@Composable
fun ControlData(title: String, iconId: Int, controlItem: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .width(170.dp)
            .height(130.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(width = 1.dp, color = Color.Black)
    ) {
        Column (
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = title,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = iconId),
                    contentDescription = "Icon",
                )
                controlItem()
            }
        }
    }
}

@Composable
fun SensorData(sensorItem: SensorItem) {
    Card(
        modifier = Modifier
            .width(170.dp)
            .height(130.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(width = 1.dp, color = Color.Black)
    ) {
        Column (
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = sensorItem.title,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = sensorItem.iconId),
                    contentDescription = "Icon",
                )
                Text(
                    text = if (!sensorItem.value.isNaN()) "${sensorItem.value}${sensorItem.measureUnit}"
                    else " --- ${sensorItem.measureUnit}",
                    fontSize = 35.sp)
            }
        }
    }
}

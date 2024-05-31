package com.example.smarthome.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarthome.R
import com.example.smarthome.model.HomeViewModel

@Composable
fun StatsScreen(viewModel: HomeViewModel) {
    ScreenBox {
        Column (
            modifier = Modifier.fillMaxWidth()
        ) {
            TitleTextWithIcon(text = "Energy consumption per month", iconId = R.drawable.energy)
            MeterCardsRow(stats = viewModel.meterStats.filter { it.measureUnit == "Kw" }.reversed())
            TitleTextWithIcon(text = "Water consumption per month", iconId = R.drawable.drop)
            MeterCardsRow(stats = viewModel.meterStats.filter { it.measureUnit == "М³" }.reversed())
        }
    }
}

@Composable
fun MeterCardsRow(stats: List<MeterStatItem>) {
    LazyRow {
        itemsIndexed(
            stats
        ) { _, stat ->
            MeterMonthCard(
                period = "${stat.begin}  -  ${stat.end}",
                value = stat.value,
                measureUnit = stat.measureUnit)
        }
    }
}

@Composable
fun MeterMonthCard(period: String, value: Double, measureUnit: String) {
    Card(
        modifier = Modifier
            .padding(top = 20.dp, start = 10.dp, end = 10.dp)
            .width(350.dp)
            .height(130.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(width = 1.dp, color = Color.Black)
    ) {
        Column( verticalArrangement = Arrangement.SpaceEvenly) {
            Text(text = period, fontSize = 24.sp,
                modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)

                Text(
                    modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
                    text = "${"%.3f".format(value)} $measureUnit",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 27.sp)
        }
    }

}
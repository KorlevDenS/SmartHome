package com.example.smarthome.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.smarthome.model.HomeViewModel
import com.example.smarthome.model.Room
import com.example.smarthome.ui.theme.Purple40
import com.example.smarthome.ui.theme.Purple90

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: HomeViewModel) {

    val navController = rememberNavController()
    Scaffold(
        topBar = {
            AreaSelect(viewModel = viewModel)
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) {
        AppNavGraph(
            navHostController = navController,
            viewModel = viewModel
        )
    }
}

@Composable
fun AreaSelect(viewModel: HomeViewModel) {

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 5.dp, end = 5.dp)
    ) {
        itemsIndexed(
            viewModel.rooms
        ) { _, room ->

            AreaButton(
                areaName = room.name,
                room = room,
                viewModel = viewModel
                )
        }
    }
}

@Composable
fun AreaButton(
    areaName: String,
    room: Room,
    viewModel: HomeViewModel
) {

    var isSelected by remember {
        mutableStateOf(room.isSelected)
    }

    ElevatedButton(
        modifier = Modifier.padding(start = 5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = when (isSelected) {
                true -> Purple40
                else -> Purple90
            },
            contentColor = when (isSelected) {
                true -> Color.White
                else -> Purple40
            },
        ),
        onClick = {
            isSelected = !isSelected
            viewModel.turnRoom(room, !room.isSelected)
            viewModel.selectArea()
        }
    ) {
        Text(
            text = areaName,
            fontSize = 19.sp
        )
    }
}

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











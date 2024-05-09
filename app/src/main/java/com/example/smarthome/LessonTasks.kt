package com.example.smarthome

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarthome.ui.theme.Gray100

@Composable
fun FruitList() {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp)
            .background(Gray100)
    ) {
        itemsIndexed(
            listOf(
                ItemRowModel(R.drawable.img_1, "Lychee", "It is a tropical tree" +
                        " native to South China, Malaysia, and northern Vietnam. The " +
                        "tree has been introduced throughout Southeast Asia and South Asia. " +
                        "Cultivation in China is documented from the 11th century.China " +
                        "is the main producer of lychees, followed by Vietnam, India, other " +
                        "countries in Southeast Asia, other countries in the South Asia, " +
                        "Madagascar, and South Africa. A tall evergreen tree, it bears " +
                        "small fleshy sweet fruits. The outside of the fruit is a pink-red, " +
                        "rough-textured soft shell."),
                ItemRowModel(R.drawable.img_2, "Pomegranate", "test"),
                ItemRowModel(R.drawable.img_3, "Durian", "test"),
                ItemRowModel(R.drawable.img_4, "Kiwi", "test"),
                ItemRowModel(R.drawable.img_6, "Grape", "test"),
                ItemRowModel(R.drawable.img_7, "Lemon", "test"),

                )
        ) {
                _, item ->
            MyRow(item = item)
        }
    }
}

@Composable
fun CircleItem() {
    val counter = remember {
        mutableIntStateOf(0)
    }
    val color = remember {
        mutableStateOf(Color.Blue)
    }

    Box(modifier = Modifier
        .size(100.dp)
        .background(color = color.value, shape = CircleShape)
        .clickable {
            when (++counter.intValue) {
                10 -> color.value = Color.Red
                20 -> color.value = Color.Green
            }
        },
        contentAlignment = Alignment.Center
    ) {
        Text(text = counter.intValue.toString(),
            style = TextStyle(color = Color.White), fontSize = 20.sp)
    }
}

@Composable
fun ListItem(name: String, fruitColor: String) {
    val counter = remember {
        mutableIntStateOf(0)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                counter.intValue++
                Log.d("MyLog", "Counter: ${counter.intValue}")
            },
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(painter = painterResource(id = R.drawable.img_1),
                    contentDescription = "image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(5.dp)
                        .size(64.dp)
                        .clip(CircleShape)
                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(text = name)
                    Text(text = "$fruitColor and count: ${counter.intValue}")
                }
            }
        }
    }
}
package com.danigutiadan.foodreminder.features.dashboard.home.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ContentAlpha
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danigutiadan.foodreminder.features.dashboard.home.ui.components.ActivityItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(buttonListener: () -> Unit) {
    val beaches = listOf(
        "Playa de las Canteras",
        "Playa de Maspalomas",
        "Playa de la Concha",
        "Playa de las Catedrales",
        "Playa de Bolonia",
        "Playa de Zahara de los Atunes",
        "Playa de la Barceloneta",
        "Playa de Benidorm",
        "Playa de la Malvarrosa",
        "Playa de Las Teresitas"
    )

    Scaffold(topBar = {
        Row(
            modifier = Modifier
                .background(Color(0xFFBEDDF1))
                .fillMaxWidth()
                .padding(start = 15.dp, top = 15.dp, end = 15.dp)
                .height(40.dp)
        ) {
            Text(
                text = "Food.Reminder",
                fontFamily = FontFamily.Serif,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .background(Color(0xFFBEDDF1))
                    .fillMaxHeight()
            )

            Spacer(modifier = Modifier.weight(1F))
            Button(onClick = {buttonListener()}) {
                Text(text = "+")
            }
        }
    }, content = { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color(0xFFBEDDF1))
                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )
            ButtonsBar()
            HomeSearchBar()
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(Modifier.background(Color(0xFFBEDDF1))) {
                items(beaches.count()) {
                    ActivityItem(title = beaches[it], index = it)
                    Spacer(
                        modifier = Modifier
                            .height(10.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSearchBar() {
    var input: String by remember { mutableStateOf("") }
    BasicTextField(
        value = input,
        onValueChange = { newText ->
            input = newText
        },
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = Color.Black
        ),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier // margin left and right
                    .fillMaxWidth()
//                    .border(
//                        width = 2.dp,
//                        color = Color(0xFFAAE9E6),
//                        shape = RoundedCornerShape(size = 16.dp)
//                    )
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 12.dp), // inner padding
            ) {
                if (input.isEmpty()) {
                    Text(
                        text = "placeholder",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )
                }
                innerTextField()
            }
        }
    )

}

@Composable
fun ButtonsBar() {
    Row(modifier = Modifier.fillMaxWidth()) {
        TextButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .weight(0.3F)
                .background(Color.Transparent)
                .clip(RoundedCornerShape(0.dp))
        ) {
            Text(text = "Filtrar", style = TextStyle(color = Color.DarkGray))
        }

        Divider(
            modifier = Modifier
                .width(1.dp)
                .height(40.dp)
                .align(Alignment.CenterVertically), color = Color.LightGray
        )

        TextButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .weight(0.3F)
                .background(Color.Transparent)
                .clip(RoundedCornerShape(0.dp))
        ) {
            Text(text = "Ordenar por", style = TextStyle(color = Color.DarkGray))
        }
//        Divider(
//            modifier = Modifier
//                .width(1.dp)
//                .height(40.dp)
//                .align(Alignment.CenterVertically), color = Color.LightGray
//        )
//
//
//        TextButton(
//            onClick = { /*TODO*/ },
//            modifier = Modifier
//                .weight(0.3F)
//                .background(Color.Transparent)
//                .clip(RoundedCornerShape(0.dp)),
//        ) {
//            Text(
//                text = "Buscar", style = TextStyle(color = Color.DarkGray)
//            )
//
//        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen({})
}
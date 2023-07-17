package com.danigutiadan.foodreminder.features.dashboard.home.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.danigutiadan.foodreminder.features.add_food.ui.screens.AddFoodNameTextField
import com.danigutiadan.foodreminder.features.dashboard.home.ui.components.FoodItem
import com.danigutiadan.foodreminder.features.food.data.model.Food
import com.danigutiadan.foodreminder.features.food.data.model.FoodInfo
import com.danigutiadan.foodreminder.features.food_type.domain.models.FoodType
import java.util.Date

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    addFoodButtonListener: () -> Unit,
    foodList: List<FoodInfo>,
    onEditButtonPressed: (FoodInfo) -> Unit,
    onDeleteButtonPressed: (FoodInfo) -> Unit
) {


    Scaffold(modifier = Modifier.background(Color(0xFFECECEC)), topBar =

    {
        TopAppBar(

            title = {
                Text(
                    "Food~Reminder",
                    fontFamily = FontFamily.Serif,
                    style = androidx.compose.material.MaterialTheme.typography.h5,
                    color = Color.White
                )
            },
            actions = {
                IconButton(onClick = { addFoodButtonListener() }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "",
                        tint = Color.White
                    )

                }
            },
            backgroundColor = Color(0xFF8BC34A)
        )
    }
//    {
//        Row(
//            modifier = Modifier
//                .background(Color(0xFFBEDDF1))
//                .fillMaxWidth()
//                .padding(start = 15.dp, top = 15.dp, end = 15.dp)
//                .height(40.dp)
//        ) {
//            Text(
//                text = "Food.Reminder",
//                fontFamily = FontFamily.Serif,
//                style = MaterialTheme.typography.headlineMedium,
//                modifier = Modifier
//                    .background(Color(0xFFBEDDF1))
//                    .fillMaxHeight()
//            )
//
//            Spacer(modifier = Modifier.weight(1F))
//            Button(onClick = {buttonListener()}) {
//                Text(text = "+")
//            }
//        }
//    }
        , content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .background(Color(0xFFECECEC))
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                //.verticalScroll(rememberScrollState())
            ) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(1.dp)
//                        .background(Color.Gray)
//                )
                // ButtonsBar()
                //HomeSearchBar()
                AddFoodNameTextField(
                    input = "",
                    placeHolder = "Busca tus alimentos",
                    isNameError = false,
                    onValueChanged = {},
                )


                Spacer(modifier = Modifier.height(5.dp))
                ButtonsBar()
                Spacer(modifier = Modifier.height(5.dp))

//                LazyColumn(modifier = Modifier.background(Color(0xFFECECEC)), content = {
//                    items(foodList.count(), key = { it }) {
//                        FoodItem(
//                            food = foodList[it],
//                            onEditButtonPressed = onEditButtonPressed,
//                            onDeleteButtonPressed = onDeleteButtonPressed,
//                            modifier = Modifier.animateItemPlacement()
//                        )
//                        Spacer(
//                            modifier = Modifier
//                                .animateItemPlacement()
//                                .height(10.dp)
//                                .fillMaxWidth()
//                        )
//                    }
//
//                })

                if (foodList.isNotEmpty()) {
                    Column(
                        Modifier
                            .background(Color(0xFFECECEC))
                            .verticalScroll(rememberScrollState())
                    ) {
                        foodList.forEach {
                            FoodItem(
                                food = it,
                                onEditButtonPressed = onEditButtonPressed,
                                onDeleteButtonPressed = onDeleteButtonPressed
                            )
                            Spacer(
                                modifier = Modifier
                                    .height(10.dp)
                                    .fillMaxWidth()
                            )
                        }
                    }
                } else {

                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "No tienes ningún alimento en tu lista aún",
                            modifier = Modifier.align(
                                Alignment.Center
                            )
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
    CustomTextField(input = input, "Hola", onValueChanged = { newText -> input = newText })

}

@Composable
fun CustomTextField(
    input: String,
    placeHolder: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
    onValueChanged: (String) -> Unit
) {
    BasicTextField(
        value = input,
        onValueChange = { newText ->
            onValueChanged(newText)
        },
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = Color.Black
        ),
        decorationBox = { innerTextField ->
            Box(
                modifier = modifier // margin left and right
                    .border(
                        width = 2.dp,
                        color = Color(0xFFAAE9E6),
                        shape = RoundedCornerShape(size = 16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 12.dp), // inner padding
            ) {
                if (input.isEmpty()) {
                    Text(
                        text = placeHolder,
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
//        Divider(
//            modifier = Modifier
//                .width(2.dp)
//                .height(40.dp)
//                .align(Alignment.CenterVertically), color = Color.LightGray
//        )
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
                .width(2.dp)
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
//                .width(2.dp)
//                .height(40.dp)
//                .align(Alignment.CenterVertically), color = Color.LightGray
//        )
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
    HomeScreen(
        {}, foodList = listOf(
            FoodInfo(1, Food(1, "Manzana", 3, Date(), 2, 1), FoodType(1, "Fruta")),
            FoodInfo(1, Food(1, "Manzana", 3, Date(), 2, 1), FoodType(1, "Fruta")),
            FoodInfo(1, Food(1, "Manzana", 3, Date(), 2, 1), FoodType(1, "Fruta")),
            FoodInfo(1, Food(1, "Manzana", 3, Date(), 2, 1), FoodType(1, "Fruta")),
            FoodInfo(1, Food(1, "Manzana", 3, Date(), 2, 1), FoodType(1, "Fruta")),
            FoodInfo(1, Food(1, "Manzana", 3, Date(), 2, 1), FoodType(1, "Fruta")),
        ),
        {},
        {}
    )
}
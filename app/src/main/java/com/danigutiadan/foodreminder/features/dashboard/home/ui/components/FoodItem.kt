package com.danigutiadan.foodreminder.features.dashboard.home.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.danigutiadan.foodreminder.R
import com.danigutiadan.foodreminder.features.food.data.model.Food
import com.danigutiadan.foodreminder.features.food.data.model.FoodStatus
import com.danigutiadan.foodreminder.features.food.data.model.FoodWithFoodType
import com.danigutiadan.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.foodreminder.utils.DateUtils.formatDateToString
import com.danigutiadan.foodreminder.utils.ImageUtils
import java.util.Date

@Composable
fun FoodItem(
    food: FoodWithFoodType,
    onEditButtonPressed: (FoodWithFoodType) -> Unit,
    onDeleteButtonPressed: (FoodWithFoodType) -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(
                getItemBackgroundColor(
                    food.food.foodStatus ?: FoodStatus.FRESH
                )
            )
        ) {
            val painter = if (food.food.image != null)
                rememberAsyncImagePainter(food.food.image)
            else
                painterResource(id = R.drawable.add_food_placeholder)
            Image(
                painter = painter, contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(105.dp)
            )
            Column() {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 20.dp),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = food.food.name,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 5.dp),
                        maxLines = 1,
                        color = Color.White
                    )
                    Text(
                        text = "Fecha de caducidad: ${formatDateToString(food.food.expiryDate)}",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    Text(
                        text = "Tipo de alimento: ${food.foodType.name}",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )

                    Text(
                        text = "Cantidad: 3",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )

                }


                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {

                    IconButton(onClick = { onEditButtonPressed(food) }, modifier = Modifier.size(24.dp)) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    IconButton(onClick = { onDeleteButtonPressed(food) }, modifier = Modifier.size(24.dp)) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))
                }


            }


        }
    }
}

private fun getItemBackgroundColor(foodState: FoodStatus): Color {
    return when (foodState) {
        FoodStatus.FRESH -> Color(0xFF8BC34A)
        FoodStatus.ABOUT_TO_EXPIRE -> Color(0xFFfcd465)
        FoodStatus.ALMOST_EXPIRED -> Color(0xFFFF7779)

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = false)
@Composable
fun PreviewActivityItem() {
    FoodItem(
        food = FoodWithFoodType(1, Food(6, "Pollo", 1, Date(), 1, 1), FoodType(1, "Hola")),
        {},
        {})
}


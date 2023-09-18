package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.dashboard.home.ui.components

import android.annotation.SuppressLint
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.R
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.Food
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodInfo
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodStatus
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.DateUtils.formatDateToString
import java.util.Date

@Composable
fun FoodItem(
    food: FoodInfo,
    onEditButtonPressed: (FoodInfo) -> Unit,
    onDeleteButtonPressed: (FoodInfo) -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(190.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(
                getItemBackgroundColor(
                    food.food.foodStatus ?: FoodStatus.FRESH
                )
            )
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(food.food.foodImageUrl)
                    .crossfade(true)
                    .error(R.drawable.add_food_placeholder)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(115.dp)
            )


            Column() {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp),
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
                        text = "${stringResource(id = R.string.expiration_date)}: ${formatDateToString(food.food.expiryDate)}",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    Text(
                        text = "${stringResource(id = R.string.food_type)}: ${food.foodType.name}",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )

                    Text(
                        text = "${stringResource(id = R.string.quantity)}: ${food.food.quantity}",
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
        food = FoodInfo(1, Food(6, "Pollo", 1, Date(), 1, 1), FoodType(1, "Alimentos congelados")),
        {},
        {})
}


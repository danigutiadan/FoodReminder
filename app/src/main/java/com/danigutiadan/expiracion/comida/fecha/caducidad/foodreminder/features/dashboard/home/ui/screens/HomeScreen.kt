package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.dashboard.home.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.R
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.ads.components.BannerAdView
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.FoodUtils.orderedAlphabetic
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.add_food.ui.screens.AddFoodNameTextField
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.add_food.ui.screens.noRippleClickable
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.dashboard.home.ui.components.FoodItem
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.Food
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodInfo
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodOrder
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food.data.model.FoodStatus
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.StringUtils.getFoodOrderFilterName
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.StringUtils.getFoodStatusFilterName
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.StringUtils.getFoodTypeName
import java.util.Date

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    searchFoodInput: String,
    addFoodButtonListener: () -> Unit,
    foodList: List<FoodInfo>,
    onEditButtonPressed: (FoodInfo) -> Unit,
    onDeleteButtonPressed: (FoodInfo) -> Unit,
    onSearchFoodChanged: (String) -> Unit,
    foodStatusSelectedOption: FoodStatus?,
    foodTypeSelectedOption: FoodType?,
    onFoodStatusFilterSelectedOption: (FoodStatus?) -> Unit,
    onFoodTypeFilterSelectedOption: (FoodType?) -> Unit,
    onApplyFilters: () -> Unit,
    foodTypeList: List<FoodType>,
    foodFiltersTextState: String,
    foodOrderList: List<FoodOrder>,
    onFoodOrderSelected: (FoodOrder?) -> Unit,
    foodOrderText: String
) {
    val isFiltersDialogVisible = remember { mutableStateOf(false) }
    val isFoodOrderDropdownVisible = remember { mutableStateOf(false) }

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
    }, content = { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFECECEC))
                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
        ) {
            AddFoodNameTextField(
                input = searchFoodInput,
                placeHolder = stringResource(id = R.string.search_food),
                isNameError = false,
                onValueChanged = { onSearchFoodChanged(it) },
            )


            Spacer(modifier = Modifier.height(5.dp))
            ButtonsBar(
                foodFiltersTextState = foodFiltersTextState,
                filterButtonClicked = { isFiltersDialogVisible.value = true },
                orderByButtonClicked = { isFoodOrderDropdownVisible.value = true },
                isFoodOrderDropdownVisible = isFoodOrderDropdownVisible.value,
                foodOrderList = foodOrderList,
                onFoodOrderDismissRequest = { isFoodOrderDropdownVisible.value = false },
                onFoodOrderSelected = onFoodOrderSelected,
                foodOrderText = foodOrderText
            )
            Spacer(modifier = Modifier.height(5.dp))

            if (foodList.isNotEmpty()) {
                LazyColumn(modifier = Modifier.background(Color(0xFFECECEC)).weight(1f), content = {
                    items(foodList.count(), key = { foodList[it].food.id ?: it }) {
                        FoodItem(
                            food = foodList[it],
                            onEditButtonPressed = onEditButtonPressed,
                            onDeleteButtonPressed = onDeleteButtonPressed,
                            modifier = Modifier.animateItemPlacement()
                        )
                        Spacer(
                            modifier = Modifier
                                .animateItemPlacement()
                                .height(10.dp)
                                .fillMaxWidth()
                        )
                    }

                })
            } else {

                Box(modifier = Modifier.fillMaxWidth().weight(1f)) {
                    Text(
                        text = stringResource(id = R.string.no_food_in_list),
                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )
                }

            }
            BannerAdView()
        }


        if (isFiltersDialogVisible.value)
            FiltersDialog(
                onDismiss = { isFiltersDialogVisible.value = false },
                foodTypeList = foodTypeList.orderedAlphabetic(LocalContext.current),
                foodStatusSelectedOption = foodStatusSelectedOption,
                foodTypeSelectedOption = foodTypeSelectedOption,
                onFoodStatusFilterSelectedOption = onFoodStatusFilterSelectedOption,
                onFoodTypeFilterSelectedOption = onFoodTypeFilterSelectedOption,
                onApplyFilters = {
                    onApplyFilters()
                    isFiltersDialogVisible.value = false
                }
            )
    })
}

@Composable
fun FoodOrderDropdown(
    isExpanded: Boolean,
    onDismissRequest: () -> Unit,
    foodOrderList: List<FoodOrder>,
    onFoodOrderSelected: (FoodOrder?) -> Unit
) {
    val isOrderByDropdownExpanded = remember { mutableStateOf(false) }
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = onDismissRequest
    ) {

        DropdownMenuItem(onClick = {
            onFoodOrderSelected(null)
            isOrderByDropdownExpanded.value = false
        }) {
            Text(text = stringResource(id = R.string.most_recent))
        }
        foodOrderList.forEach {
            DropdownMenuItem(onClick = {
                onFoodOrderSelected(it)
                isOrderByDropdownExpanded.value = false
            }) {
                Text(text = getFoodOrderFilterName(it, LocalContext.current))
            }
        }

    }
}

@Composable
fun ButtonsBar(
    foodFiltersTextState: String,
    filterButtonClicked: () -> Unit,
    orderByButtonClicked: () -> Unit,
    isFoodOrderDropdownVisible: Boolean,
    foodOrderList: List<FoodOrder>,
    onFoodOrderDismissRequest: () -> Unit,
    onFoodOrderSelected: (FoodOrder?) -> Unit,
    foodOrderText: String
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        TextButton(
            onClick = filterButtonClicked,
            modifier = Modifier
                .weight(0.3F)
                .background(Color.Transparent)
                .clip(RoundedCornerShape(0.dp))
        ) {
            Text(
                text = foodFiltersTextState,
                style = TextStyle(color = Color.DarkGray),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }

        Divider(
            modifier = Modifier
                .width(2.dp)
                .height(40.dp)
                .align(Alignment.CenterVertically), color = Color.LightGray
        )

        TextButton(
            onClick = orderByButtonClicked,
            modifier = Modifier
                .weight(0.3F)
                .background(Color.Transparent)
                .clip(RoundedCornerShape(0.dp))
        ) {
            Text(
                text = foodOrderText,
                style = TextStyle(color = Color.DarkGray),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                maxLines = 2
            )
        }
        FoodOrderDropdown(
            isExpanded = isFoodOrderDropdownVisible,
            onDismissRequest = onFoodOrderDismissRequest,
            foodOrderList,
            onFoodOrderSelected
        )
    }
}


@Composable
fun FiltersDialog(
    onDismiss: () -> Unit,
    foodTypeList: List<FoodType>,
    foodStatusSelectedOption: FoodStatus?,
    foodTypeSelectedOption: FoodType?,
    onFoodStatusFilterSelectedOption: (FoodStatus?) -> Unit,
    onFoodTypeFilterSelectedOption: (FoodType?) -> Unit,
    onApplyFilters: () -> Unit
) {
    val foodStatusList =
        listOf(FoodStatus.FRESH, FoodStatus.ABOUT_TO_EXPIRE, FoodStatus.ALMOST_EXPIRED)
    val isFoodStatusDropdownExpanded = remember { mutableStateOf(false) }
    val isFoodTypeDropdownExpanded = remember { mutableStateOf(false) }
    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        containerColor = Color.White,
        onDismissRequest = onDismiss,
        title = { Text(stringResource(id = R.string.filters)) },
        confirmButton = {
            Row() {
                Text(
                    stringResource(id = R.string.clear_filters),
                    color = Color.Black,
                    modifier = Modifier.clickable {
                        onFoodStatusFilterSelectedOption(null)
                        onFoodTypeFilterSelectedOption(null)
                        onApplyFilters()
                    },
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    stringResource(id = R.string.apply),
                    color = Color.Black,
                    modifier = Modifier.clickable {
                        onApplyFilters()
                    },
                    fontSize = 18.sp
                )


            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                //FOOD STATUS
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .noRippleClickable {
                        isFoodStatusDropdownExpanded.value =
                            !isFoodStatusDropdownExpanded.value
                    }) {
                    Text(
                        text = "${stringResource(id = R.string.status)}:",
                        fontSize = 17.sp
                    )

                    DropdownMenu(
                        expanded = isFoodStatusDropdownExpanded.value,
                        onDismissRequest = { isFoodStatusDropdownExpanded.value = false }
                    ) {

                        DropdownMenuItem(onClick = {
                            onFoodStatusFilterSelectedOption(null)
                            isFoodStatusDropdownExpanded.value = false
                        }) {
                            Text(text = stringResource(id = R.string.all))
                        }
                        foodStatusList.forEach {
                            DropdownMenuItem(onClick = {
                                onFoodStatusFilterSelectedOption(it)
                                isFoodStatusDropdownExpanded.value = false
                            }) {
                                Text(text = getFoodStatusFilterName(it, LocalContext.current))
                            }
                        }

                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = getFoodStatusFilterName(
                            foodStatusSelectedOption,
                            LocalContext.current
                        ),
                        fontSize = 17.sp
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))


                //FOOD TYPE
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .noRippleClickable {
                        isFoodTypeDropdownExpanded.value =
                            !isFoodTypeDropdownExpanded.value
                    }) {
                    Text(
                        text = "${stringResource(id = R.string.food_type)}:",
                        fontSize = 17.sp
                    )

                    DropdownMenu(
                        expanded = isFoodTypeDropdownExpanded.value,
                        onDismissRequest = { isFoodTypeDropdownExpanded.value = false }
                    ) {

                        DropdownMenuItem(onClick = {
                            onFoodTypeFilterSelectedOption(null)
                            isFoodTypeDropdownExpanded.value = false
                        }) {
                            Text(text = stringResource(id = R.string.all))
                        }
                        foodTypeList.forEach {
                            DropdownMenuItem(onClick = {

                                onFoodTypeFilterSelectedOption(it)
                                isFoodTypeDropdownExpanded.value = false
                            }) {
                                Text(text = it.foodTypeName ?: "")
                            }
                        }

                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = foodTypeSelectedOption?.foodTypeNameResource ?: stringResource(id = R.string.all),
                        fontSize = 17.sp
                    )
                }
            }
        }
    )

}


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(
        "",
        {},
        foodList = listOf(
            FoodInfo(1, Food(1, "Manzana", 3, Date(), 2, 1), FoodType(1, "Fruta")),
            FoodInfo(1, Food(1, "Manzana", 3, Date(), 2, 1), FoodType(1, "Fruta")),
            FoodInfo(1, Food(1, "Manzana", 3, Date(), 2, 1), FoodType(1, "Fruta")),
            FoodInfo(1, Food(1, "Manzana", 3, Date(), 2, 1), FoodType(1, "Fruta")),
            FoodInfo(1, Food(1, "Manzana", 3, Date(), 2, 1), FoodType(1, "Fruta")),
            FoodInfo(1, Food(1, "Manzana", 3, Date(), 2, 1), FoodType(1, "Fruta")),
        ),
        {},
        {},
        {},
        null,
        null,
        {},
        {},
        {},
        listOf(),
        "",
        listOf(),
        {},
        "Ordenar por"
    )
}

//@Preview(showBackground = true)
@Composable
fun PreviewFiltersDialog() {
    Box(modifier = Modifier.fillMaxSize()) {
        FiltersDialog({}, listOf(), null, null, {}, {}, {})
    }
}



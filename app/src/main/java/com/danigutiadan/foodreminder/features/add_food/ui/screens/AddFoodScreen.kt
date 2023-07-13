package com.danigutiadan.foodreminder.features.add_food.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.danigutiadan.foodreminder.R
import com.danigutiadan.foodreminder.features.add_food.ui.components.CustomDatePickerDialog
import com.danigutiadan.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.foodreminder.features.onboarding.adduserinfo.ui.screens.AddProfileImageButtonSheetDialog
import com.danigutiadan.foodreminder.utils.DateUtils.formatDateToString
import com.danigutiadan.foodreminder.utils.Response
import com.dokar.sheets.BottomSheetLayout
import com.dokar.sheets.BottomSheetState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.Date

@Composable
fun AddFoodScreen(
    backClickListener: () -> Unit,
    barcodeClickListener: () -> Unit,
    saveFoodListener: () -> Unit,
    onExpiryDateSelected: (Date) -> Unit,
    foodTypeListState: Response<List<FoodType>>? = null,
    foodBitmap: Bitmap?,
    bottomSheetPictureDialogState: BottomSheetState,
    closePictureDialog: Boolean,
    doCloseDialog: () -> Unit,
    onTakePicture: () -> Unit,
    onGetExistentPicture: () -> Unit,
    foodQuantity: String,
    foodName: String,
    onFoodTypeSelected: (FoodType) -> Unit,
    onFoodQuantityChanged: (String) -> Unit,
    onFoodNameChanged: (String) -> Unit,
    dateSelected: Date?,
    daysBeforeExpiration: String,
    onDaysBeforeExpiration: (String) -> Unit,
    onQuantityPlusPressed: () -> Unit,
    onQuantityMinusPressed: () -> Unit,
    onDaysBeforeExpirationPlusPressed: () -> Unit,
    onDaysBeforeExpirationMinusPressed: () -> Unit,
    imageUrl: String,
    isNameError: Boolean,
    isQuantityError: Boolean,
    isFoodTypeError: Boolean,
    isExpiryDateError: Boolean,
    isDaysBeforeExpirationError: Boolean,
) {
    val scope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }
    Scaffold(topBar = {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { backClickListener() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            },
            title = {
                Text("Añade tu alimento", style = MaterialTheme.typography.h6, color = Color.White)
            },
            backgroundColor = Color(0xFF8BC34A),
            actions = {
                IconButton(onClick = { barcodeClickListener() }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "",
                        tint = Color.White
                    )

                }
                IconButton(onClick = { saveFoodListener() }) {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "",
                        tint = Color.White
                    )

                }
            }
        )
    }) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color(0xFFECECEC))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column() {
                        Box(
                            modifier = Modifier
                                .height(150.dp)
                                .width(130.dp)
                        ) {

                            Box(
                                modifier = Modifier
                                    .height(190.dp)
                                    .width(110.dp)
                                    .align(Alignment.Center),

                                ) {
                                //Image
                                FoodImage(
                                    foodBitmap,
                                    { scope.launch { bottomSheetPictureDialogState.expand() } },
                                    imageUrl
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .height(30.dp)
                                    .width(30.dp)
                                    .align(Alignment.BottomEnd),
                                contentAlignment = Alignment.BottomEnd
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_camera_rounded),
                                    contentDescription = ""
                                )
                            }

                        }

                    }


                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "¿Cuál es el nombre de tu producto?")
                Spacer(modifier = Modifier.height(5.dp))
                AddFoodNameTextField(
                    input = foodName,
                    placeHolder = "Nombre del producto",
                    isNameError = isNameError,
                    onValueChanged = {
                        onFoodNameChanged(it)
                    })

                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "¿Cuántas unidades, paquetes, bricks... tienes?")
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    AddFoodQuantityTextField(
                        input = foodQuantity,
                        textAlign = TextAlign.Start,
                        isQuantityError = isQuantityError,
                        onValueChanged = {
                            onFoodQuantityChanged(it)
                        })
                    Spacer(modifier = Modifier.width(20.dp))
                    IconButton(
                        onClick = { onQuantityMinusPressed() }, modifier = Modifier
                            .height(20.dp)
                            .width(20.dp)
                    ) {
                        Icon(painter = painterResource(id = R.drawable.ic_minus), "")
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    IconButton(
                        onClick = { onQuantityPlusPressed() }, modifier = Modifier
                            .height(20.dp)
                            .width(20.dp)
                    ) {
                        Icon(painter = painterResource(id = R.drawable.ic_plus), "")
                    }

                }
                Spacer(modifier = Modifier.height(12.dp))

                when (foodTypeListState) {
                    is Response.Success -> {
                        FoodTypeDropdownMenu(
                            foodTypeList = foodTypeListState.data,
                            onFoodTypeSelected = onFoodTypeSelected,
                            isFoodTypeError
                        )
                    }

                    else -> {}
                }

                val showDatePicker = remember { mutableStateOf(false) }


                Text(text = "Introduce la fecha de caducidad de tu producto")
                Spacer(modifier = Modifier.height(5.dp))

                ExpiryDateText(showDatePicker, dateSelected, isExpiryDateError)
                Spacer(modifier = Modifier.height(12.dp))

                if (showDatePicker.value) {
                    CustomDatePickerDialog(
                        label = "Fecha de caducidad",
                        onDateSelected = { date ->
                            onExpiryDateSelected(date)
                        },
                        onDismissRequest = {
                            showDatePicker.value = false
                        })
                }

                Text(text = "¿Cuántos días antes de la fecha de caducidad deseas recibir una notificación?")
                Spacer(modifier = Modifier.height(5.dp))
                AddTimeLeftTextField(
                    input = daysBeforeExpiration,
                    onValueChanged = { onDaysBeforeExpiration(it) },
                    onDaysBeforeExpirationPlusPressed = { onDaysBeforeExpirationPlusPressed() },
                    onDaysBeforeExpirationMinusPressed = { onDaysBeforeExpirationMinusPressed() },
                    isDaysBeforeExpirationError = isDaysBeforeExpirationError
                )

            }
        }

    }

    if (bottomSheetPictureDialogState.visible) BottomSheetLayout(backgroundColor = Color(0xFFECECEC),
        state = bottomSheetPictureDialogState,
        content = {

            Column(Modifier.background(Color(0xFFECECEC))) {
                AddProfileImageButtonSheetDialog(
                    onClick = { onTakePicture() },
                    text = "Tomar foto",
                    interactionSource = interactionSource
                )

                Spacer(modifier = Modifier.height(20.dp))
                AddProfileImageButtonSheetDialog(
                    onClick = { onGetExistentPicture() },
                    text = "Elegir foto existente",
                    interactionSource = interactionSource
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        })

    if (closePictureDialog)
        LaunchedEffect(Unit) {
            coroutineScope {
                launch {
                    bottomSheetPictureDialogState.collapse()
                    doCloseDialog()
                }
            }
        }

}

@Composable
private fun ExpiryDateText(
    showDatePicker: MutableState<Boolean>,
    dateSelected: Date?,
    isExpiryDateError: Boolean
) {
    Text(
        modifier = Modifier
            .border(
                width = 1.5.dp,
                color = if (isExpiryDateError) Color(0xFFFF7779) else Color.White,
                shape = RoundedCornerShape(size = 12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .fillMaxWidth()
            .padding(start = 12.dp, end = 30.dp, top = 10.dp, bottom = 10.dp)
            .noRippleClickable { showDatePicker.value = true },
        text = if (formatDateToString(dateSelected).isNullOrBlank()) "Fecha de caducidad" else formatDateToString(
            dateSelected
        )!!,
        fontSize = 18.sp,
        color = if (formatDateToString(dateSelected).isNullOrBlank()) Color.Gray else Color.Black
    )
}

@Composable
fun FoodImage(imageBitmap: Bitmap?, onClickImage: () -> Unit, imageUrl: String) {
    val painter = if (imageUrl.isNotBlank())
        rememberAsyncImagePainter(imageUrl)
    else if (imageBitmap != null)
        rememberAsyncImagePainter(imageBitmap)
    else painterResource(R.drawable.add_food_placeholder)

    Image(painter = painter,
        contentDescription = "avatar",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .height(160.dp)
            .width(110.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onClickImage()
            }

    )
}


fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

@Composable
fun FoodTypeDropdownMenu(
    foodTypeList: List<FoodType>,
    onFoodTypeSelected: (FoodType) -> Unit,
    isFoodTypeError: Boolean
) {
    val dropdownExpanded = remember { mutableStateOf(false) }
    val selectedOption: MutableState<FoodType?> = remember { mutableStateOf(null) }

    Text(text = "¿Qué tipo de alimento es?")
    Spacer(modifier = Modifier.height(5.dp))
    Column {
        Text(
            text = if (selectedOption.value?.name.isNullOrBlank()) "Tipo de alimento" else "${selectedOption.value?.name}",
            fontSize = 18.sp,
            color = if (selectedOption.value?.name.isNullOrBlank()) Color.Gray else Color.Black,
            modifier = Modifier
                .border(
                    width = 1.5.dp,
                    color = if (isFoodTypeError) Color(0xFFFF7779) else Color.White,
                    shape = RoundedCornerShape(size = 12.dp)
                )
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .fillMaxWidth()
                .padding(start = 12.dp, end = 30.dp, top = 10.dp, bottom = 10.dp)
                .noRippleClickable { dropdownExpanded.value = !dropdownExpanded.value }
        )
        DropdownMenu(
            expanded = dropdownExpanded.value,
            onDismissRequest = { dropdownExpanded.value = false }
        ) {
            foodTypeList.forEach {
                DropdownMenuItem(onClick = {
                    onFoodTypeSelected(it)
                    selectedOption.value = it
                    dropdownExpanded.value = false
                }) {
                    Text(text = it.name)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun AddFoodNameTextField(
    input: String,
    textAlign: TextAlign? = null,
    placeHolder: String,
    onValueChanged: (String) -> Unit,
    isNameError: Boolean
) {
    BasicTextField(
        value = input,
        singleLine = true,
        onValueChange = { newText ->
            onValueChanged(newText)
        },
        textStyle = TextStyle(
            fontSize = 18.sp,
            textAlign = textAlign ?: TextAlign.Left,
            color = Color.Black
        ),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth() // margin left and right
                    .border(
                        width = 1.5.dp,
                        color = if (isNameError) Color(0xFFFF7779) else Color.White,
                        shape = RoundedCornerShape(size = 12.dp)
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(horizontal = 12.dp, vertical = 10.dp)
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
fun AddFoodQuantityTextField(
    input: String,
    textAlign: TextAlign? = null,
    onValueChanged: (String) -> Unit,
    isQuantityError: Boolean
) {

    Row() {
        Box(
            modifier = Modifier // margin left and right

                .border(
                    width = 1.5.dp,
                    color = if (isQuantityError) Color(0xFFFF7779) else Color.White,
                    shape = RoundedCornerShape(size = 12.dp)
                )
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 10.dp)
                .width(120.dp)
        ) {
            Row() {
                BasicTextField(
                    modifier = Modifier.width(IntrinsicSize.Min),
                    value = input,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { newText ->
                        onValueChanged(newText)
                    },
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        textAlign = textAlign ?: TextAlign.Left,
                        color = Color.Black
                    )
                )
                val text: String = if (input.isNotBlank()) {
                    if (input.toInt() == 1) " unidad" else " unidades"
                } else
                    " unidades"

                Text(text = text, fontSize = 18.sp)
            }
        }
    }

}

@Composable
fun AddTimeLeftTextField(
    input: String,
    textAlign: TextAlign? = null,
    onValueChanged: (String) -> Unit,
    onDaysBeforeExpirationPlusPressed: () -> Unit,
    onDaysBeforeExpirationMinusPressed: () -> Unit,
    isDaysBeforeExpirationError: Boolean
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier // margin left and right
                .border(
                    width = 1.5.dp,
                    color = if (isDaysBeforeExpirationError) Color(0xFFFF7779) else Color.White,
                    shape = RoundedCornerShape(size = 12.dp)
                )
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 10.dp)
                .width(120.dp)
        ) {
            Row() {
                BasicTextField(
                    modifier = Modifier.width(IntrinsicSize.Min),
                    value = input,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { newText ->
                        onValueChanged(newText)
                    },
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        textAlign = textAlign ?: TextAlign.Left,
                        color = Color.Black
                    )
                )
                val text: String = if (input.isNotBlank()) {
                    if (input.toInt() == 1) " día antes" else " días antes"
                } else
                    " días antes"

                Text(text = text, fontSize = 18.sp)


            }
        }

        Spacer(modifier = Modifier.width(20.dp))
        IconButton(
            onClick = { onDaysBeforeExpirationMinusPressed() }, modifier = Modifier
                .height(20.dp)
                .width(20.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_minus), "")
        }
        Spacer(modifier = Modifier.width(20.dp))
        IconButton(
            onClick = { onDaysBeforeExpirationPlusPressed() }, modifier = Modifier
                .height(20.dp)
                .width(20.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_plus), "")
        }
    }

}

@Preview
@Composable
fun PreviewAddFoodScreen() {
    AddFoodScreen(
        {},
        {},
        {},
        {},
        Response.Success(listOf()),
        null,
        BottomSheetState(),
        true,
        {},
        {},
        {},
        "0",
        "",
        {},
        {},
        {},
        Date(),
        "0",
        {},
        {},
        {},
        {},
        {},
        "",
        true,
        true,
        true,
        true,
        true,
    )
}
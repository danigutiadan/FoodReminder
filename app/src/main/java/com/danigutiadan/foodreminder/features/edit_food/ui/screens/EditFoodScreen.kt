package com.danigutiadan.foodreminder.features.edit_food.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danigutiadan.foodreminder.R
import com.danigutiadan.foodreminder.features.add_food.ui.components.CustomDatePickerDialog
import com.danigutiadan.foodreminder.features.add_food.ui.screens.AddFoodNameTextField
import com.danigutiadan.foodreminder.features.add_food.ui.screens.AddFoodQuantityTextField
import com.danigutiadan.foodreminder.features.add_food.ui.screens.AddTimeLeftTextField
import com.danigutiadan.foodreminder.features.add_food.ui.screens.ExpiryDateText
import com.danigutiadan.foodreminder.features.add_food.ui.screens.FoodImage
import com.danigutiadan.foodreminder.features.add_food.ui.screens.FoodTypeDropdownMenu
import com.danigutiadan.foodreminder.features.food.data.model.Food
import com.danigutiadan.foodreminder.features.food_type.domain.models.FoodType
import com.danigutiadan.foodreminder.features.onboarding.adduserinfo.ui.screens.AddProfileImageButtonSheetDialog
import com.danigutiadan.foodreminder.utils.Response
import com.dokar.sheets.BottomSheetLayout
import com.dokar.sheets.BottomSheetState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.Date

@Composable
fun EditFoodScreen(
    backClickListener: () -> Unit,
    barcodeClickListener: () -> Unit,
    saveFoodListener: () -> Unit,
    onExpiryDateSelected: (Date) -> Unit,
    foodTypeListState: Response<List<FoodType>>? = null,
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
    remoteImageUrl: String,
    isNameError: Boolean,
    isQuantityError: Boolean,
    isFoodTypeError: Boolean,
    isExpiryDateError: Boolean,
    isDaysBeforeExpirationError: Boolean,
    selectedFoodType: FoodType?
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
                Text(stringResource(id = R.string.edit_food), style = MaterialTheme.typography.h6, color = Color.White)
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
                                    { scope.launch { bottomSheetPictureDialogState.expand() } },
                                    remoteImageUrl
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
                Text(text = stringResource(id = R.string.product_name_prompt))
                Spacer(modifier = Modifier.height(5.dp))
                AddFoodNameTextField(
                    input = foodName,
                    placeHolder = stringResource(id = R.string.product_name),
                    isNameError = isNameError,
                    onValueChanged = {
                        onFoodNameChanged(it)
                    })

                Spacer(modifier = Modifier.height(12.dp))
                Text(text = stringResource(id = R.string.notification_days_prompt))
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
                            isFoodTypeError,
                            selectedFoodType = selectedFoodType
                        )
                    }

                    else -> {}
                }

                val showDatePicker = remember { mutableStateOf(false) }


                Text(text = stringResource(id = R.string.expiration_date_prompt))
                Spacer(modifier = Modifier.height(5.dp))

                ExpiryDateText(showDatePicker, dateSelected, isExpiryDateError)
                Spacer(modifier = Modifier.height(12.dp))

                if (showDatePicker.value) {
                    CustomDatePickerDialog(
                        label = stringResource(id = R.string.expiration_date),
                        onDateSelected = { date ->
                            onExpiryDateSelected(date)
                        },
                        onDismissRequest = {
                            showDatePicker.value = false
                        })
                }

                Text(text = stringResource(id = R.string.notification_days_prompt))
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
                    text = stringResource(id = R.string.take_photo),
                    interactionSource = interactionSource
                )

                Spacer(modifier = Modifier.height(20.dp))
                AddProfileImageButtonSheetDialog(
                    onClick = { onGetExistentPicture() },
                    text = stringResource(id = R.string.choose_existing_photo),
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



@Preview
@Composable
fun PreviewAddFoodScreen() {
    EditFoodScreen(
        {},
        {},
        {},
        {},
        Response.Success(listOf()),
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
        false,
        FoodType(1, "Legumbres")
    )
}
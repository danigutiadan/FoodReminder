package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.adduserinfo.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.R
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signup.ui.components.TermsConditions
import com.dokar.sheets.BottomSheetLayout
import com.dokar.sheets.BottomSheetState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*

@Composable
fun AddUserInfoScreen(
    bottomSheetPictureDialogState: BottomSheetState,
    closePictureDialog: Boolean,
    doCloseDialog: () -> Unit,
    name: String,
    lastName: String,
    profileBitmap: Bitmap?,
    birth: Date?,
    termsChecked: Boolean,
    addUserInfoButtonEnabled: Boolean,
    onNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onBirthChanged: (Date) -> Unit,
    onTermsChecked: (Boolean) -> Unit,
    addUserInfoClick: () -> Unit,
    onTakePicture: () -> Unit,
    onGetExistentPicture: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Let's Get Started!", fontSize = 35.sp)
            Spacer(modifier = Modifier.height(50.dp))
            UserImage(profileBitmap) { scope.launch { bottomSheetPictureDialogState.expand() } }
            Spacer(modifier = Modifier.height(20.dp))
            SignUpTextField(text = name, placeHolder = "Nombre") {
                onNameChanged(it)
            }
            Spacer(modifier = Modifier.height(20.dp))
            SignUpTextField(text = lastName, placeHolder = "Apellidos") {
                onLastNameChanged(it)
            }

            Spacer(modifier = Modifier.height(20.dp))

            SignUpDateTextField(
                text = birth,
                placeHolder = "Introduce tu fecha de nacimiento",
                onValueChanged = {
                    //Nada porque el TextField está deshabilitado, se comporta como un botón, pero lo tenemos así para
                    //Conseguir el mismo estilo que los demás TextField
                }
            ) { dateSelected ->
                onBirthChanged(dateSelected)
            }

            Spacer(modifier = Modifier.height(20.dp))

            TermsConditions(onTermsChecked = { onTermsChecked(it) }, termsChecked = termsChecked)

            Spacer(modifier = Modifier.height(20.dp))

            SubmitAddUserInfoButton(
                isEnabled = addUserInfoButtonEnabled,
                onSubmitButtonClick = { addUserInfoClick() })

        }

        if (bottomSheetPictureDialogState.visible) BottomSheetLayout(state = bottomSheetPictureDialogState,
            content = {

                Column {
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
}

@Composable
fun SubmitAddUserInfoButton(onSubmitButtonClick: () -> Unit, isEnabled: Boolean) {
    Button(onClick = { onSubmitButtonClick() }, enabled = isEnabled) {
        Text(text = "Guardar")

    }
}

@Composable
fun AddProfileImageButtonSheetDialog(
    onClick: () -> Unit, text: String, interactionSource: MutableInteractionSource
) {
    Box(modifier = Modifier
        .padding(horizontal = 10.dp)
        .fillMaxWidth()
        .clickable(interactionSource = interactionSource, indication = null) {
            onClick()
        }) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6,
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White)
                .fillMaxWidth()
                .padding(10.dp)

        )
    }


}
@Preview
@Composable
fun Preview() {
    AddProfileImageButtonSheetDialog(onClick = {  }, text = "Tomar foto", interactionSource = MutableInteractionSource())
}

@Composable
fun UserImage(imageBitmap: Bitmap?, onClickImage: () -> Unit) {
    val painter = if (imageBitmap != null)
        rememberAsyncImagePainter(imageBitmap)
    else painterResource(R.drawable.user)

    Image(painter = painter,
        contentDescription = "avatar",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .clickable {
                onClickImage()
            }

    )
}


@Composable
fun SignUpTextField(text: String, placeHolder: String, onValueChanged: (String) -> Unit) {

    var isFocused: Boolean by remember { mutableStateOf(false) }
    val border =
        if (isFocused) BorderStroke(1.dp, Color.Blue) else BorderStroke(1.dp, Color.LightGray)
    TextField(modifier = Modifier
        .fillMaxWidth()
        .focusRequester(FocusRequester())
        .onFocusChanged {
            isFocused = it.isFocused
        }
        .border(border, RoundedCornerShape(30.dp)),
        value = text,
        onValueChange = { onValueChanged(it) },
        placeholder = { Text(text = placeHolder) },
        shape = RoundedCornerShape(30.dp),
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            backgroundColor = Color.White
        )
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpDateTextField(
    text: Date?,
    placeHolder: String,
    onValueChanged: (String) -> Unit,
    onDateSelected: (Date) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    var isFocused: Boolean by remember { mutableStateOf(false) }
    val calendarState = rememberUseCaseState()
    CalendarDialog(config = CalendarConfig(
        monthSelection = true, yearSelection = true, locale = Locale("es", "ES")
    ), state = calendarState, selection = CalendarSelection.Date { date ->
        onDateSelected(getDateSelected(date))
    })
    val border =
        if (isFocused) BorderStroke(1.dp, Color.Blue) else BorderStroke(1.dp, Color.LightGray)
    TextField(modifier = Modifier
        .fillMaxWidth()
        .focusRequester(FocusRequester())
        .clickable(interactionSource = interactionSource, indication = null) {
            calendarState.show()
        }
        .onFocusChanged {
            isFocused = it.isFocused
        }
        .border(border, RoundedCornerShape(30.dp)),
        value = if (text == null) "" else "Fecha de nacimiento: ${formattedDate(text)}",
        enabled = false,
        onValueChange = { onValueChanged(it) },
        placeholder = { Text(text = placeHolder) },
        shape = RoundedCornerShape(30.dp),
        colors = TextFieldDefaults.textFieldColors(
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            disabledTextColor = Color.Black,
            backgroundColor = Color.White
        )
    )

}

fun formattedDate(date: Date?): String {
    return if (date != null) {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))
        val month = String.format("%02d", calendar.get(Calendar.MONTH) + 1)
        val year = calendar.get(Calendar.YEAR)
        "$day/$month/$year"
    } else {
        ""
    }
}

fun getDateSelected(date: LocalDate): Date {
    val calendar = Calendar.getInstance()
    calendar.set(date.year, date.monthValue - 1, date.dayOfMonth, 0, 0, 0)
    return calendar.time
}

@Composable
@Preview
fun PreviewAddUserInfoScreen() {
    AddUserInfoScreen(
        bottomSheetPictureDialogState = BottomSheetState(),
        closePictureDialog = false,
        doCloseDialog = { /*TODO*/ },
        name = "",
        lastName = "",
        profileBitmap = null,
        birth = Date(),
        termsChecked = false,
        addUserInfoButtonEnabled = false,
        onNameChanged = {},
        onLastNameChanged = {},
        onBirthChanged = {},
        onTermsChecked = {},
        addUserInfoClick = { },
        onTakePicture = {}) {

    }

}


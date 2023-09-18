package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.ui.OnboardingViewModel
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

@Composable
fun EmailTextField(
    email: String,
    viewModel: OnboardingViewModel,
    password: String
) {
    TextField(
        value = email,
        onValueChange = { viewModel.onLoginChanged(it, password) },
        placeholder = {
            Text(
                text = "Email"
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}

@Composable
fun SecondEmailTextField(
    email: String,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = email,
        onValueChange = { onValueChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = { /* Acción al pulsar el botón Siguiente */ }),
        placeholder = { Text(text = "example@gmail.com") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = MaterialTheme.colors.secondary,
            focusedBorderColor = MaterialTheme.colors.secondary,
            unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
        )
    )
}

@Composable
fun SecondPasswordTextField(
    password: String,
    placeholder: String,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = password,
        onValueChange = { onValueChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = { /* Acción al pulsar el botón Siguiente */ }),
        visualTransformation = PasswordVisualTransformation(),
        placeholder = { Text(text = placeholder) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = MaterialTheme.colors.secondary,
            focusedBorderColor = MaterialTheme.colors.secondary,
            unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
        )
    )
}

@Composable
fun PasswordTextField(
    password: String,
    viewModel: OnboardingViewModel,
    email: String
) {
    TextField(
        value = password,
        onValueChange = { viewModel.onLoginChanged(email, it) },
        placeholder = {
            Text(
                text = "Password"
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
fun LoginButton(
    isButtonEnabled: Boolean,
    text: String,
    onButtonClick: () -> Unit
) {
    Button(
        onClick = { onButtonClick() },
        enabled = isButtonEnabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFFF0000),
            disabledBackgroundColor = Color(0xFFFF4E4E),
            contentColor = Color.White,
            disabledContentColor = Color.White
        )
    ) {
        Text(text = text)
    }
}

@Composable
fun RegisterText(goToSignUp: () -> Unit) {
    Text(text = "¿Aún no te estás registrado? Regístrate aquí", modifier = Modifier.clickable {
        goToSignUp()
    })
}

@Composable
fun AddUserText(goToAddUserInfo: () -> Unit) {
    Text(text = "User Info", modifier = Modifier.clickable {
        goToAddUserInfo()
    })
}

@Composable
fun LoginTextError(message: String) {

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        text = message,
        style = TextStyle(color = Color.Red)
    )
    
}

@Composable
fun LoginError(response: Response<FirebaseUser>) {
    when ((response as Response.Error).exception) {
        is FirebaseAuthInvalidUserException -> {
            LoginTextError(message = "El usuario no existe")
        }
        is FirebaseAuthInvalidCredentialsException -> {
            LoginTextError(message = "Las credenciales no son correctas")

        }
        else -> {}
    }
}

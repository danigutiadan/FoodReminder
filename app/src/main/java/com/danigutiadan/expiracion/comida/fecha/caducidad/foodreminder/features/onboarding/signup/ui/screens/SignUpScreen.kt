package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signup.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.ui.components.LoginButton
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.ui.components.SecondEmailTextField
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.ui.components.SecondPasswordTextField
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signup.ui.components.TermsConditions

@Composable
fun SignUpScreen(
    email: String,
    password: String,
    repeatPassword: String,
    termsChecked: Boolean,
    isButtonEnabled: Boolean,
    onSignupChanged: (String, String, String, Boolean) -> Unit,
    onTermsChecked: (Boolean) -> Unit,
    onLoginButtonClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(Color(0xFFAF6060))
    ) {
        SignUpContent(
            email = email,
            password = password,
            repeatPassword = repeatPassword,
            termsChecked = termsChecked,
            isButtonEnabled = isButtonEnabled,
            onSignupChanged = onSignupChanged,
            onTermsChecked = onTermsChecked,
            onLoginButtonClicked = onLoginButtonClicked
        )
    }
}


@Composable
fun SignUpContent(
    email: String,
    password: String,
    repeatPassword: String,
    termsChecked: Boolean,
    isButtonEnabled: Boolean,
    onSignupChanged: (String, String, String, Boolean) -> Unit,
    onTermsChecked: (Boolean) -> Unit,
    onLoginButtonClicked: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            SecondEmailTextField(email = email) {
                //verificar datos en el VM
                onSignupChanged(it, password, repeatPassword, termsChecked)
            }
            Spacer(modifier = Modifier.height(20.dp))
            SecondPasswordTextField(password = password, placeholder = "Contraseña") {
                //verificar datos en el VM
                onSignupChanged(email, it, repeatPassword, termsChecked)
            }
            Spacer(modifier = Modifier.height(20.dp))
            SecondPasswordTextField(
                password = repeatPassword,
                placeholder = "Repite tu contraseña"
            ) {
                //verificar datos en el VM
                onSignupChanged(email, password, it, termsChecked)
            }

            TermsConditions({

                onTermsChecked(it)
            }, termsChecked)

            LoginButton(isButtonEnabled = isButtonEnabled, text = "Registrarse") {
                onLoginButtonClicked()
            }

        }

    }
}

@Composable
@Preview
fun PreviewSignUp() {
    SignUpScreen(
        email = "",
        password = "",
        repeatPassword = "",
        termsChecked = false,
        isButtonEnabled = false,
        onSignupChanged = { _, _, _, _ -> },
        onTermsChecked = {}
    ) {

    }
}

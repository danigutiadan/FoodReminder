package com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.R
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.ui.components.AddUserText
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.ui.components.LoginButton
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.ui.components.LoginError
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.ui.components.RegisterText
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.ui.components.SecondEmailTextField
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.features.onboarding.signin.ui.components.SecondPasswordTextField
import com.danigutiadan.expiracion.comida.fecha.caducidad.foodreminder.utils.Response
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser


@Composable
fun SignInScreen(
    email: String,
    password: String,
    isButtonEnabled: Boolean,
    signInState: Response<FirebaseUser>,
    onLoginChanged: (String, String) -> Unit,
    resetValues: () -> Unit,
    goToSignUp: () -> Unit, goToAddUserInfo: () -> Unit,
    signInWithEmail: () -> Unit
) {
    Scaffold(content = {
        SignInContent(
            it,
            email,
            password,
            isButtonEnabled,
            signInState,
            onLoginChanged,
            resetValues,
            goToSignUp,
            goToAddUserInfo,
            signInWithEmail
        )
    })


}

@Composable
fun Toolbar() {
    TopAppBar(title = { Text(text = "hola") })
}

@Composable
fun SignInContent(
    padding: PaddingValues,
    email: String,
    password: String,
    isButtonEnabled: Boolean,
    signInState: Response<FirebaseUser>,
    onLoginChanged: (String, String) -> Unit,
    resetValues: () -> Unit,
    goToSignUp: (() -> Unit),
    goToAddUserInfo: (() -> Unit),
    signInWithEmail: () -> Unit
) {
     Box(
        contentAlignment = Alignment.TopCenter, modifier = Modifier
             .fillMaxSize()
             .padding(padding)
             .background(Color(0xA400BCD4))
    )
    {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo), contentDescription = "logo",
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            SecondEmailTextField(email) {
                onLoginChanged(it, password)
            }
            Spacer(modifier = Modifier.height(10.dp))
            SecondPasswordTextField(password, "Password") {
               onLoginChanged(email, it)
            }
            if (signInState is Response.Error)
                LoginError(response = signInState)
            LoginButton(isButtonEnabled, "Iniciar sesion") { signInWithEmail() }
            RegisterText {
                goToSignUp()
               resetValues()

            }

            AddUserText {
                goToAddUserInfo()
            }

        }
    }
}

@Composable
@Preview
fun PreviewSignIn() {
    SignInScreen(
        email = "",
        password = "",
        isButtonEnabled = false,
        signInState = Response.Error(FirebaseAuthInvalidCredentialsException("text", "holas")),
        onLoginChanged = {_, _ ->},
        resetValues = {},
        goToSignUp = {},
        goToAddUserInfo = {}) {

    }
}








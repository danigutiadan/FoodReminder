package com.danigutiadan.foodreminder.features.onboarding.signin.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danigutiadan.foodreminder.R
import com.danigutiadan.foodreminder.features.onboarding.signin.ui.*
import com.danigutiadan.foodreminder.utils.Result
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser


@Composable
fun SignInScreen(
    email: String,
    password: String,
    isButtonEnabled: Boolean,
    signInState: Result<FirebaseUser>,
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
    signInState: Result<FirebaseUser>,
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
            if (signInState is Result.Error)
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
        signInState = Result.Error(FirebaseAuthInvalidCredentialsException("text", "holas")),
        onLoginChanged = {_, _ ->},
        resetValues = {},
        goToSignUp = {},
        goToAddUserInfo = {}) {

    }
}








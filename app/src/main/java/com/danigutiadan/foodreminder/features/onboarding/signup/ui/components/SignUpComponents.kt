package com.danigutiadan.foodreminder.features.onboarding.signup.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TermsConditions(onTermsChecked: (Boolean) -> Unit, termsChecked: Boolean) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = termsChecked,
                onCheckedChange = { onTermsChecked(it) },
                Modifier.padding(end = 5.dp)
            )

            Text(text = "Acepto los terminos y condiciones")
        }
    }
}
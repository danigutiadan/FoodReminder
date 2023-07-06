package com.danigutiadan.foodreminder.features.dashboard.profile.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable

@Composable
fun ProfileScreen(onLogoutClick: () -> Unit) {
    Column() {
        Text(text = "Fragment profile")
        Button(onClick = { onLogoutClick() }) {
            Text(text = "Logout")
        }
    }
}
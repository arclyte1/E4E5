package com.example.e4e5.presentation.signin

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun SignInScreen(
    googleSignInListener: () -> Unit
) {
    Box {
        Button(onClick = {
            googleSignInListener()
        }) {
            Text("Sign in with google")
        }
    }
}
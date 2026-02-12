package com.example.project1.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project1.SignUpViewModel
import com.example.project1.database.AppDatabase

/**
 * TITLE: SettingsScreen.kt
 * @author Jesus Alfaro-Suarez
 * COURSE: CST- 438
 * DATE: 02/02/2026
 * ASSIGNMENT: Project 01
 * PURPOSE: Creates a screen for the sign up page.
 * Allows users to enter a username and password that is valid.
 */

@Composable
fun SettingsScreen() {

    var showUsername by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }


    if (showUsername) {
        ChangeUsernameScreen() // or SignUpScreen(onBack = { showSignUp = false })
        return
    }

    if (showPassword) {
        ChangePasswordScreen() // or SignUpScreen(onBack = { showSignUp = false })
        return
    }

    // Gets userDAO
    val context = LocalContext.current
    val userDao = AppDatabase
        .getDatabase(context.applicationContext)
        .userDao()

    // Creates SignUpViewModel and passes userDao through it.
    val viewModel = remember { SignUpViewModel(userDao) }

    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Title
        Text(
            text = "Settings",
            color = Color.Black,
            fontSize = 40.sp,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,


        )

        // Space between create account and username
        Spacer(modifier = Modifier.height(40.dp))
        HorizontalDivider(thickness = 2.dp,color =Color.LightGray)
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier.clickable(onClick = { showUsername = true }),
            text = "Change Username",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        // Space between create account and username
        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider(thickness = 2.dp,color =Color.LightGray)
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier.clickable(onClick = { showPassword = true }),
            text = "Change Password",
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider(thickness = 2.dp,color =Color.LightGray)
        Spacer(modifier = Modifier.height(80.dp))

        Button(
            // When button is pressed, onClick will call the SignUpViewModel
            // to validate the credentials (SignUpViewModel yet to be implemented).
            onClick = { viewModel.create(username, password) },
            modifier = Modifier
                .width(300.dp)
                .height(80.dp),
            colors = ButtonDefaults.buttonColors(),
        ) {
            Text(
                text = "Logout",
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

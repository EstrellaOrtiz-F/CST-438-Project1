package com.example.project1

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SignUpScreen(){
    
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Title
        Text(
            text = "Create Account",
            color = Color.Black,
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        // Space between create account and username
        Spacer(modifier = Modifier.height(80.dp))

        // Username
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            placeholder = {
                Text("Username", color = Color.Black)
            },
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 25.sp,
            ),
            modifier = Modifier
                .width(300.dp)
                .height(80.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Black,
                focusedBorderColor = Color.Black
            )
        )


        //space between username and password
        Spacer(modifier = Modifier.height(40.dp))

        // Password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = {
                Text("Password", color = Color.Black)
            },
            visualTransformation = PasswordVisualTransformation(),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 25.sp
            ),
            modifier = Modifier
                .width(300.dp)
                .height(80.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Black,
                focusedBorderColor = Color.Black
            )
        )

        //space between password and button
        Spacer(modifier = Modifier.height(80.dp))

        // Button
        Button(
            // When button is pressed, onClick will call the SignUpViewModel
            // to validate the credentials (SignUpViewModel yet to be implemented).
            onClick = { },
            modifier = Modifier
                .width(300.dp)
                .height(80.dp),
            colors = ButtonDefaults.buttonColors()
        ) {
            Text(
                text = "Create Account",
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(240.dp))

        // Already have an account screen
        Text(
            //modifier = Modifier.clickable(onClick = ), -- for later
            text = "Already have an account? Login",
            color = Color.Black,
            fontSize = 15.sp,
            fontStyle = FontStyle.Italic


        )
    }
}
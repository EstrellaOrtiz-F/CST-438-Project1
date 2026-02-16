package com.example.project1.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project1.SignUpViewModel
import com.example.project1.database.AppDatabase

@Composable
fun ChangeUsernameScreen() {

    // Gets userDAO
    val context = LocalContext.current
    val userDao = AppDatabase
        .getDatabase(context.applicationContext)
        .userDao()

    // Creates SignUpViewModel and passes userDao through it.
    val viewModel = remember { ChangeUserViewModel(userDao) }

    var username by remember { mutableStateOf("") }
    var newUsername by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Title
        Text(
            text = "Change Username",
            color = Color.Black,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        // Space between create account and username
        Spacer(modifier = Modifier.height(25.dp))
        HorizontalDivider(thickness = 2.dp, color = Color.LightGray)
        Spacer(modifier = Modifier.height(25.dp))

        /* Start of box */

        Box(
            modifier = Modifier
                .width(370.dp)
                .height(370.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.10f),
                    shape = RoundedCornerShape(20.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 25.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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

                // new Username
                OutlinedTextField(
                    value = newUsername,
                    onValueChange = { newUsername = it },
                    placeholder = {
                        Text("New Username", color = Color.Black)
                    },
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


                //space between username and password
                Spacer(modifier = Modifier.height(40.dp))

                // new Username
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
            }
        }

        /*  End of Box */

        //space between password and button
        Spacer(modifier = Modifier.height(60.dp))

        // Button
        Button(
            onClick = { viewModel.changeUsername(username, newUsername, password) },
            modifier = Modifier
                .width(300.dp)
                .height(80.dp),
            colors = ButtonDefaults.buttonColors(),
        ) {
            Text(
                text = "Change",
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(35.dp))
    }
}
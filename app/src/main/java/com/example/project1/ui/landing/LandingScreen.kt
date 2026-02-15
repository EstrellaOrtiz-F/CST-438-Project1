package com.example.project1.ui.landing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
//the loading screen
//what users see after loging in
//they can nagivage to their profile,the cardlist view or the settings
fun LandingScreen(
    username: String,
    onOpenProfile: () -> Unit,
    onOpenCards: () -> Unit,
    onOpenSettings: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome, $username",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(28.dp))
  //displays profile
        Button(
            onClick = onOpenProfile,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Profile")
        }

        Spacer(Modifier.height(12.dp))
  //cards button
        Button(
            onClick = onOpenCards,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Cards")
        }

        Spacer(Modifier.height(12.dp))

        //displays the settings
        //I linked the landing page to the settings
        //I didn't touch the settings much and the functions of the features still need to be implemented
        Button(
            onClick = onOpenSettings,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Settings")
        }
    }
}

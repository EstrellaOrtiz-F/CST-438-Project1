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

        Button(
            onClick = onOpenProfile,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Profile")
        }

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = onOpenCards,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Cards")
        }

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = onOpenSettings,
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Settings")
        }
    }
}

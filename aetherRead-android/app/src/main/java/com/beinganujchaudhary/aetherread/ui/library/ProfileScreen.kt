package com.beinganujchaudhary.aetherread.ui.library

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit,
    onSignOut: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My AetherRead", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Subscription Banner
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F5EB)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Looking to invite your team?", fontWeight = FontWeight.Bold, color = Color.Black)
                        Text("AetherRead Mobile subscription for multiple users", fontSize = 12.sp, color = Color.DarkGray)
                    }
                    Button(
                        onClick = { /* TODO */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                    ) {
                        Text("Learn More")
                    }
                }
            }

            // Profile Info
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.DarkGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text("A", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("anuj.chaudhary.ba@gmail.com", fontWeight = FontWeight.Medium)
                    Text("Aether Drive  16.8 MB / 5.0 GB", fontSize = 12.sp, color = Color.Gray)
                    Surface(
                        color = Color(0xFF00C8FF),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text("Subscribed", color = Color.Black, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Settings List
            ProfileMenuItem(Icons.Default.Settings, "Settings") {}
            ProfileMenuItem(Icons.Default.Favorite, "Recommend AetherRead") {}
            ProfileMenuItem(Icons.Default.Info, "Help and Feedback") {}
            ProfileMenuItem(Icons.Default.Info, "About") {}
            ProfileMenuItem(Icons.Default.Star, "Tip Center") {}
            ProfileMenuItem(Icons.Default.AccountCircle, "Manage") {}
            ProfileMenuItem(Icons.Default.Lock, "Privacy Consent") {}
            ProfileMenuItem(Icons.Default.ExitToApp, "Sign Out") { onSignOut() }
        }
    }
}

@Composable
fun ProfileMenuItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = title, tint = Color.Gray, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, fontSize = 16.sp, fontWeight = FontWeight.Normal)
    }
}

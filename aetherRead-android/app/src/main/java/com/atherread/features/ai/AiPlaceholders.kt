package com.atherread.features.ai

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.atherread.core.theme.DarkBackground
import com.atherread.core.theme.TextPrimary

interface FutureAiFeature {
    fun open()
}

@Composable
fun AiComingSoonScreen(featureName: String, onBack: () -> Unit) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        Toast.makeText(context, "$featureName (Coming Soon)", Toast.LENGTH_SHORT).show()
        onBack() // Automatically pop back since it's just a placeholder
    }
    
    Box(modifier = Modifier.fillMaxSize().background(DarkBackground), contentAlignment = Alignment.Center) {
        Text("Loading $featureName...", color = TextPrimary)
    }
}

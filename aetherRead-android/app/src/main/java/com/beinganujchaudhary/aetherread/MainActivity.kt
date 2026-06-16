package com.beinganujchaudhary.aetherread

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.beinganujchaudhary.aetherread.core.navigation.AetherReadNavGraph
import com.beinganujchaudhary.aetherread.core.theme.AetherReadTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Single-Activity entry point for AetherRead.
 * All navigation is handled via Jetpack Compose Navigation inside [AetherReadNavGraph].
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()          // Edge-to-edge display
        setContent {
            AetherReadTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    AetherReadNavGraph()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainPreview() {
    AetherReadTheme {
        AetherReadNavGraph()
    }
}

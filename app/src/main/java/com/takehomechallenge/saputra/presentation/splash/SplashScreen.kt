package com.takehomechallenge.saputra.presentation.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.takehomechallenge.saputra.R
import com.takehomechallenge.saputra.ui.theme.BejanaTestTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit = {}  // Default value for preview
) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "alpha"
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(2000)
        onNavigateToHome()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.splash_background),
            contentDescription = "Splash Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Logo container
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.alpha(alphaAnim)
        ) {

            Spacer(modifier = Modifier.height(160.dp))
            // First logo
            Image(
                painter = painterResource(id = R.drawable.logo_1),
                contentDescription = "Logo 1",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(149.dp))

            // Second logo
            Image(
                painter = painterResource(id = R.drawable.logo_2),
                contentDescription = "Logo 2",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(name = "SplashScreen (Light)", showBackground = true)
@Composable
fun SplashScreenPreviewLight() {
    BejanaTestTheme(darkTheme = false) {
        SplashScreen()
    }
}

@Preview(name = "SplashScreen (Dark)", showBackground = true)
@Composable
fun SplashScreenPreviewDark() {
    BejanaTestTheme(darkTheme = true) {
        SplashScreen()
    }
}


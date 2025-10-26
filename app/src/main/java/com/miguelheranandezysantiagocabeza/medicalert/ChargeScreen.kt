package com.miguelheranandezysantiagocabeza.medicalert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.rotate


@Composable
fun MedicAlertSplashScreen() {
    // Animación de rotación para el spinner
    val infiniteTransition = rememberInfiniteTransition(label = "spinner")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.weight(1f))

            // Logo MedicAlert (Cruz con gradiente)
            Image(painter = painterResource(R.drawable.medicalertlogo), contentDescription = "Logo corporativo", modifier = Modifier.size(500.dp))

            Spacer(modifier = Modifier.height(16.dp))

            // Spinner de carga
            LoadingSpinner(rotation = rotation)

            Spacer(modifier = Modifier.weight(1f))

            // Logo inferior
            Box(
                modifier = Modifier
                    .size(100.dp),
                contentAlignment = Alignment.Center
            ) {

                Image(painter = painterResource(R.drawable.papitasfritaslogo), contentDescription = "Logo corporativo", modifier = Modifier.fillMaxSize())
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun LoadingSpinner(rotation: Float) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .rotate(rotation),
        contentAlignment = Alignment.Center
    ) {
        // Puntos del spinner
        repeat(8) { index ->
            val angle = (index * 45f)
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .rotate(angle)
            ) {
                Box(
                    modifier = Modifier
                        .height(15.dp)
                        .width(8.dp)
                        .offset(y = (-10).dp)
                        .background(
                            Color.Blue.copy(alpha = 0.2f + (index * 0.1f))
                        )
                )
            }
        }
    }
}

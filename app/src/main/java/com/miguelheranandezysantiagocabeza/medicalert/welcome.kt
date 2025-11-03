package com.miguelheranandezysantiagocabeza.medicalert

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.miguelheranandezysantiagocabeza.medicalert.ui.theme.MedicAlertTheme

@Composable
fun WelcomeScreen(
    onCitas: () -> Unit,
    onMedicaciones: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scroll = rememberScrollState()
    val year = java.time.Year.now().value

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { inner ->
        Column(
            modifier = modifier
                .padding(inner)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.medicalertlogo),
                contentDescription = stringResource(R.string.cd_app_logo),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scroll)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(24.dp))

                Text(
                    text = stringResource(R.string.welcome_title),
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary, // Corregido
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(32.dp))

                Button(
                    onClick = onCitas,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer, // Corregido
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer // Corregido
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(stringResource(R.string.welcome_cta_citas))
                }

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = onMedicaciones,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer, // Corregido
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer // Corregido
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(stringResource(R.string.welcome_cta_meds))
                }

                Spacer(Modifier.height(100
                    .dp))

                Text(
                    text = "©$year Papitas Fritas Co. All rights reserved.\nInnovación Crocante.",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.outline // Corregido
                )

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun WelcomePreview() {
    MedicAlertTheme {
        WelcomeScreen(onCitas = {}, onMedicaciones = {})
    }
}

package com.miguelheranandezysantiagocabeza.medicalert

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Colores - Cambiar nombres para evitar conflictos
private val HistoryHeaderColor = Color(0xFFB3E5FC)
private val HistoryCardColor = Color(0xFFE3F2FD)
private val HistoryButtonColor = Color(0xFF90CAF9)
private val HistorySubtitleColor = Color(0xFF6B6B6B)

// Estado del medicamento
enum class DoseStatus { TAKEN, DUE, MISSED }

// Modelo de datos
data class MedHistoryItem(
    val name: String,
    val date: String,
    val time: String,
    val status: DoseStatus
)

@Composable
fun HistoryScreen(
    items: List<MedHistoryItem>,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HistoryHeaderColor)
                    .padding(vertical = 16.dp)
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 12.dp)
                ) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás", tint = Color.Black)
                }
                Text(
                    text = "Historial",
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = onBack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = HistoryButtonColor)
                ) {
                    Text(
                        text = "Volver",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(items) { item ->
                    HistoryCard(item)
                }
            }
        }
    )
}

@Composable
private fun HistoryCard(item: MedHistoryItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(HistoryCardColor, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.name,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "${item.date}   ${item.time}",
                color = HistorySubtitleColor,
                fontSize = 12.sp
            )
        }
        when (item.status) {
            DoseStatus.TAKEN -> Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "Tomada",
                tint = Color(0xFF2ECC71),
                modifier = Modifier.size(28.dp)
            )
            DoseStatus.DUE -> Icon(
                imageVector = Icons.Filled.AccessTime,
                contentDescription = "Pendiente",
                tint = Color.Black,
                modifier = Modifier.size(28.dp)
            )
            DoseStatus.MISSED -> Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = "Omitida",
                tint = Color(0xFFFF6F00),
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

// Solo una preview básica para ver la pantalla completa
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HistoryScreenPreview() {
    val sampleItems = remember {
        listOf(
            MedHistoryItem("Acetaminofén", "10/03/2025", "8:00am", DoseStatus.TAKEN),
            MedHistoryItem("Loratadina", "10/03/2025", "9:00am", DoseStatus.DUE),
            MedHistoryItem("Risperidona", "10/03/2025", "8:00am", DoseStatus.MISSED)
        )
    }
    MaterialTheme {
        HistoryScreen(items = sampleItems, onBack = {})
    }
}
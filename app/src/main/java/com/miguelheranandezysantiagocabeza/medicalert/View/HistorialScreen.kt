package com.miguelheranandezysantiagocabeza.medicalert.View

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

// Colores personalizados para la pantalla de historial
private val HistoryHeaderColor = Color(0xFF87CEEB) // Azul claro para la barra superior
private val HistoryCardColor = Color.White // Fondo blanco para las tarjetas
private val HistoryButtonColor = Color(0xFF87CEEB) // Azul claro para el botón
private val HistorySubtitleColor = Color(0xFF6B6B6B) // Gris para texto secundario

// Enumeración para representar el estado de una dosis de medicamento
enum class DoseStatus {
    TAKEN,  // Tomada
    DUE,    // Pendiente
    MISSED  // Omitida
}

// Modelo de datos para un elemento del historial de medicamentos
data class MedHistoryItem(
    val name: String,      // Nombre del medicamento
    val date: String,      // Fecha (formato: dd/mm/yyyy)
    val time: String,      // Hora (formato: h:mma/pm)
    val status: DoseStatus // Estado de la dosis
)

/**
 * Pantalla de Historial de Medicamentos
 * Muestra una lista de todas las dosis de medicamentos con su estado
 *
 * @param items Lista de elementos del historial a mostrar
 * @param onBack Callback que se ejecuta al hacer clic en volver
 */
@Composable
fun HistoryScreen(
    items: List<MedHistoryItem>,
    onBack: () -> Unit
) {
    // Scaffold: estructura básica de la pantalla
    Scaffold(
        // Barra superior personalizada
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth() // Ocupa todo el ancho
                    .background(HistoryHeaderColor) // Fondo azul claro
                    .padding(vertical = 12.dp) // Padding vertical reducido
            ) {
                // Botón de retroceso alineado a la izquierda
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .align(Alignment.CenterStart) // Alinea a la izquierda verticalmente centrado
                        .padding(start = 8.dp) // Pequeño padding desde el borde
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Atrás",
                        tint = Color.White // Icono blanco
                    )
                }
                // Título centrado
                Text(
                    text = "Historial",
                    modifier = Modifier.align(Alignment.Center), // Centrado en el Box
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.White // Texto blanco
                )
            }
        },
        // Barra inferior con botón de volver
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp) // Padding alrededor
            ) {
                // Botón de volver
                Button(
                    onClick = onBack,
                    modifier = Modifier
                        .fillMaxWidth() // Ocupa todo el ancho disponible
                        .height(48.dp), // Altura estándar
                    shape = RoundedCornerShape(8.dp), // Bordes redondeados suaves
                    colors = ButtonDefaults.buttonColors(
                        containerColor = HistoryButtonColor // Azul claro
                    )
                ) {
                    Text(
                        text = "Volver",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                }
            }
        },
        // Contenido principal: lista de elementos del historial
        content = { innerPadding ->
            // LazyColumn: lista con scroll eficiente para muchos elementos
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding) // Respeta el padding del Scaffold
                    .fillMaxSize() // Ocupa todo el espacio disponible
                    .padding(horizontal = 16.dp, vertical = 12.dp), // Padding interno
                verticalArrangement = Arrangement.spacedBy(10.dp) // Espacio entre tarjetas
            ) {
                // Itera sobre cada elemento y crea una tarjeta
                items(items) { item ->
                    HistoryCard(item)
                }
            }
        }
    )
}

/**
 * Tarjeta individual que muestra un elemento del historial
 *
 * @param item Elemento del historial con información del medicamento y su estado
 */
@Composable
private fun HistoryCard(item: MedHistoryItem) {
    // Row: organiza el contenido horizontalmente
    Row(
        modifier = Modifier
            .fillMaxWidth() // Ocupa todo el ancho
            .background(HistoryCardColor, RoundedCornerShape(8.dp)) // Fondo blanco con bordes redondeados
            .padding(horizontal = 16.dp, vertical = 12.dp), // Padding interno
        verticalAlignment = Alignment.CenterVertically // Alinea verticalmente al centro
    ) {
        // Column: contiene la información del medicamento
        Column(modifier = Modifier.weight(1f)) { // weight(1f) empuja el icono a la derecha
            // Nombre del medicamento en negrita
            Text(
                text = item.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp)) // Espacio entre nombre y fecha/hora

            // Fecha y hora en texto secundario
            Text(
                text = "${item.date} ${item.time}", // Concatena fecha y hora con espacio
                color = HistorySubtitleColor, // Gris
                fontSize = 12.sp
            )
        }

        // Icono que representa el estado de la dosis
        when (item.status) {
            // Dosis tomada: check verde
            DoseStatus.TAKEN -> Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "Tomada",
                tint = Color(0xFF4CAF50), // Verde
                modifier = Modifier.size(28.dp)
            )
            // Dosis pendiente: reloj negro
            DoseStatus.DUE -> Icon(
                imageVector = Icons.Filled.AccessTime,
                contentDescription = "Pendiente",
                tint = Color.Black, // Negro
                modifier = Modifier.size(28.dp)
            )
            // Dosis omitida: triángulo de advertencia naranja
            DoseStatus.MISSED -> Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = "Omitida",
                tint = Color(0xFFFF9800), // Naranja
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

// Preview para visualizar la pantalla en Android Studio
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HistoryScreenPreview() {
    // Datos de ejemplo para la preview
    val sampleItems = remember {
        listOf(
            MedHistoryItem("Acetaminofén", "10/03/2025", "8:00am", DoseStatus.TAKEN),
            MedHistoryItem("Loratadina", "10/03/2025", "9:00am", DoseStatus.DUE),
            MedHistoryItem("Risperidona", "10/03/2025", "8:00am", DoseStatus.MISSED)
        )
    }
    // Envuelve en MaterialTheme para aplicar el tema
    MaterialTheme {
        HistoryScreen(items = sampleItems, onBack = {})
    }
}
package com.miguelheranandezysantiagocabeza.medicalert.View

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Modelo de datos para una cita médica
data class Cita(
    val titulo: String,    // Título de la cita (ej: "Odontólogo")
    val fecha: String,     // Fecha y hora de la cita
    val lugar: String      // Lugar donde se realizará la cita
)

/**
 * Pantalla principal de Citas Médicas
 * Muestra una lista de citas con opciones para ver detalles, editar y acceder al historial
 *
 * @param OnClickDetalles Callback que se ejecuta al hacer clic en una tarjeta de cita
 * @param OnClickAgregar Callback que se ejecuta al hacer clic en añadir o editar cita
 * @param OnclickVolver Callback que se ejecuta al hacer clic en el botón de volver
 * @param OnclickHistorial Callback que se ejecuta al hacer clic en el botón de historial
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitasScreen(
    OnClickDetalles: (cita: Cita) -> Unit,
    OnClickAgregar: () -> Unit,
    OnclickVolver: () -> Unit,
    OnclickHistorial: () -> Unit
) {
    // Lista de citas con datos de ejemplo
    // remember y mutableStateListOf mantienen el estado de la lista entre recomposiciones
    val citas = remember {
        mutableStateListOf(
            Cita("Odontólogo", "12/11/2025 - 3:00 PM", "Clínica Santa María"),
            Cita("Control general", "18/11/2025 - 10:00 AM", "Hospital Universitario")
        )
    }

    // Scaffold: estructura básica de la pantalla con topBar y bottomBar
    Scaffold(
        // Barra superior de la aplicación
        topBar = {
            TopAppBar(
                title = {
                    // Título de la pantalla
                    Text(
                        text = "Citas",
                        fontSize = 18.sp, // Tamaño reducido
                        fontWeight = FontWeight.SemiBold, // Peso semi-negrita
                        color = Color.White // Color blanco para contraste con fondo azul
                    )
                },
                // Icono de navegación (botón volver)
                navigationIcon = {
                    IconButton(onClick = { OnclickVolver() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White // Color blanco para el icono
                        )
                    }
                },
                // Acciones en la barra (botón historial)
                actions = {
                    IconButton(onClick = { OnclickHistorial() }) {
                        Icon(
                            imageVector = Icons.Outlined.List,
                            contentDescription = "Historial",
                            tint = Color.White // Color blanco para el icono
                        )
                    }
                },
                // Colores de la barra superior
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF87CEEB) // Azul claro (Sky Blue)
                )
            )
        },
        // Barra inferior con botón de añadir cita
        bottomBar = {
            // Box para centrar el botón
            Box(
                modifier = Modifier
                    .fillMaxWidth() // Ocupa todo el ancho
                    .padding(16.dp), // Padding alrededor del contenedor
                contentAlignment = Alignment.Center // Centra el contenido
            ) {
                // Botón para añadir nueva cita
                Button(
                    onClick = { OnClickAgregar() },
                    modifier = Modifier
                        .wrapContentWidth() // Ajusta el ancho al contenido
                        .height(40.dp) // Altura del botón
                        .padding(horizontal = 8.dp), // Padding horizontal interno
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF87CEEB) // Azul claro
                    ),
                    shape = RoundedCornerShape(6.dp) // Bordes redondeados suaves
                ) {
                    Text(
                        text = "Añadir Cita",
                        fontSize = 14.sp, // Tamaño de fuente pequeño
                        fontWeight = FontWeight.Normal, // Peso normal
                        color = Color.White // Texto blanco
                    )
                }
            }
        }
    ) { paddingValues ->
        // Contenido principal: lista de citas
        Column(
            modifier = Modifier
                .fillMaxSize() // Ocupa todo el espacio disponible
                .padding(paddingValues) // Respeta el padding del Scaffold
                .padding(horizontal = 16.dp, vertical = 8.dp), // Padding adicional interno
            verticalArrangement = Arrangement.spacedBy(8.dp) // Espacio de 8dp entre cada elemento
        ) {
            // Itera sobre cada cita y crea una tarjeta
            citas.forEach { cita ->
                CitaCard(
                    cita = cita,
                    onDetalles = { OnClickDetalles(cita) }, // Callback para ver detalles
                    onEditar = { OnClickAgregar() } // Callback para editar
                )
            }
        }
    }
}

/**
 * Tarjeta individual que muestra la información de una cita médica
 *
 * @param cita Objeto con los datos de la cita (título, fecha, lugar)
 * @param onDetalles Callback que se ejecuta al hacer clic en la tarjeta completa
 * @param onEditar Callback que se ejecuta al hacer clic en el icono de editar
 */
@Composable
fun CitaCard(
    cita: Cita,
    onDetalles: () -> Unit,
    onEditar: () -> Unit
) {
    // Card: contenedor con elevación y bordes redondeados
    Card(
        modifier = Modifier
            .fillMaxWidth() // Ocupa todo el ancho disponible
            .clickable(onClick = onDetalles), // Hace la tarjeta clickeable
        shape = RoundedCornerShape(8.dp), // Bordes redondeados de 8dp
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Fondo blanco limpio
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp) // Sombra sutil
    ) {
        // Row: organiza el contenido horizontalmente
        Row(
            modifier = Modifier
                .fillMaxWidth() // Ocupa todo el ancho
                .padding(12.dp), // Padding interno compacto
            horizontalArrangement = Arrangement.SpaceBetween, // Separa contenido a los extremos
            verticalAlignment = Alignment.CenterVertically // Alinea verticalmente al centro
        ) {
            // Column: contiene la información de la cita (título, fecha, lugar)
            Column(
                modifier = Modifier.weight(1f) // Ocupa el espacio disponible
            ) {
                // Título de la cita en mayúsculas y negrita
                Text(
                    text = cita.titulo.uppercase(), // Convierte a mayúsculas
                    fontSize = 14.sp, // Tamaño pequeño
                    fontWeight = FontWeight.Bold, // Negrita para destacar
                    color = Color(0xFF2C2C2C) // Gris oscuro
                )
                Spacer(modifier = Modifier.height(4.dp)) // Espacio pequeño

                // Fecha y hora de la cita
                Text(
                    text = cita.fecha,
                    fontSize = 12.sp, // Tamaño pequeño
                    color = Color(0xFF666666) // Gris medio
                )
                Spacer(modifier = Modifier.height(2.dp)) // Espacio mínimo

                // Lugar de la cita
                Text(
                    text = cita.lugar,
                    fontSize = 12.sp, // Tamaño pequeño
                    color = Color(0xFF666666) // Gris medio
                )
            }

            // Botón de edición (icono)
            IconButton(
                onClick = onEditar,
                modifier = Modifier.size(36.dp) // Tamaño del área clickeable
            ) {
                Icon(
                    imageVector = Icons.Default.Edit, // Icono de lápiz/editar
                    contentDescription = "Editar",
                    tint = Color(0xFF666666), // Color gris medio
                    modifier = Modifier.size(20.dp) // Tamaño del icono
                )
            }
        }
    }
}

// Preview de la pantalla completa
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCitasScreen() {
    CitasScreen(
        OnClickDetalles = {},
        OnClickAgregar = {},
        OnclickVolver = {},
        OnclickHistorial = {}
    )
}

// Preview de una tarjeta individual
@Preview(showBackground = true)
@Composable
fun PreviewCitaCard() {
    CitaCard(
        cita = Cita(
            titulo = "Odontólogo",
            fecha = "12/11/2025 - 3:00 PM",
            lugar = "Clínica Santa María"
        ),
        onDetalles = {},
        onEditar = {}
    )
}
package com.miguelheranandezysantiagocabeza.medicalert.View

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

// Colores personalizados (mismos que la pantalla de medicación)
val CitaHeaderColor = Color(0xFF87CEEB) // Azul claro para la barra superior
val CitaButtonColor = Color(0xFF87CEEB) // Azul claro para el botón Guardar
val CitaPrimaryTextColor = Color(0xFF2C2C2C) // Gris oscuro para texto principal
val CitaSecondaryTextColor = Color(0xFF666666) // Gris medio para texto secundario
val CitaDividerColor = Color(0xFFE0E0E0) // Gris claro para los divisores

/**
 * Pantalla para Editar o Añadir una nueva Cita Médica
 * Permite al usuario ingresar información de una cita médica
 *
 * @param onBack Callback que se ejecuta al hacer clic en volver
 * @param onSave Callback que se ejecuta al guardar, recibe todos los datos de la cita
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditCitaScreen(
    onBack: () -> Unit,
    onSave: (titulo: String, fecha: String, hora: String, lugar: String, notas: String) -> Unit
) {
    // Estados para almacenar los valores de los campos
    var titulo by remember { mutableStateOf("") } // Título de la cita (ej: "Odontólogo")
    var fecha by remember { mutableStateOf("") } // Fecha de la cita
    var hora by remember { mutableStateOf("") } // Hora de la cita
    var lugar by remember { mutableStateOf("") } // Lugar de la cita
    var notas by remember { mutableStateOf("") } // Notas adicionales

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // DatePickerDialog para seleccionar la fecha
    val datePicker = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                // Formatea la fecha como dd/mm/yyyy
                fecha = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    // TimePickerDialog para seleccionar la hora
    val timePicker = remember {
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                // Formatea la hora en formato 12 horas con AM/PM
                hora = String.format(
                    "%02d:%02d %s",
                    if (hourOfDay % 12 == 0) 12 else hourOfDay % 12,
                    minute,
                    if (hourOfDay < 12) "AM" else "PM"
                )
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false // Formato de 12 horas
        )
    }

    // Scaffold: estructura básica de la pantalla con barra superior
    Scaffold(
        // Barra superior personalizada
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth() // Ocupa todo el ancho
                    .background(CitaHeaderColor) // Fondo azul claro
                    .padding(vertical = 14.dp) // Padding vertical
            ) {
                // Botón de volver alineado a la izquierda
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 8.dp)
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Atrás",
                        tint = Color.White // Icono blanco
                    )
                }
                // Título centrado de dos líneas
                Text(
                    text = "Editar/Añadir\nCita",
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp, // Tamaño reducido
                    lineHeight = 20.sp,
                    color = Color.White // Texto blanco
                )
            }
        },
        containerColor = Color.White // Fondo blanco para la pantalla
    ) { innerPadding ->
        // Column: contenido principal organizado verticalmente
        Column(
            modifier = Modifier
                .padding(innerPadding) // Respeta el padding del Scaffold
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 24.dp), // Padding interno
            verticalArrangement = Arrangement.spacedBy(20.dp) // Espacio entre elementos
        ) {
            // Campo de Título
            CitaInput(
                label = "Título",
                value = titulo,
                onValueChange = { titulo = it },
                placeholder = "Ej: Odontólogo, Control general"
            )

            // Row: Fecha y Hora en la misma fila
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween // Separa los elementos
            ) {
                // Campo de Fecha (clickeable para abrir el selector de fecha)
                Column(
                    modifier = Modifier.weight(1f) // Ocupa la mitad del espacio
                ) {
                    // Label "Fecha"
                    Text(
                        text = "Fecha",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = CitaPrimaryTextColor
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    // Valor de la fecha (clickeable)
                    Text(
                        text = if (fecha.isEmpty()) "dd/mm/yyyy" else fecha,
                        fontSize = 13.sp,
                        color = if (fecha.isEmpty())
                            CitaSecondaryTextColor.copy(alpha = 0.5f)
                        else
                            CitaSecondaryTextColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { datePicker.show() } // Abre el selector de fecha
                            .padding(vertical = 4.dp)
                    )

                    // Divisor debajo del campo
                    Divider(
                        color = CitaDividerColor,
                        thickness = 1.dp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp)) // Espacio entre campos

                // Campo de Hora (clickeable para abrir el selector de hora)
                Column(
                    modifier = Modifier.weight(1f) // Ocupa la otra mitad del espacio
                ) {
                    // Label "Hora"
                    Text(
                        text = "Hora",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = CitaPrimaryTextColor
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    // Valor de la hora (clickeable)
                    Text(
                        text = if (hora.isEmpty()) "00:00 AM" else hora,
                        fontSize = 13.sp,
                        color = if (hora.isEmpty())
                            CitaSecondaryTextColor.copy(alpha = 0.5f)
                        else
                            CitaSecondaryTextColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { timePicker.show() } // Abre el selector de hora
                            .padding(vertical = 4.dp)
                    )

                    // Divisor debajo del campo
                    Divider(
                        color = CitaDividerColor,
                        thickness = 1.dp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Campo de Lugar
            CitaInput(
                label = "Lugar",
                value = lugar,
                onValueChange = { lugar = it },
                placeholder = "Ej: Clínica Santa María"
            )

            // Campo de Notas (opcional)
            CitaInput(
                label = "Notas",
                value = notas,
                onValueChange = { notas = it },
                placeholder = "Notas adicionales (opcional)"
            )

            Spacer(modifier = Modifier.weight(1f)) // Empuja el botón Guardar hacia abajo

            // Botón de Guardar
            Button(
                onClick = { onSave(titulo, fecha, hora, lugar, notas) },
                modifier = Modifier
                    .fillMaxWidth() // Ocupa todo el ancho
                    .height(50.dp), // Altura estándar
                shape = RoundedCornerShape(8.dp), // Bordes redondeados suaves
                colors = ButtonDefaults.buttonColors(
                    containerColor = CitaButtonColor // Azul claro
                )
            ) {
                Text(
                    "Guardar",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}

/**
 * Campo de entrada personalizado para citas
 * Muestra un label, un campo de texto editable y un divisor
 *
 * @param label Etiqueta del campo (ej: "Título", "Lugar")
 * @param value Valor actual del campo
 * @param onValueChange Callback que se ejecuta cuando cambia el valor
 * @param placeholder Texto placeholder cuando el campo está vacío
 * @param modifier Modificador opcional para personalizar el componente
 */
@Composable
fun CitaInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // Label del campo
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp, // Tamaño reducido
            color = CitaPrimaryTextColor
        )
        Spacer(modifier = Modifier.height(4.dp))

        // Campo de texto básico
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                fontSize = 13.sp, // Tamaño de texto reducido
                color = CitaSecondaryTextColor
            ),
            singleLine = true, // Solo una línea
            decorationBox = { innerTextField ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    // Muestra el placeholder si el campo está vacío
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            fontSize = 13.sp,
                            color = CitaSecondaryTextColor.copy(alpha = 0.5f) // Semi-transparente
                        )
                    }
                    innerTextField() // Renderiza el campo de texto
                }
            },
            modifier = Modifier.padding(vertical = 4.dp)
        )

        // Divisor debajo del campo
        Divider(
            color = CitaDividerColor,
            thickness = 1.dp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

// Preview de la pantalla completa
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AddEditCitaPreview() {
    MaterialTheme {
        AddEditCitaScreen(
            onBack = {},
            onSave = { _, _, _, _, _ -> }
        )
    }
}


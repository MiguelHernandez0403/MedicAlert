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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miguelheranandezysantiagocabeza.medicalert.viewmodel.CitasViewModel
import kotlinx.coroutines.flow.collectLatest
import java.util.*

val CitaHeaderColor = Color(0xFF87CEEB)
val CitaButtonColor = Color(0xFF87CEEB)
val CitaPrimaryTextColor = Color(0xFF2C2C2C)
val CitaSecondaryTextColor = Color(0xFF666666)
val CitaDividerColor = Color(0xFFE0E0E0)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditCitaScreen(
    idCita: Int?,
    onBack: () -> Unit,
    onSave: () -> Unit,
    viewModel: CitasViewModel
) {
    val context = LocalContext.current

    var titulo by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var lugar by remember { mutableStateOf("") }
    var notas by remember { mutableStateOf("") }

    // === Cargar datos si es edición ===
    LaunchedEffect(idCita) {
        if (idCita != null && idCita != -1) {
            viewModel.obtenerPorId(idCita).collectLatest { m ->
                if (m != null) {
                    titulo = m.titulo
                    fecha = m.fecha
                    hora = m.hora
                    lugar = m.lugar
                    notas = m.notas ?: ""
                }
            }
        }
    }

    val calendar = Calendar.getInstance()

    // ------------------------- PICKERS -------------------------
    val datePicker = remember {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                fecha = String.format("%02d/%02d/%d", day, month + 1, year)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    val timePicker = remember {
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->

                hora = String.format(
                    "%02d:%02d %s",
                    if (hourOfDay % 12 == 0) 12 else hourOfDay % 12,
                    minute,
                    if (hourOfDay < 12) "AM" else "PM"
                )

            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )
    }

    // ------------------------- UI -------------------------
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CitaHeaderColor)
                    .padding(vertical = 14.dp)
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 8.dp)
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Atrás",
                        tint = Color.White
                    )
                }

                Text(
                    text = "Editar/Añadir\nCita",
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    color = Color.White
                )
            }
        },
        containerColor = Color.White
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            CitaInput("Título", titulo, { titulo = it }, "Ej: Odontólogo, Control general")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(modifier = Modifier.weight(1f)) {
                    Text("Fecha", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = if (fecha.isEmpty()) "dd/mm/yyyy" else fecha,
                        fontSize = 13.sp,
                        color = CitaSecondaryTextColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { datePicker.show() }
                            .padding(vertical = 4.dp)
                    )
                    Divider(color = CitaDividerColor)
                }

                Spacer(Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text("Hora", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = if (hora.isEmpty()) "00:00 AM" else hora,
                        fontSize = 13.sp,
                        color = CitaSecondaryTextColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { timePicker.show() }
                            .padding(vertical = 4.dp)
                    )
                    Divider(color = CitaDividerColor)
                }
            }

            CitaInput("Lugar", lugar, { lugar = it }, "Ej: Clínica Santa María")
            CitaInput("Notas", notas, { notas = it }, "Notas adicionales (opcional)")

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {

                    // ======================================================
                    //     GENERAR fechaHoraMillis de forma segura y sin bugs
                    // ======================================================
                    val cal = Calendar.getInstance()

                    if (fecha.contains("/") && hora.contains(":")) {
                        try {
                            val (d, m, y) = fecha.split("/").map { it.toInt() }
                            cal.set(Calendar.DAY_OF_MONTH, d)
                            cal.set(Calendar.MONTH, m - 1)
                            cal.set(Calendar.YEAR, y)

                            // Hora
                            val (h12, minAmPm) = hora.split(":")
                            val (min, ampm) = minAmPm.split(" ")

                            var h24 = h12.toInt()
                            val mn = min.toInt()

                            h24 = when (ampm.uppercase()) {
                                "AM" -> if (h24 == 12) 0 else h24
                                "PM" -> if (h24 != 12) h24 + 12 else 12
                                else -> h24
                            }

                            cal.set(Calendar.HOUR_OF_DAY, h24)
                            cal.set(Calendar.MINUTE, mn)
                            cal.set(Calendar.SECOND, 0)

                        } catch (_: Exception) { }
                    }

                    val fechaHoraMillis = cal.timeInMillis

                    if (idCita == null || idCita == -1) {
                        viewModel.insertar(
                            titulo = titulo,
                            fecha = fecha,
                            hora = hora,
                            fechaHoraMillis = fechaHoraMillis,
                            lugar = lugar,
                            notas = notas
                        )
                    } else {
                        viewModel.actualizar(
                            id = idCita,
                            titulo = titulo,
                            fecha = fecha,
                            hora = hora,
                            fechaHoraMillis = fechaHoraMillis,
                            lugar = lugar,
                            notas = notas
                        )
                    }

                    onSave()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CitaButtonColor)
            ) {
                Text("Guardar", fontWeight = FontWeight.SemiBold, color = Color.White)
            }
        }
    }
}

@Composable
fun CitaInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
        Spacer(Modifier.height(4.dp))

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(fontSize = 13.sp, color = CitaSecondaryTextColor),
            singleLine = true,
            decorationBox = { inner ->
                Row {
                    if (value.isEmpty()) {
                        Text(
                            placeholder,
                            fontSize = 13.sp,
                            color = CitaSecondaryTextColor.copy(alpha = 0.5f)
                        )
                    }
                    inner()
                }
            },
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Divider(color = CitaDividerColor)
    }
}

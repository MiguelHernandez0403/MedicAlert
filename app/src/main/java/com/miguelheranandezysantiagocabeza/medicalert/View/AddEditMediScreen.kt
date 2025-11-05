package com.miguelheranandezysantiagocabeza.medicalert.View

import android.app.TimePickerDialog
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import java.util.*

// Colores basados en la imagen (aproximados)
val HeaderColor = Color(0xFFB3E5FC) // Azul claro de la cabecera
val ButtonColor = Color(0xFF90CAF9) // Azul del botón Guardar
val PhotoBoxColor = Color(0xFFE3F2FD) // Azul muy claro del cuadro de la foto
val PrimaryTextColor = Color.Black
val SecondaryTextColor = Color(0xFF616161) // Color para el texto de relleno (como gris oscuro)
val DividerColor = Color(0xFFD3D3D3) // Gris claro para los divisores

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditMediScreen(
    onBack: () -> Unit,
    onSave: (nombre: String, dosis: String, regularidad: String, hora: String, foto: Uri?) -> Unit
) {
    // Valores de ejemplo para simular la vista de la imagen
    var nombre by remember { mutableStateOf("acetaminofen") }
    var dosis by remember { mutableStateOf("1 Tableta") }
    var regularidad by remember { mutableStateOf("diario") }
    var hora by remember { mutableStateOf("8:00 AM") }
    var fotoUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val timePicker by remember {
        mutableStateOf(
            TimePickerDialog(
                context,
                { _, hour, minute ->
                    hora = String.format(
                        "%02d:%02d %s",
                        if (hour % 12 == 0) 12 else hour % 12,
                        minute,
                        if (hour < 12) "AM" else "PM"
                    )
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
            )
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        fotoUri = uri
    }

    Scaffold(
        topBar = {
            // Cabecera idéntica a la imagen
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HeaderColor)
                    .padding(vertical = 20.dp, horizontal = 0.dp)
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = PrimaryTextColor)
                }
                Text(
                    text = "Editar/Añadir\nMedicación",
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp,
                    color = PrimaryTextColor
                )
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 100.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // --- Nombre ---
            Spacer(modifier = Modifier.height(10.dp))
            MedicationInput(
                label = "Nombre",
                value = nombre,
                onValueChange = { nombre = it },
                placeholder = "acetaminofen"
            )

            // --- Dosis ---
            MedicationInput(
                label = "Dosis",
                value = dosis,
                onValueChange = { dosis = it },
                placeholder = "1 Tableta"
            )

            // --- Regularidad y Hora ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Regularidad
                // Usamos el MedicationInput para el estilo de label/valor/divisor
                MedicationInput(
                    label = "Regularidad",
                    value = regularidad,
                    onValueChange = { regularidad = it },
                    placeholder = "diario",
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(20.dp))

                // Hora - Diseño idéntico al de la imagen
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Hora",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = PrimaryTextColor
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = hora,
                        fontSize = 14.sp,
                        color = SecondaryTextColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { timePicker.show() }
                    )
                    // Divisor
                    Divider(color = DividerColor, thickness = 1.dp, modifier = Modifier.padding(top = 2.dp))
                }
            }

            // --- Foto ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(PhotoBoxColor, RoundedCornerShape(8.dp))
                    .clickable { launcher.launch("image/*") },
                contentAlignment = Alignment.CenterStart
            ) {
                if (fotoUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(fotoUri),
                        contentDescription = "Foto medicamento",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Default.CameraAlt,
                            contentDescription = "Foto",
                            tint = PrimaryTextColor,
                            modifier = Modifier.padding(start = 20.dp)
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text("FOTO", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = PrimaryTextColor)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f)) // Empuja el botón Guardar hacia abajo

            // --- Guardar ---
            Button(
                onClick = { onSave(nombre, dosis, regularidad, hora, fotoUri) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(bottom = 20.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ButtonColor)
            ) {
                Text("Guardar", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
            }
        }
    }
}


@Composable
fun MedicationInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold,
            fontSize = 25.sp,
            color = PrimaryTextColor
        )
        Spacer(modifier = Modifier.height(6.dp))

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(fontSize = 14.sp, color = SecondaryTextColor),
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            fontSize = 20.sp,
                            color = SecondaryTextColor
                        )
                    }
                    innerTextField()
                }
            }
        )

        Divider(color = DividerColor, thickness = 1.dp, modifier = Modifier.padding(top = 2.dp))
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AddEditMediPreview() {
    MaterialTheme {
        AddEditMediScreen(
            onBack = {},
            onSave = { _, _, _, _, _ -> }
        )
    }
}
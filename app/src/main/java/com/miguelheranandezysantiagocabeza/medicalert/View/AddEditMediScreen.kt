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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import java.util.*



val HeaderColor = Color(0xFF87CEEB)
val ButtonColor = Color(0xFF87CEEB)
val PhotoBoxColor = Color(0xFFF5F5F5)
val PrimaryTextColor = Color(0xFF2C2C2C)
val SecondaryTextColor = Color(0xFF666666)
val DividerColor = Color(0xFFE0E0E0)



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditMediScreen(
    onBack: () -> Unit,
    onSave: () -> Unit
) {
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HeaderColor)
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
                    text = "Editar/Añadir\nMedicación",
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
            MedicationInput(
                label = "Nombre",
                value = nombre,
                onValueChange = { nombre = it },
                placeholder = "acetaminofen"
            )

            MedicationInput(
                label = "Dosis",
                value = dosis,
                onValueChange = { dosis = it },
                placeholder = "1 Tableta"
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MedicationInput(
                    label = "Regularidad",
                    value = regularidad,
                    onValueChange = { regularidad = it },
                    placeholder = "diario",
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Hora",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = PrimaryTextColor
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = hora,
                        fontSize = 13.sp,
                        color = SecondaryTextColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { timePicker.show() }
                            .padding(vertical = 4.dp)
                    )

                    Divider(
                        color = DividerColor,
                        thickness = 1.dp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(PhotoBoxColor, RoundedCornerShape(8.dp))
                    .clickable { launcher.launch("image/*") },
                contentAlignment = Alignment.Center
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
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "FOTO",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = PrimaryTextColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onSave() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonColor
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
            fontSize = 14.sp,
            color = PrimaryTextColor
        )
        Spacer(modifier = Modifier.height(4.dp))

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                fontSize = 13.sp,
                color = SecondaryTextColor
            ),
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            fontSize = 13.sp,
                            color = SecondaryTextColor.copy(alpha = 0.5f)
                        )
                    }
                    innerTextField()
                }
            },
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Divider(
            color = DividerColor,
            thickness = 1.dp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
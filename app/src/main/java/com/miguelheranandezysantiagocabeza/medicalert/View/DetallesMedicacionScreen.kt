package com.miguelheranandezysantiagocabeza.medicalert.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.miguelheranandezysantiagocabeza.medicalert.MedicAlertApplication
import com.miguelheranandezysantiagocabeza.medicalert.R
import com.miguelheranandezysantiagocabeza.medicalert.ViewModel.MedicacionViewModel
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.MedicacionEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallesMedicacionScreen(
    idMedicacion: Int,
    OnClickVolver: () -> Unit
) {

    val app = LocalContext.current.applicationContext as MedicAlertApplication

    val viewModel: MedicacionViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MedicacionViewModel(app.medicacionRepository) as T
            }
        }
    )

    val medicacion = viewModel.obtenerPorId(idMedicacion).collectAsState(initial = null)

    if (medicacion.value == null) {
        Text(
            text = "Cargando...",
            modifier = Modifier.padding(20.dp)
        )
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Detalles de\nMedicación",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            lineHeight = 22.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = OnClickVolver) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF87CEEB)
                )
            )
        }
    ) { paddingValues ->

        DetallesUI(
            medicacion = medicacion.value!!,
            onVolver = OnClickVolver,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun DetallesUI(
    medicacion: MedicacionEntity,
    onVolver: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        // ============================
        // FOTO DESDE FILE INTERNO
        // ============================
        if (!medicacion.imagen.isNullOrEmpty()) {

            val ruta = "file://${medicacion.imagen}"

            Image(
                painter = rememberAsyncImagePainter(ruta),
                contentDescription = "Foto del medicamento",
                modifier = Modifier
                    .size(140.dp)
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )

        } else {

            Image(
                painter = painterResource(R.drawable.camara),
                contentDescription = "Sin imagen",
                modifier = Modifier.size(120.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = medicacion.nombre,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2C2C2C)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "${medicacion.hora} · cada ${medicacion.frecuenciaHoras} horas",
            fontSize = 14.sp,
            color = Color(0xFF666666)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE8F4F8)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Dosis",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2C2C2C)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = medicacion.dosis,
                    fontSize = 16.sp,
                    color = Color(0xFF2C2C2C)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onVolver,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF87CEEB)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Volver",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}
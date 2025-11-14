package com.miguelheranandezysantiagocabeza.medicalert.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.miguelheranandezysantiagocabeza.medicalert.MedicAlertApplication
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.entity.HistorialEntity
import com.miguelheranandezysantiagocabeza.medicalert.ViewModel.MedicacionViewModel

private val HistoryHeaderColor = Color(0xFF87CEEB)
private val HistoryButtonColor = Color(0xFF87CEEB)
private val ItemBg = Color(0xFFF5F5F5)

@Composable
fun HistoryScreen(
    onBack: () -> Unit
) {
    // Obtener ViewModel correctamente usando tu Application
    val app = LocalContext.current.applicationContext as MedicAlertApplication

    val viewModel: MedicacionViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MedicacionViewModel(app.medicacionRepository) as T
            }
        }
    )

    // Historial completo
    val historial by viewModel.historial.collectAsState()

    // Agrupar por día
    val historialAgrupado = historial.groupBy { it.fecha }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HistoryHeaderColor)
                    .padding(vertical = 12.dp)
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 8.dp)
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Atrás",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Historial",
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color.White
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
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = HistoryButtonColor
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
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            historialAgrupado.forEach { (fecha, lista) ->

                // ---- TÍTULO: FECHA ----
                item {
                    Text(
                        text = fecha,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2C2C2C)
                    )
                }

                // ---- ITEMS DE ESE DÍA ----
                items(lista) { item ->
                    HistorialItem(item)
                }
            }
        }
    }
}

@Composable
fun HistorialItem(item: HistorialEntity) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(ItemBg, RoundedCornerShape(10.dp))
            .padding(14.dp)
    ) {
        Text(
            text = item.nombre,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2C2C2C)
        )
        Text(
            text = "Dosis: ${item.dosis}",
            fontSize = 14.sp,
            color = Color(0xFF555555)
        )
        Text(
            text = "Programada: ${item.horaProgramada}",
            fontSize = 13.sp,
            color = Color(0xFF777777)
        )
        Text(
            text = "Tomada: ${item.horaTomada}",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF444444)
        )
    }
}
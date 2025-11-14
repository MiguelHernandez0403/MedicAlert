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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.entity.MedicacionEntity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.miguelheranandezysantiagocabeza.medicalert.MedicAlertApplication
import com.miguelheranandezysantiagocabeza.medicalert.ViewModel.MedicacionViewModel
import androidx.lifecycle.ViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicacionesScreen(
    medicaciones: List<MedicacionEntity>,
    onEditMedicacion: (Int) -> Unit,
    onDetallesMedicacion: (Int) -> Unit,
    onVolver: () -> Unit,
    onHistorial: () -> Unit,
    onAddMedicacion: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Medicaciones",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = onHistorial) {
                        Icon(Icons.Outlined.List, contentDescription = "Historial", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF87CEEB)
                )
            )
        },
        bottomBar = {
            Button(
                onClick = onAddMedicacion,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF87CEEB))
            ) {
                Text("Añadir Medicación", color = Color.White)
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            medicaciones.forEach { medicacion ->
                MedicacionCard(
                    medicacion = medicacion,
                    onEdit = { id -> onEditMedicacion(id) },
                    onDetalles = { id -> onDetallesMedicacion(id) }
                )
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}


@Composable
fun MedicacionCard(
    medicacion: MedicacionEntity,
    onEdit: (Int) -> Unit,
    onDetalles: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDetalles(medicacion.id) }   // ← AQUÍ PASAMOS EL ID REAL
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            Column(modifier = Modifier.weight(1f)) {
                Text(medicacion.nombre)
                Text(medicacion.hora)
                Text(medicacion.dosis)
            }

            IconButton(onClick = { onEdit(medicacion.id) }) {   // ← AQUÍ TAMBIÉN PASAMOS EL ID
                Icon(Icons.Default.Edit, contentDescription = "Editar")
            }
        }
    }
}
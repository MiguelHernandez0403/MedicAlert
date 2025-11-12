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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion
import com.miguelheranandezysantiagocabeza.medicalert.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicacionesScreen(
    OnClickDetalles: () -> Unit,
    OnClickEditar: () -> Unit,
    OnclickVolver: () -> Unit,
    OnclickHistorial: () -> Unit
) {
    val medicaciones = remember {
        mutableStateListOf<Medicacion>(
            Medicacion("Aspirina", "8:00 AM", "1 TABLETA", 6, imagen = R.drawable.acetaminofen),
            Medicacion("Loratadina", "7:00 AM", "1 TABLETA", 12, null)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Medicaciones",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { OnclickVolver() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { OnclickHistorial() }) {
                        Icon(
                            imageVector = Icons.Outlined.List,
                            contentDescription = "Lista",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF87CEEB)
                )
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { OnClickEditar() },
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(40.dp)
                        .padding(horizontal = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF87CEEB)
                    ),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "Añadir Medicación",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            medicaciones.forEach { medicacion ->
                MedicacionCard(
                    medicacion = medicacion,
                    onEdit = { OnClickEditar() },
                    onDetalles = { OnClickDetalles() }
                )
            }
        }
    }
}

@Composable
fun MedicacionCard(
    medicacion: Medicacion,
    onEdit: () -> Unit,
    onDetalles: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onDetalles),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = medicacion.nombre,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2C2C2C)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = medicacion.hora,
                    fontSize = 13.sp,
                    color = Color(0xFF666666)
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = medicacion.dosis,
                    fontSize = 13.sp,
                    color = Color(0xFF666666)
                )
            }

            IconButton(
                onClick = onEdit,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar",
                    tint = Color(0xFF666666),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
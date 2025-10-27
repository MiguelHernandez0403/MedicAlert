package com.miguelheranandezysantiagocabeza.medicalert.Models

import androidx.compose.material3.Icon
import com.miguelheranandezysantiagocabeza.medicalert.R

data class Medicacion(
    val nombre: String,
    val hora: String,
    val dosis: String,
    val frecuencia: Int,
    val imagen : Int?
)


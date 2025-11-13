package com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicacion")
data class MedicacionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,
    val dosis: String,
    val hora: String,             // "HH:mm"
    val frecuenciaHoras: Int,     // cada cuántas horas repetir
    val imagen: String? = null    // puede ser URL, base64, nombre de archivo
)
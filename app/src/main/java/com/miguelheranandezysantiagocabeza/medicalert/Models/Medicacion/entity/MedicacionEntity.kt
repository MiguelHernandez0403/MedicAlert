package com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicacion")
data class MedicacionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val dosis: String,
    val hora: String,
    val frecuenciaHoras: Int,
    val imagen: String?
)
package com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historial")
data class HistorialEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idMedicacion: Int,
    val nombre: String,
    val dosis: String,
    val horaProgramada: String,
    val horaTomada: String,
    val fecha: String, // dd/MM/yyyy
    val tipo: String = "manual"
)
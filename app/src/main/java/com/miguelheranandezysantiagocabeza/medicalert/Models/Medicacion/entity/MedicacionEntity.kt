package com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicacion")
data class MedicacionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,
    val dosis: String,

    // Hora visible para UI
    val hora: String,

    // 🔥 Hora exacta en milisegundos, necesaria para AlarmManager
    val horaMillis: Long,

    // 🔥 Cada cuántas horas se repite
    val frecuenciaHoras: Int,   // 0 = no repetir

    // Ruta local de imagen (null si no hay foto)
    val imagen: String?
)

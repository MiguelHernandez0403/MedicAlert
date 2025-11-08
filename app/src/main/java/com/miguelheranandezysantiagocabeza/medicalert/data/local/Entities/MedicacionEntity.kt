package com.miguelheranandezysantiagocabeza.medicalert.data.local.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medicacion")
data class MedicacionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val hora: String,
    val frecuencia: Int,
    val dosis: String,
    val imagen: String?
)
package com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "citas")
data class CitasEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val titulo: String,
    val fecha: String,
    val hora: String,
    val lugar: String,
    val notas: String?
)
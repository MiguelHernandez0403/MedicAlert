package com.miguelheranandezysantiagocabeza.medicalert.data.local.MedicAlertDataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.miguelheranandezysantiagocabeza.medicalert.domain.Models.Medicacion
import com.miguelheranandezysantiagocabeza.medicalert.data.local.DAO.MedicacionDao

@Database (entities = [Medicacion::class], version = 1, exportSchema = false)
abstract class MedicAlertDatabase : RoomDatabase() {
    abstract fun medicacionDao(): MedicacionDao
}
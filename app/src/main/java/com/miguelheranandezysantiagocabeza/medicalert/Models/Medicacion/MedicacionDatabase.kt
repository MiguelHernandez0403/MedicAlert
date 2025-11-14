package com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.dao.CitasDao
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.dao.HistorialDao
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.dao.MedicacionDao
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.entity.CitasEntity
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.entity.HistorialEntity
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.entity.MedicacionEntity

@Database(
    entities = [
        MedicacionEntity::class,
        HistorialEntity::class,
        CitasEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class MedicacionDatabase : RoomDatabase() {

    abstract fun medicacionDao(): MedicacionDao
    abstract fun historialDao(): HistorialDao

    abstract fun citasDao(): CitasDao


    companion object {

        @Volatile
        private var INSTANCE: MedicacionDatabase? = null

        fun getInstance(context: Context): MedicacionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MedicacionDatabase::class.java,
                    "medicacion_db"
                )
                    .fallbackToDestructiveMigration()  // ← evita crashes por cambios
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
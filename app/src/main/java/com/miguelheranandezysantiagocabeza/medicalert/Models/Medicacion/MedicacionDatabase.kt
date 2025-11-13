package com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MedicacionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MedicacionDatabase : RoomDatabase() {

    abstract fun medicacionDao(): MedicacionDao

    companion object {
        @Volatile
        private var INSTANCE: MedicacionDatabase? = null

        fun getInstance(context: Context): MedicacionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MedicacionDatabase::class.java,
                    "medicacion_db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

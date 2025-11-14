package com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.repository

import android.content.Context
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.MedicacionDatabase
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.entity.CitasEntity
import kotlinx.coroutines.flow.Flow

class CitasRepository private constructor(context: Context) {

    private val citasDao = MedicacionDatabase.getInstance(context).citasDao()

    fun getCitas(): Flow<List<CitasEntity>> = citasDao.getAll()

    fun getCitasById(id: Int): Flow<CitasEntity?> =
        citasDao.getCitaById(id)

    suspend fun insertCita(cita: CitasEntity) = citasDao.insertCita(cita)

    suspend fun updateCita(cita: CitasEntity) = citasDao.updateCita(cita)

    suspend fun deleteCita(id: Int) = citasDao.deleteCita(id)

    companion object {

        @Volatile
        private var INSTANCE: CitasRepository? = null

        fun getInstance(context: Context): CitasRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = CitasRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }
}
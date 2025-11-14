package com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.repository

import android.content.Context
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.MedicacionDatabase
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.entity.HistorialEntity
import kotlinx.coroutines.flow.Flow

class HistorialRepository private constructor(context: Context) {

    private val historialDao = MedicacionDatabase.getInstance(context).historialDao()

    fun getHistorial(): Flow<List<HistorialEntity>> = historialDao.getAll()

    fun getHistorialPorMedicacion(id: Int): Flow<List<HistorialEntity>> =
        historialDao.getByMedicacion(id)

    suspend fun insertarHistorial(item: HistorialEntity) =
        historialDao.insert(item)

    suspend fun borrarHistorial(id: Int) =
        historialDao.delete(id)
}

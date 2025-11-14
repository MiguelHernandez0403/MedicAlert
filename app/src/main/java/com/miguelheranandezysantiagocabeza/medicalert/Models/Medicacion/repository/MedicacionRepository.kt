package com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.repository

import android.content.Context
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.MedicacionDatabase
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.entity.HistorialEntity
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.entity.MedicacionEntity
import kotlinx.coroutines.flow.Flow

class MedicacionRepository private constructor(context: Context) {

    private val db = MedicacionDatabase.Companion.getInstance(context)
    private val medicacionDao = db.medicacionDao()
    private val historialDao = db.historialDao()

    // ============================================================
    //                        MEDICACIÓN
    // ============================================================

    fun getAll(): Flow<List<MedicacionEntity>> =
        medicacionDao.getAll()

    suspend fun getById(id: Int) =
        medicacionDao.getById(id)

    fun getByIdFlow(id: Int): Flow<MedicacionEntity?> =
        medicacionDao.getByIdFlow(id)

    suspend fun insert(item: MedicacionEntity) =
        medicacionDao.insert(item)

    suspend fun update(item: MedicacionEntity) =
        medicacionDao.update(item)

    suspend fun delete(id: Int) =
        medicacionDao.deleteById(id)

    // ============================================================
    //                        HISTORIAL
    // ============================================================

    fun getHistorial(): Flow<List<HistorialEntity>> =
        historialDao.getAll()

    fun getHistorialPorMedicacion(id: Int): Flow<List<HistorialEntity>> =
        historialDao.getByMedicacion(id)

    suspend fun insertHistorial(item: HistorialEntity) =
        historialDao.insert(item)

    suspend fun deleteHistorial(id: Int) =
        historialDao.delete(id)

    suspend fun clearHistorial() =
        historialDao.clear()

    // ============================================================

    companion object {
        @Volatile private var INSTANCE: MedicacionRepository? = null

        fun getInstance(context: Context): MedicacionRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = MedicacionRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }
}
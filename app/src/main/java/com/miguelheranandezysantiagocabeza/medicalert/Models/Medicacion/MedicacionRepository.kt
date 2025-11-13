package com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion

import android.content.Context
import kotlinx.coroutines.flow.Flow

class MedicacionRepository private constructor(context: Context) {

    private val dao = MedicacionDatabase.getInstance(context).medicacionDao()

    fun getAll(): Flow<List<MedicacionEntity>> = dao.getAll()

    suspend fun getById(id: Int) = dao.getById(id)

    fun getByIdFlow(id: Int): Flow<MedicacionEntity?> = dao.getByIdFlow(id)

    suspend fun insert(item: MedicacionEntity) = dao.insert(item)

    suspend fun update(item: MedicacionEntity) = dao.update(item)

    suspend fun delete(id: Int) = dao.deleteById(id)

    companion object {
        @Volatile
        private var INSTANCE: MedicacionRepository? = null

        fun getInstance(context: Context): MedicacionRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = MedicacionRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }
}
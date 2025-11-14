package com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.entity.HistorialEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistorialDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: HistorialEntity)

    @Query("SELECT * FROM historial ORDER BY timestamp DESC")
    fun getAll(): Flow<List<HistorialEntity>>

    @Query("SELECT * FROM historial WHERE idMedicacion = :id ORDER BY timestamp DESC")
    fun getByMedicacion(id: Int): Flow<List<HistorialEntity>>

    @Query("DELETE FROM historial WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM historial")
    suspend fun clear()
}

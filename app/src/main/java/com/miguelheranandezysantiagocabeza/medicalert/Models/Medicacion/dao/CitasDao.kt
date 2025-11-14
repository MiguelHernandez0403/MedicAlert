package com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.entity.CitasEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CitasDao {

    @Query("SELECT * FROM citas ORDER BY fecha DESC, id DESC")
    fun getAll(): Flow<List<CitasEntity>>

    @Query("SELECT * FROM citas WHERE id = :id")
    fun getCitaById(id: Int): Flow<CitasEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCita(cita: CitasEntity)

    @Update
    suspend fun updateCita(cita: CitasEntity)

    @Query("DELETE FROM citas WHERE id = :id")
    suspend fun deleteCita(id: Int)
}
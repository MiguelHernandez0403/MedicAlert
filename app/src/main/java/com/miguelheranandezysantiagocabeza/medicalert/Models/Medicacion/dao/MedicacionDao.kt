package com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.entity.MedicacionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicacionDao {

    @Query("SELECT * FROM medicacion ORDER BY hora ASC")
    fun getAll(): Flow<List<MedicacionEntity>>

    @Query("SELECT * FROM medicacion WHERE id = :id")
    suspend fun getById(id: Int): MedicacionEntity?

    @Query("SELECT * FROM medicacion WHERE id = :id LIMIT 1")
    fun getByIdFlow(id: Int): Flow<MedicacionEntity?>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(medicacion: MedicacionEntity)

    @Update
    suspend fun update(medicacion: MedicacionEntity)

    @Query("DELETE FROM medicacion WHERE id = :id")
    suspend fun deleteById(id: Int)
}
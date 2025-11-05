package com.miguelheranandezysantiagocabeza.medicalert.data.local.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion

@Dao
interface MedicacionDao {

    @Query("SELECT * FROM Medicacion")
    fun getAll(): List<Medicacion>

    @Insert
    fun insertAll(vararg medicaciones: Medicacion)

    @Insert
    fun insert(medicacion: Medicacion)

    @Delete
    fun delete(medicacion: Medicacion)

    @Update
    fun update(medicacion: Medicacion)
}
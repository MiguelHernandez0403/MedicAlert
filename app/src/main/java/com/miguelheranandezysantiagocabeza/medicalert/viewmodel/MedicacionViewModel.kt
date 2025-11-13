package com.miguelheranandezysantiagocabeza.medicalert.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.MedicacionEntity
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.MedicacionRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MedicacionViewModel(
    private val repo: MedicacionRepository
) : ViewModel() {

    // LISTA COMPLETA EN TIEMPO REAL
    private val _medicaciones = MutableStateFlow<List<MedicacionEntity>>(emptyList())
    val medicaciones: StateFlow<List<MedicacionEntity>> = _medicaciones.asStateFlow()

    init {
        // Se mantiene siempre sincronizado con la BD
        viewModelScope.launch {
            repo.getAll().collect { lista ->
                _medicaciones.value = lista
            }
        }
    }

    // --------------------------
    // OBTENER POR ID
    // --------------------------
    fun obtenerPorId(id: Int?): Flow<MedicacionEntity?> {
        return if (id == null || id == -1) flowOf(null)
        else repo.getByIdFlow(id)
    }

    // --------------------------
    // INSERTAR NUEVA MEDICACIÓN
    // --------------------------
    fun insertar(
        nombre: String,
        dosis: String,
        hora: String,
        frecuencia: Int,
        imagen: String?
    ) {
        viewModelScope.launch {
            val nueva = MedicacionEntity(
                nombre = nombre,
                dosis = dosis,
                hora = hora,
                frecuenciaHoras = frecuencia,
                imagen = imagen
            )
            repo.insert(nueva)
        }
    }

    // --------------------------
    // ACTUALIZAR MEDICACIÓN
    // --------------------------
    fun actualizar(
        id: Int?,
        nombre: String,
        dosis: String,
        hora: String,
        frecuencia: Int,
        imagen: String?
    ) {
        if (id == null || id == -1) return  // no se puede actualizar algo que no existe

        viewModelScope.launch {
            repo.update(
                MedicacionEntity(
                    id = id,
                    nombre = nombre,
                    dosis = dosis,
                    hora = hora,
                    frecuenciaHoras = frecuencia,
                    imagen = imagen
                )
            )
        }
    }

    // --------------------------
    // ELIMINAR MEDICACIÓN
    // --------------------------
    fun eliminar(id: Int) {
        viewModelScope.launch {
            repo.delete(id)
        }
    }
}
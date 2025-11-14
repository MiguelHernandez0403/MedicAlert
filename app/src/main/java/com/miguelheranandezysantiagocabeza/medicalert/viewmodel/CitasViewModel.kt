package com.miguelheranandezysantiagocabeza.medicalert.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.entity.CitasEntity
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion.repository.CitasRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CitasViewModel(
    private val repo: CitasRepository
) : ViewModel() {

    private val _citas = MutableStateFlow<List<CitasEntity>>(emptyList())
    val citas: StateFlow<List<CitasEntity>> = _citas.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getCitas().collect { lista ->
                _citas.value = lista
            }
        }
    }

    fun obtenerPorId(id: Int?): Flow<CitasEntity?> {
        return if (id == null || id == -1) {
            MutableStateFlow(null)
        } else {
            repo.getCitasById(id)
        }
    }

    fun insertar(titulo: String, fecha: String, hora: String, lugar: String, notas: String?) {
        viewModelScope.launch {
            val nueva = CitasEntity(
                titulo = titulo,
                fecha = fecha,
                hora = hora,
                lugar = lugar,
                notas = notas
            )
            repo.insertCita(nueva)
        }
    }

    fun actualizar(id: Int, titulo: String, fecha: String, hora: String, lugar: String, notas: String?) {
        viewModelScope.launch {
            val data = CitasEntity(
                id = id,
                titulo = titulo,
                fecha = fecha,
                hora = hora,
                lugar = lugar,
                notas = notas
            )
            repo.updateCita(data)
        }
    }

    fun eliminar(id: Int) {
        viewModelScope.launch {
            repo.deleteCita(id)
        }
    }
}
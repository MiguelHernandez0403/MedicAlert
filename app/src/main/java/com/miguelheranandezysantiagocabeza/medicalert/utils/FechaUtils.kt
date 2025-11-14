package com.miguelheranandezysantiagocabeza.medicalert.utils

import java.text.SimpleDateFormat
import java.util.*

object FechaUtils {

    fun combinarFechaYHora(fecha: String, hora: String): Long {
        if (fecha.isBlank() || hora.isBlank()) return 0L

        val formato = SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault())

        return try {
            val date = formato.parse("$fecha $hora")
            date?.time ?: 0L
        } catch (e: Exception) {
            0L
        }
    }

    fun horaATimestamp(hora: String): Long {
        val formato = SimpleDateFormat("hh:mm a", Locale.getDefault())

        return try {
            val date = formato.parse(hora)
            date?.time ?: 0L
        } catch (e: Exception) {
            0L
        }
    }
    fun obtenerFechaHoy(): String {
        val f = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return f.format(Date())
    }

    fun obtenerHoraActual(): String {
        val f = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return f.format(Date())
    }
}
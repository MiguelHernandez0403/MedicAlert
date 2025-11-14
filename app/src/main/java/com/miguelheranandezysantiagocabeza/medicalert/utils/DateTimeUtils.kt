package com.miguelheranandezysantiagocabeza.medicalert.utils

import java.text.SimpleDateFormat
import java.util.*

fun convertirFechaHoraAMillis(fecha: String, hora: String): Long {
    val formato = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val fechaCompleta = "$fecha $hora"

    val date = formato.parse(fechaCompleta)
        ?: throw IllegalArgumentException("Fecha u hora inválida: $fechaCompleta")

    return date.time
}

fun millisAHora(millis: Long): String {
    val formato = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formato.format(Date(millis))
}

fun millisAFecha(millis: Long): String {
    val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formato.format(Date(millis))
}

fun millisAFechaHora(millis: Long): String {
    val formato = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return formato.format(Date(millis))
}

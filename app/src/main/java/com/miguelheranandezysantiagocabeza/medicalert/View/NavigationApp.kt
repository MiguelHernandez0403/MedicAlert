package com.miguelheranandezysantiagocabeza.medicalert.View

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun NavigationApp(){

    val navController = rememberNavController()
    val startDestination = "Detalles"


    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = "Charge") {
            ChargeScreen()
        }

        composable (route = "Medicacion") {
            MedicacionesScreen()
        }
        composable (route = "Detalles") {
            DetallesMedicacionScreen()
        }
    }
}
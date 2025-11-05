package com.miguelheranandezysantiagocabeza.medicalert.Navigation

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.miguelheranandezysantiagocabeza.medicalert.View.AddEditMediScreen
import com.miguelheranandezysantiagocabeza.medicalert.View.HistoryScreen
import com.miguelheranandezysantiagocabeza.medicalert.Models.Medicacion
import com.miguelheranandezysantiagocabeza.medicalert.View.ChargeScreen
import com.miguelheranandezysantiagocabeza.medicalert.View.DetallesMedicacionScreen
import com.miguelheranandezysantiagocabeza.medicalert.View.MedicacionesScreen
import com.miguelheranandezysantiagocabeza.medicalert.View.WelcomeScreen
import kotlinx.coroutines.delay


@Composable
fun NavigationApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "Charge",
        modifier = Modifier.fillMaxSize()
    ) {
        composable (route = "Charge") {
            ChargeScreenWithNavigation(
                onLoadingComplete = {
                    navController.navigate("Welcome") {
                        popUpTo("Charge") { inclusive = true }
                    }
                }
            )
        }

        composable(route = "Welcome"){
            WelcomeScreen(
                onCitas = { navController.navigate("Citas") },
                onMedicaciones = { navController.navigate("Medicacion") }
            )
        }

        composable(route = "Medicacion") {
            MedicacionesScreen (
                OnClickDetalles = { medicacion ->
                    // Serializamos el objeto a JSON
                    val medicacionJson = Uri.encode(Gson().toJson(medicacion))
                    navController.navigate("Detalle/$medicacionJson")
                },
                OnClickEditar = { navController.navigate("AddEditMedi") },
                OnclickVolver = { navController.popBackStack() },
                OnclickHistorial = {navController.navigate("Historial")}
            )
        }

        composable(route = "AddEditMedi") {
            AddEditMediScreen (
                onBack = { navController.popBackStack() },
                onSave = { nombre, dosis, regularidad, hora, foto ->
                    navController.popBackStack()
                }
            )
        }

        composable(route = "Historial"){
            HistoryScreen(
                items = emptyList(),
                onBack = { navController.popBackStack() }
            )
        }

        composable(route = "Detalle/{medicacion}",
            arguments = listOf(navArgument("medicacion") { type = NavType.StringType })
        ) {
                backStackEntry ->
            val medicacionJson = backStackEntry.arguments?.getString("medicacion")
            val medicacion = Gson().fromJson(
                Uri.decode(medicacionJson),
                Medicacion::class.java
            )
            DetallesMedicacionScreen(medicacion,
                OnClickVolver = { navController.popBackStack()
                })
        }
    }
}

@Composable
fun ChargeScreenWithNavigation(
    onLoadingComplete: () -> Unit
) {
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect (Unit) {
        delay(3000)
        isLoading = false
        onLoadingComplete()
    }

    ChargeScreen()
}
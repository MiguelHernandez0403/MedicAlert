package com.miguelheranandezysantiagocabeza.medicalert.Navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.miguelheranandezysantiagocabeza.medicalert.MedicAlertApplication
import com.miguelheranandezysantiagocabeza.medicalert.View.AddEditMediScreen
import com.miguelheranandezysantiagocabeza.medicalert.View.HistoryScreen
import com.miguelheranandezysantiagocabeza.medicalert.View.AddEditCitaScreen
import com.miguelheranandezysantiagocabeza.medicalert.View.ChargeScreen
import com.miguelheranandezysantiagocabeza.medicalert.View.CitasScreen
import com.miguelheranandezysantiagocabeza.medicalert.View.DetallesMedicacionScreen
import com.miguelheranandezysantiagocabeza.medicalert.View.MedicacionesScreen
import com.miguelheranandezysantiagocabeza.medicalert.View.WelcomeScreen
import com.miguelheranandezysantiagocabeza.medicalert.ViewModel.MedicacionViewModel
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

        composable("Medicacion") {
            MedicacionesScreen(
                OnClickDetalles = { id ->
                    navController.navigate("Detalle/$id")
                },
                OnClickEditar = {               // CREAR
                    navController.navigate("AddEditMedi")
                },
                OnclickVolver = { navController.popBackStack() },
                OnclickHistorial = { navController.navigate("Historial") }
            )
        }

        composable("AddEditMedi") {

            val app = LocalContext.current.applicationContext as MedicAlertApplication
            val viewModel: MedicacionViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return MedicacionViewModel(app.medicacionRepository) as T
                    }
                }
            )

            AddEditMediScreen(
                onBack = { navController.popBackStack() },
                onSave = { navController.popBackStack() },
                idMedicacion = null,
                viewModel = viewModel
            )
        }

        composable(
            route = "AddEditMedi/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->

            val app = LocalContext.current.applicationContext as MedicAlertApplication
            val viewModel: MedicacionViewModel = viewModel (
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return MedicacionViewModel(app.medicacionRepository) as T
                    }
                }
            )

            val id = backStackEntry.arguments?.getInt("id")

            AddEditMediScreen(
                onBack = { navController.popBackStack() },
                onSave = { navController.popBackStack() },
                idMedicacion = id,
                viewModel = viewModel
            )
        }

        composable(route = "Historial"){
            HistoryScreen(onBack = { navController.popBackStack() })
        }

        composable(
            route = "Detalle/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("id") ?: 0

            DetallesMedicacionScreen(
                idMedicacion = id,
                OnClickVolver = { navController.popBackStack() }
            )
        }

        composable(route = "Citas") {
            CitasScreen (
                OnClickAgregar = { navController.navigate("AddEditCita")},
                OnclickVolver = { navController.popBackStack() }
            )
        }

        composable(route = "AddEditCita") {
            AddEditCitaScreen(
                onBack = { navController.popBackStack() },
                onSave = { navController.popBackStack() }
            )
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
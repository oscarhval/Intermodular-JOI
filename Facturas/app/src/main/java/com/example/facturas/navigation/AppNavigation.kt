package com.example.facturas.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.facturas.viewmodels.FacturasViewModel
import com.example.facturas.views.*

@SuppressLint("SuspiciousIndentation")
@Composable
fun AppNavigation(viewModel: FacturasViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "LoginScreen") {
        composable("LoginScreen") {
            LoginScreen(navController)
        }
        composable("RegisterScreen") {
            RegisterScreen(navController)
        }
        composable("FacturaListView") {
            FacturaListView(navController, viewModel)
        }
        composable("FacturaAddView") {
            FacturaAddView(navController, viewModel)
        }
        composable("CompraOVenta") {
            CompraOVenta(navController = navController)
        }
        composable(
            "FacturaUpdateView/{id}/{numeroFactura}/{fecha}/{emisor}/{receptor}/{base}/{iva}/{tipo}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
                navArgument("numeroFactura") { type = NavType.StringType },
                navArgument("fecha") { type = NavType.StringType },
                navArgument("emisor") { type = NavType.StringType },
                navArgument("receptor") { type = NavType.StringType },
                navArgument("base") { type = NavType.StringType },
                navArgument("iva") { type = NavType.StringType },
                navArgument("tipo") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            FacturaUpdateView(
                navController,
                viewModel,
                backStackEntry.arguments?.getString("id") ?: "",
                backStackEntry.arguments?.getString("numeroFactura") ?: "",
                backStackEntry.arguments?.getString("fecha") ?: "",
                backStackEntry.arguments?.getString("emisor") ?: "",
                backStackEntry.arguments?.getString("receptor") ?: "",
                backStackEntry.arguments?.getString("base") ?: "",
                backStackEntry.arguments?.getString("iva") ?: "",
                backStackEntry.arguments?.getString("tipo") ?: ""
            )
        }
    }
}

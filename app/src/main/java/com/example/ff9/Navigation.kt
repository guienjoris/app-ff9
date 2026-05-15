package com.example.ff9

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

object Routes {
    const val screenHome = "screen_home"
    const val screenChoosePerso = "screen_choose_perso"
    const val screenDetailsPerso = "screen_details_perso"
}

@Composable
fun AppNavigation() {
    // 2. Initialisation du NavController
    val navController = rememberNavController()

    // 3. Configuration du NavHost
    NavHost(
        navController = navController,
        startDestination = Routes.screenHome,

    ) {
        // Écran A
        composable(Routes.screenHome) {
            HomeScreen(
                onNavigateToListPersos = {
                    navController.navigate(Routes.screenChoosePerso)
                }
            )
        }

        composable(Routes.screenChoosePerso) {
            ListChoosePersoView(
                onBack = { navController.popBackStack() },
                onNavigateToPersoDetails = { indexPerso ->
                    navController.navigate("${Routes.screenDetailsPerso}/$indexPerso")
                }
            )
        }

        composable(
            route = "${Routes.screenDetailsPerso}/{indexPerso}",
            arguments = listOf(navArgument("indexPerso") { type = NavType.StringType })
        ) { backStackEntry ->
            val persoParams = backStackEntry.arguments?.getString("indexPerso") ?: "Inconnu"
            PersoDetailsView(
                indexPerso = persoParams,
                onBack = { navController.popBackStack(Routes.screenChoosePerso,inclusive = false) }
            )
        }
    }
}


